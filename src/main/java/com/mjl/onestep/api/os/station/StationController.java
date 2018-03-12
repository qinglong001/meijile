package com.mjl.onestep.api.os.station;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mjl.onestep.common.base.BaseController;
import com.mjl.onestep.common.utils.AjaxJson;
import com.mjl.onestep.common.utils.QiniuUtil;
import com.mjl.onestep.po.os.OsContent;
import com.mjl.onestep.po.os.OsStationInfo;
import com.mjl.onestep.po.os.OsTemplate;
import com.mjl.onestep.po.sys.SysUser;
import com.mjl.onestep.repository.os.OsContentRs;
import com.mjl.onestep.repository.os.OsStationInfoRs;
import com.mjl.onestep.repository.os.OsTemplateRs;
import com.mjl.onestep.service.StationService;
import com.xiaoleilu.hutool.date.DateUtil;

/**
 * 站点信息核心控制器类
 * 
 * @author fcj
 * 
 */
@RestController
@RequestMapping("/api/station/")
@Api(value = "站点相关接口 StationController /api/station/", tags = { "OS一步建站  站点相关接口" })
public class StationController extends BaseController {

	@Autowired
	private OsStationInfoRs osStationInfoRs;
	@Autowired
	private OsTemplateRs osTemplateRs;
	@Autowired
	private StationService stationService;
	@Autowired
	private OsContentRs osContentRs;

	@ApiOperation(value = "获取该用户的站点信息", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", value = "页数从0开始", required = false, dataType = "Integer", paramType = "form"),
			@ApiImplicitParam(name = "size", value = "每页数量", required = false, dataType = "Integer", paramType = "form") })
	@PostMapping("/list")
	public AjaxJson list(HttpServletRequest request,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) {
		AjaxJson a = getA();
		Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC,
				"createTime");
		Page<OsStationInfo> stationInfoPage = osStationInfoRs
				.findByStationUserIdOrderByCreateTimeAsc(getUserId(), pageable);
		if (stationInfoPage.getContent().size() > 0) {
			for (int i = 0; i < stationInfoPage.getContent().size(); i++) {
				if (stationInfoPage.getContent().get(i).getTemplateId() != null) {
					OsTemplate osTemplate = osTemplateRs
							.findOne(stationInfoPage.getContent().get(i)
									.getTemplateId());
					stationInfoPage.getContent().get(i)
							.setOsTemplate(osTemplate);
				}
			}
		}
		a.put("data", stationInfoPage);
		return a;
	}

	@ApiOperation(value = "新增站点信息", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "站点名称", required = false, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "prefix", value = "站点地址前缀", required = false, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "templateId", value = "模板id", required = false, dataType = "Long", paramType = "form") })
	@PostMapping("/add")
	public AjaxJson add(@RequestParam(required = false) String name,
			@RequestParam(required = false) String prefix,
			@RequestParam(required = false) Long templateId) {
		AjaxJson a = getA();
		// 查询站点地址前缀是否重复

		OsStationInfo oStationInfos = osStationInfoRs.findByPrefix(prefix);
		// 获取用户站点个数
		List<OsStationInfo> osStationInfos = osStationInfoRs
				.findByStationUserIdOrderByCreateTimeAsc(getUserId());
		if (oStationInfos != null) {
			a.errorMsg("站点地址前缀重复", "-1");
		} else if (osStationInfos.size() > 4) {
			a.errorMsg("当前用户站点数量不能超过4个", "-1");
		} else {
			OsStationInfo s = new OsStationInfo();
			s.setName(name);
			s.setState(1);
			s.setType(1);
			s.setPrefix(prefix);
			s.setTemplateId(templateId);
			s.setStationUserId(getUserId());
			s.setStationUserName(getUserName());
			s.setCreateTime(DateUtil.date());
			osStationInfoRs.save(s);
			a.successMsg("添加成功");
		}
		return a;
	}

	@ApiOperation(value = "保存站点模板", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "stationInfoId", value = "站点id", required = false, dataType = "Long", paramType = "form"),
			@ApiImplicitParam(name = "templateId", value = "模板id", required = false, dataType = "Long", paramType = "form") })
	@PostMapping("/saveTemplateId")
	public AjaxJson saveTemplateId(
			@RequestParam(required = false) Long stationInfoId,
			@RequestParam(required = false) Long templateId) {
		AjaxJson a = getA();
		// 查询站点信息是否存在
		OsStationInfo oStationInfo = osStationInfoRs.findOne(stationInfoId);
		if (oStationInfo == null) {
			a.errorMsg("站点信息不存在", "-1");
		} else {
			oStationInfo.setTemplateId(templateId);
			osStationInfoRs.save(oStationInfo);
			a.successMsg("保存成功");
		}
		return a;
	}

	@ApiOperation(value = "修改站点名称", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "站点名称", required = false, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "id", value = "站点id", required = false, dataType = "Long", paramType = "form") })
	@PostMapping("/edit")
	public AjaxJson edit(@RequestParam(required = false) Long id,
			@RequestParam(required = false) String name) {
		AjaxJson a = getA();
		OsStationInfo stationInfo = osStationInfoRs.findOne(id);
		if (stationInfo != null) {
			stationInfo.setName(name);
			osStationInfoRs.saveAndFlush(stationInfo);
			a.successMsg("修改成功");
		} else {
			a.errorMsg("修改失败", "-1");
		}
		return a;
	}

	@ApiOperation(value = "删除站点信息", notes = "")
	@ApiImplicitParam(name = "id", value = "站点id", required = false, dataType = "Long", paramType = "form")
	@PostMapping("/del")
	public AjaxJson del(@RequestParam(required = false) Long id) {
		AjaxJson a = getA();
		QiniuUtil qiniuUtil = new QiniuUtil();
		// 查询站点下的内容信息并进行删除操作
		OsStationInfo stationInfo = osStationInfoRs.findOne(id);
		List<OsContent> osContents = osContentRs.findByStationIdAndTemplateId(
				stationInfo.getId(), stationInfo.getTemplateId());
		for (int i = 0; i < osContents.size(); i++) {
			if (osContents.get(i).getType() == 2) {
				//删除七牛云图片
				qiniuUtil.removeImg(osContents.get(i).getContentInfo());
			}
			osContentRs.delete(osContents.get(i).getId());
		}
		osStationInfoRs.delete(id);
		a.successMsg("删除成功");
		return a;
	}
}
