package com.mjl.onestep.api.os.template;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnitUtil;

import net.sf.ehcache.hibernate.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mjl.onestep.common.base.BaseController;
import com.mjl.onestep.common.interceptor.IgnoreAuth;
import com.mjl.onestep.common.utils.AjaxJson;
import com.mjl.onestep.common.utils.QiniuUtil;
import com.mjl.onestep.po.os.OsTemplate;
import com.mjl.onestep.repository.os.OsTemplateRs;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.RandomUtil;
/**
 * 模板管理核心控制器类
 * @author fcj
 *
 */
@Component
@RestController
@RequestMapping("/api/template/")
@Api(value="模板相关接口 TemplateController /api/template/",tags={"OS一步建站  模板相关接口"})


public class TemplateController extends BaseController {
	
	@Autowired
    private OsTemplateRs osTemplateRs;
	@Value("${domain.name}")
	private  String  domain;
	@Value("${qiniu.path}")
	private  String  qiniuPath;
	
	@ApiOperation(value="获取模板列表数据", notes="")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", value = "页数从0开始", required = false, dataType = "Integer" ,paramType="form"),
		@ApiImplicitParam(name = "size", value = "每页数量", required = false, dataType = "Integer" ,paramType="form"),
		@ApiImplicitParam(name = "type", value = "模板类型 1、个人2、企业、3、销售", required = false, dataType = "Integer" ,paramType="form")
	})
	@PostMapping("/list")
	@IgnoreAuth
	public AjaxJson list(	
								@RequestParam Integer page,
								@RequestParam Integer size,
								@RequestParam Integer type
							){
		AjaxJson a=	getA();
		
		//分页查询获取数据
		Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "createTime"); 
		Page<OsTemplate> pagePo = null;
		if(type == null) {
			 pagePo=	osTemplateRs.findAll(pageable);
		}else {
			 pagePo=	osTemplateRs.findByType(type,pageable);
		}
		//返回拼接预览地址
		for (int i = 0; i < pagePo.getContent().size(); i++) {
			pagePo.getContent().get(i).setLinkUrlstr("http://"+pagePo.getContent().get(i).getLinkurl()+domain);
			pagePo.getContent().get(i).setPic1Str(qiniuPath+pagePo.getContent().get(i).getPic1());
		}
		a.put("data", pagePo);
		return a;
	}
	
	@ApiOperation(value="新增模板", notes="")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "title", value = "模板标题", required = false, dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "info", value = "模板介绍", required = false, dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "linkurl", value = "模板预览地址", required = false, dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "type", value = "模板类型 1、个人2、企业、3、销售", required = false, dataType = "Integer" ,paramType="form"),
		@ApiImplicitParam(name = "file", value = "模板预览图", required = false, dataType = "MultipartFile" ,paramType="form")
	})
	@PostMapping("/add")
	public AjaxJson add(	
							@RequestParam String title,
							@RequestParam String info,
							@RequestParam String linkurl,
							@RequestParam Integer type,
							@RequestParam("file") MultipartFile file
							){
		AjaxJson a=	getA();
		
		FileInputStream inputStream;
		try {
			inputStream = (FileInputStream) file.getInputStream();
		QiniuUtil q=new QiniuUtil();
	    String path = q.uploadImg(inputStream, getImgId());
		OsTemplate po=new OsTemplate();
		po.setTitle(title);
		po.setInfo(info);
		po.setLinkurl(linkurl);
		po.setPic1(path);
		po.setType(type);
		//新增模板状态为开启
		po.setStatus(1);
		po.setCreateTime(DateUtil.now());
		po.setCreateUserId(getUserId());
		
		osTemplateRs.save(po);
		a.successMsg("添加成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}
	
	@ApiOperation(value="修改模板状态", notes="")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "模板id", required = false, dataType = "Long" ,paramType="form"),
		@ApiImplicitParam(name = "status", value = "模板状态 1开启 2关闭", required = false, dataType = "Integer" ,paramType="form")
	
	})
	@PostMapping("/editStatus")
	public AjaxJson editStatus(
								@RequestParam Long    id,
								@RequestParam Integer  status
							){
		AjaxJson a=	getA();
		OsTemplate po=osTemplateRs.findOne(id);
		if(po!=null){
			po.setStatus(status);
			osTemplateRs.saveAndFlush(po);
			a.successMsg("修改成功");
		}else{
			a.errorMsg("修改失败", "-1");
		}
		return a;
	}
	@ApiOperation(value="修改模板预览图", notes="")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "模板id", required = false, dataType = "Long" ,paramType="form"),
		@ApiImplicitParam(name = "file", value = "模板预览图",  dataType = "MultipartFile" ,paramType="MultipartFile")
	
	})
	@PostMapping("/editPic")
	public AjaxJson editPic(
								@RequestParam Long    id,
								@RequestParam("file") MultipartFile file
							){
		AjaxJson a=	getA();
		OsTemplate po=osTemplateRs.findOne(id);
		FileInputStream inputStream;
		try {
		inputStream = (FileInputStream) file.getInputStream();
		QiniuUtil q=new QiniuUtil();
	     q.removeImg(po.getPic1());
	     String path =q.uploadImg(inputStream,getImgId());
		po.setPic1(path);
		osTemplateRs.saveAndFlush(po);
		a.put("path", qiniuPath+path);
		a.successMsg("修改成功");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return a;
	}
	@ApiOperation(value="修改模板信息", notes="")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "模板id", required = false, dataType = "Long" ,paramType="form"),
		@ApiImplicitParam(name = "title", value = "模板标题", required = false, dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "info", value = "模板介绍", required = false, dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "linkurl", value = "模板预览地址", required = false, dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "type", value = "模板类型 1、个人2、企业、3、销售", required = false, dataType = "Integer" ,paramType="form")
	})
	@PostMapping("/edit")
	public AjaxJson edit(
								@RequestParam Long    id,
								@RequestParam String  title,
								@RequestParam String  info,
								@RequestParam String  linkurl,
								@RequestParam Integer type
							){
		AjaxJson a=	getA();
		OsTemplate po=osTemplateRs.findOne(id);
		if(po!=null){
			po.setTitle(title);
			po.setInfo(info);
			po.setLinkurl(linkurl);
			po.setType(type);
			osTemplateRs.saveAndFlush(po);
			a.successMsg("修改成功");
		}else{
			a.errorMsg("修改失败", "-1");
		}
		return a;
	}
	@ApiOperation(value="删除模板信息", notes="")
	@ApiImplicitParam(name = "id", value = "模板id", required = false, dataType = "Long" ,paramType="form")
	@PostMapping("/del")
	public AjaxJson delStation(	
							@RequestParam(required = false) Long id
							){
		AjaxJson a=	getA();
		osTemplateRs.delete(id);
		a.successMsg("删除成功");
		return a;
	}
}
