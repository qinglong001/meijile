package com.mjl.onestep.api.os.channel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mjl.onestep.common.base.BaseController;
import com.mjl.onestep.common.utils.AjaxJson;
import com.mjl.onestep.po.os.OsChannel;
import com.mjl.onestep.repository.os.OsChannelRs;
import com.xiaoleilu.hutool.date.DateUtil;
/**
 * 頻道核心控制器类
 * @author fcj
 *
 */
@RestController
@RequestMapping("/api/channel/")
@Api(value="頻道相关接口  ChannelController",tags={"OS一步建站 頻道相关接口"})
public class ChannelController extends BaseController {
	
	@Autowired
    private OsChannelRs osChannelRs;
	
	
	@ApiOperation(value="根据站点id获取频道列表信息", notes="根据站点id获取频道列表信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "stationId", value = "站点id", required = false, dataType = "Long" ,paramType="form")
	})
	@PostMapping("/list")
	public AjaxJson list(		@RequestParam(required = false) Long stationId
							){
		AjaxJson a=	getA();
		List<OsChannel> listPo=	osChannelRs.findByStationlIdOrderByCreateTimeAsc(stationId);
		a.put("data", listPo);
		return a;
	}
	@ApiOperation(value="新增频道", notes="")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "stationId", value = "站点id", required = false, dataType = "Long" ,paramType="form"),
		@ApiImplicitParam(name = "templateId", value = "模板id", required = false, dataType = "Long" ,paramType="form"),
		@ApiImplicitParam(name = "enName", value = "频道中文名称", required = false, dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "zhName", value = "频道英文名称", required = false, dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "position", value = "频道位置", required = false, dataType = "Integer" ,paramType="form"),
		@ApiImplicitParam(name = "linkurl", value = "频道链接地址", required = false, dataType = "String" ,paramType="form")
	})
	@PostMapping("/add")
	@CacheEvict(value="Channel", allEntries=true)
	public AjaxJson childrenMenu(	
							@RequestParam(required = false) Long stationId,
							@RequestParam(required = false) Long templateId,
							@RequestParam(required = false) String enName,
							@RequestParam(required = false) String zhName,
							@RequestParam(required = false) Integer	position,
							@RequestParam(required = false) String	linkurl
							){
		AjaxJson a=	getA();
		//判断已有频道数量是否超过10个
		List<OsChannel> listPo=	osChannelRs.findByStationlIdOrderByCreateTimeAsc(stationId);
			OsChannel po=new OsChannel();
			po.setStationlId(stationId);
			po.setTemplateId(templateId);
			po.setPosition(position);
			po.setEnName(enName);
			po.setZhName(zhName);
			po.setLinkurl(linkurl);
			po.setStatus(1);
			po.setCreateTime(DateUtil.now());
			osChannelRs.save(po);
			a.successMsg("添加成功");
		
		return a;
	}
	@ApiOperation(value="修改频道信息", notes="")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "频道id", required = false, dataType = "Long" ,paramType="form"),
		@ApiImplicitParam(name = "templateId", value = "模板id", required = false, dataType = "Long" ,paramType="form"),
		@ApiImplicitParam(name = "enName", value = "频道中文名称", required = false, dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "zhName", value = "频道英文名称", required = false, dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "position", value = "频道位置", required = false, dataType = "in" ,paramType="form"),
		@ApiImplicitParam(name = "linkurl", value = "频道链接地址", required = false, dataType = "String" ,paramType="form")
	})
	@PostMapping("/edit")
	@CacheEvict(value="Channel", allEntries=true)
	public AjaxJson editStation(
								@RequestParam(required = false) Long id,
								@RequestParam(required = false) Long templateId,
								@RequestParam(required = false) String enName,
								@RequestParam(required = false) String zhName,
								@RequestParam(required = false) Integer	position,
								@RequestParam(required = false) String	linkurl
							){
		AjaxJson a=	getA();
		OsChannel po=osChannelRs.findOne(id);
		if(po!=null){
			po.setTemplateId(templateId);
			po.setEnName(enName);
			po.setZhName(zhName);
			po.setPosition(position);
			po.setLinkurl(linkurl);
			osChannelRs.saveAndFlush(po);
			a.successMsg("修改成功");
		}else{
			a.errorMsg("修改失败", "-1");
		}
		return a;
	}
	
	@ApiOperation(value="删除频道信息", notes="")
	@ApiImplicitParam(name = "id", value = "删除id", required = false, dataType = "Long" ,paramType="form")
	@PostMapping("/del")
	@CacheEvict(value="Channel", allEntries=true)
	public AjaxJson delStation(	
							@RequestParam(required = false) Long id
							){
		AjaxJson a=	getA();
		osChannelRs.delete(id);
		a.successMsg("删除成功");
		return a;
	}
	
}
