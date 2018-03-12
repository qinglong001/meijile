package com.mjl.onestep.api.os.sign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mjl.onestep.common.base.BaseController;
import com.mjl.onestep.common.utils.AjaxJson;
import com.mjl.onestep.po.os.OsSign;
import com.mjl.onestep.repository.os.OsSignRs;
import com.xiaoleilu.hutool.date.DateUtil;
/**
 * 內容标志核心控制器类
 * @author fcj
 *
 */
@RestController
@RequestMapping("/api/sign/")
@Api(value="內容标志相关接口  SignController",tags={"OS一步建站  內容标志相关接口"})
public class SignController extends BaseController {
	
	@Autowired
    private OsSignRs osSignRs;
	
	
	@ApiOperation(value="获取标志列表分页数据", notes="获取标志列表分页数据")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", value = "页数从0开始", required = false, dataType = "Integer" ,paramType="form"),
		@ApiImplicitParam(name = "size", value = "每页数量", required = false, dataType = "Integer" ,paramType="form")
	})
	@PostMapping("/list")
	public AjaxJson list(		
								@RequestParam(required = false) Integer page,
								@RequestParam(required = false) Integer size
							){
		AjaxJson a=	getA();
		Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "createTime"); 
		Page<OsSign> pagePo=	osSignRs.findAll(pageable);
		a.put("data", pagePo);
		return a;
	}
	@ApiOperation(value="新增内容标志", notes="")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "enName", value = "内容标志英文名称", required = false, dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "zhName", value = "内容标志中文名称", required = false, dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "contentType", value = "内容标志类型 1、文字2、图片3、链接", required = false, dataType = "int" ,paramType="form")
	})
	@PostMapping("/add")
	public AjaxJson childrenMenu(	
							@RequestParam(required = false) String enName,
							@RequestParam(required = false) String zhName,
							@RequestParam(required = false) Integer contentType
							){
		AjaxJson a=	getA();
		OsSign po=new OsSign();
		po.setEnName(enName);
		po.setZhName(zhName);
		po.setContentType(contentType);
		po.setCreateTime(DateUtil.now());
		po.setCreateUserId(getUserId());
		osSignRs.save(po);
		a.successMsg("添加成功");
		return a;
	}
	@ApiOperation(value="修改内容标志信息", notes="")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "内容标志id", required = false, dataType = "Long" ,paramType="form"),
		@ApiImplicitParam(name = "enName", value = "内容标志英文名称", required = false, dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "zhName", value = "内容标志中文名称", required = false, dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "contentType", value = "内容标志类型 1、文字2、图片3、链接", required = false, dataType = "Integer" ,paramType="form")
	})
	@PostMapping("/edit")
	public AjaxJson editStation(
								@RequestParam(required = false) Long id,
								@RequestParam(required = false) String enName,
								@RequestParam(required = false) String zhName,
								@RequestParam(required = false) Integer contentType
							){
		AjaxJson a=	getA();
		OsSign po=osSignRs.findOne(id);
		if(po!=null){
			po.setEnName(enName);
			po.setZhName(zhName);
			po.setContentType(contentType);
			osSignRs.saveAndFlush(po);
			a.successMsg("修改成功");
		}else{
			a.errorMsg("修改失败", "-1");
		}
		return a;
	}
	@ApiOperation(value="删除内容标志信息", notes="")
	@ApiImplicitParam(name = "id", value = "内容标志id", required = false, dataType = "Long" ,paramType="form")
	@PostMapping("/del")
	public AjaxJson delStation(	
							@RequestParam(required = false) Long id
							){
		AjaxJson a=	getA();
		osSignRs.delete(id);
		a.successMsg("删除成功");
		return a;
	}
	
}
