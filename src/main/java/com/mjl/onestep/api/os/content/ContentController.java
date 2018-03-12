package com.mjl.onestep.api.os.content;
import com.mjl.onestep.common.base.BaseController;
import com.mjl.onestep.common.interceptor.IgnoreAuth;
import com.mjl.onestep.common.utils.AjaxJson;
import com.mjl.onestep.common.utils.QiniuUtil;
import com.mjl.onestep.po.os.OsContent;
import com.mjl.onestep.repository.os.OsContentRs;
import com.mjl.onestep.repository.os.OsStationInfoRs;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.util.RandomUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
/**
 * 內容核心控制器类
 * @author fcj
 *
 */
@Component
@RestController
@RequestMapping("/api/content/")
@Api(value="內容相关接口  ContentController",tags={"OS一步建站  內容相关接口"})
public class ContentController extends BaseController {
	
	@Autowired
    private OsContentRs osContentRs;
	
	@Value("${qiniu.path}")
	private  String  qiniuPath;
	
	
	@ApiOperation(value="根据站点id和模板id和标志获取单个内容", notes="根据站点id和模板id和标志获取单个内容")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "stationId", value = "站点id",  dataType = "Long" ,paramType="form"),
		@ApiImplicitParam(name = "templateId", value = "模板id",  dataType = "Long" ,paramType="form"),
		@ApiImplicitParam(name = "signId", value = "标志id",  dataType = "Long" ,paramType="form")
	})
	@PostMapping("/getOneContent")
	public AjaxJson getOneContent(		
								@RequestParam() Long stationId,
								@RequestParam() Long templateId,
								@RequestParam() String signId
							){
		AjaxJson a=	getA();
		List<OsContent> listPo=	osContentRs.findByStationIdAndTemplateIdAndSignIdOrderByCreateTime(stationId, templateId, signId);
		JSONObject json=new JSONObject();
		if(listPo.get(0).getType()==2){
			json.put(listPo.get(0).getSignId(),qiniuPath+listPo.get(0).getContentInfo());
		}else{
			json.put(listPo.get(0).getSignId(),listPo.get(0).getContentInfo());
		}
		a.put("data", json);
		return a;
	}
	@ApiOperation(value="添加文本内容")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "stationId", value = "站点id",  dataType = "Long" ,paramType="form"),
		@ApiImplicitParam(name = "templateId", value = "模板id",  dataType = "Long" ,paramType="form"),
		@ApiImplicitParam(name = "signId", value = " 标识id",  dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "contentInfo", value = "内容信息",  dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "contentRemark", value = "内容描述信息",  dataType = "String" ,paramType="form")
		
	})
	@PostMapping("/add")
	@IgnoreAuth
	public AjaxJson add(	
							@RequestParam() Long stationId,
							@RequestParam() Long templateId,
							@RequestParam() String signId,
							@RequestParam() String contentInfo,
							@RequestParam() String contentRemark
							){
		List<OsContent> listPo=	osContentRs.findByStationIdAndTemplateIdAndSignIdOrderByCreateTime(stationId,templateId,signId);
		AjaxJson a = getA();
		OsContent po=new OsContent();
		if(listPo.size()>0){
			//更新内容
			po=listPo.get(0);
			po.setContentInfo(contentInfo);
			po.setUpdateTime(DateUtil.now());
			osContentRs.saveAndFlush(po);
		}else {

			po.setChannelId(Long.parseLong("0"));
			po.setStationId(stationId);
			po.setTemplateId(templateId);
			po.setSignId(signId);
			po.setType(1);
			po.setContentInfo(contentInfo);
			po.setContentRemark(contentRemark);
			po.setUserId(getUserId());
			po.setCreateTime(DateUtil.now());
			osContentRs.save(po);
		}
		a.successMsg("添加成功");
		return a;
	}
	
	@ApiOperation(value="添加图片内容")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "stationId", value = "站点id",  dataType = "Long" ,paramType="form"),
		@ApiImplicitParam(name = "templateId", value = "模板id",  dataType = "Long" ,paramType="form"),
		@ApiImplicitParam(name = "signId", value = " 标识id",  dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "file", value = "图片文件",  dataType = "MultipartFile" ,paramType="form"),
		@ApiImplicitParam(name = "contentRemark", value = "内容描述信息",  dataType = "String" ,paramType="form")
		
	})
	@PostMapping("/addImage")
	@IgnoreAuth
	public AjaxJson addImage(	
							@RequestParam("file") MultipartFile file,
							@RequestParam() Long stationId,
							@RequestParam() Long templateId,
							@RequestParam() String signId,
							@RequestParam() String contentRemark
							){
			AjaxJson a=	getA();
			FileInputStream inputStream;
			QiniuUtil q=new QiniuUtil();
			OsContent po=new OsContent();
			String path;
		try {
				inputStream = (FileInputStream) file.getInputStream();

				//查询之前有没有上传图片
				List<OsContent> listPo=	osContentRs.findByStationIdAndTemplateIdAndSignIdOrderByCreateTime(stationId,templateId,signId);
				if(listPo.size()>0){
					//更新图片
					 	q.removeImg(listPo.get(0).getContentInfo().toString());
					 	path = q.uploadImg(inputStream, getImgId());
						if(path==null){
							a.errorMsg("添加失败","-1");
							return a;
						}
					 po=listPo.get(0);
					 po.setContentInfo(path);
					 po.setUpdateTime(DateUtil.now());
					 osContentRs.saveAndFlush(po);
				}else{
					//新增图片
					path = q.uploadImg(inputStream,getImgId());
					if(path==null){
						a.errorMsg("添加失败","-1");
						return a;
					}
					po.setChannelId(Long.parseLong("0"));
					po.setStationId(stationId);
					po.setTemplateId(templateId);
					po.setSignId(signId);
					po.setType(2);
					po.setContentInfo(path);
					po.setContentRemark(contentRemark);
					po.setUserId(getUserId());
					po.setCreateTime(DateUtil.now());
					osContentRs.save(po);
				}

				a.successMsg("添加成功");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return a;
	}
	@ApiOperation(value="修改内容信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "内容信息id",  dataType = "Long" ,paramType="form"),
		@ApiImplicitParam(name = "contentInfo", value = "内容信息",  dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "contentRemark", value = "内容描述信息",  dataType = "String" ,paramType="form")
	})
	@PostMapping("/edit")
	@CacheEvict(value="Content", allEntries=true)
	public AjaxJson editStation(
							@RequestParam() Long id,
							@RequestParam() String contentInfo,
							@RequestParam() String contentRemark
							){
		AjaxJson a=	getA();
		OsContent po=osContentRs.findOne(id);
		if(po!=null){
			po.setContentInfo(contentInfo);
			po.setContentRemark(contentRemark);
			po.setUpdateTime(DateUtil.now());
			osContentRs.saveAndFlush(po);
			a.successMsg("修改成功");
		}else{
			a.errorMsg("修改失败", "-1");
		}
		return a;
	}
	
	@ApiOperation(value="删除内容信息")
	@ApiImplicitParam(name = "id", value = "删除内容id",  dataType = "Long" ,paramType="form")
	@PostMapping("/del")
	@CacheEvict(value="Content", allEntries=true)
	public AjaxJson delStation(	
							@RequestParam() Long id
							){
		AjaxJson a=	getA();
		osContentRs.delete(id);
		a.successMsg("删除成功");
		return a;
	}
	
}
