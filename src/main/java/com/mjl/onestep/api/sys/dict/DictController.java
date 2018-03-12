package com.mjl.onestep.api.sys.dict;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mjl.onestep.common.base.BaseController;
import com.mjl.onestep.common.utils.AjaxJson;
import com.mjl.onestep.po.sys.SysDict;
import com.mjl.onestep.repository.sys.SysDictRs;
import com.xiaoleilu.hutool.date.DateUtil;
/**
 * 系统字典核心控制器类
 * @author fcj
 *
 */
@RestController
@RequestMapping("/api/dict/")
@Api(value="系统字典接口 TemplateController /api/dict/",tags={"SYS系统 字典相关接口"})

public class DictController extends BaseController {
	
	@Autowired
    private SysDictRs sysDictRs;
	
	
	@ApiOperation(value="获取所有字典")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", value = "页数从0开始", dataType = "Integer" ,paramType="form"),
		@ApiImplicitParam(name = "size", value = "每页数量", dataType = "Integer" ,paramType="form")
	})
	@PostMapping("/list")
	public AjaxJson mainMenu(@RequestParam(required = false) Integer page,
							 @RequestParam(required = false) Integer size
	){
		AjaxJson a=	getA();
		Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "createTime"); 
		Page<SysDict> pagePo=	sysDictRs.findAll(pageable);
		a.put("data", pagePo);
		return a;
	}
	
	@ApiOperation(value="新增字典")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name", value = "字典值名称", dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "bianma", value = "字典编码", dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "dictValue", value = "字典值", dataType = "Integer" ,paramType="form"),
		@ApiImplicitParam(name = "ordyBy", value = "字典排序序号", dataType = "Integer" ,paramType="form")
	})
	@PostMapping("/add")
	public AjaxJson childrenMenu(	
							@RequestParam(required = false) String name,
							@RequestParam(required = false) String	bianma,
							@RequestParam(required = false) Integer dictValue,
							@RequestParam(required = false) Integer ordyBy
							){
		AjaxJson a=	getA();
		SysDict po=new SysDict();
		po.setName(name);
		po.setBianma(bianma);
		po.setOrdyBy( ordyBy );
		po.setDictValue(dictValue);
		po.setCreateTime(DateUtil.now());
		po.setCreateUserId(getUserId());
		sysDictRs.save(po);
		a.successMsg("添加成功");
		return a;
	}
	@ApiOperation(value="修改字典")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "字典id", dataType = "Long" ,paramType="form"),
		@ApiImplicitParam(name = "name", value = "字典值名称", dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "dictValue", value = "字典值", dataType = "Integer" ,paramType="form"),
		@ApiImplicitParam(name = "ordyBy", value = "字典排序序号", dataType = "Integer" ,paramType="form")
	})
	@PostMapping("/edit")
	public AjaxJson editStation(
								@RequestParam(required = false) Long id,
								@RequestParam(required = false) String name,
								@RequestParam(required = false) Integer dictValue,
								@RequestParam(required = false) Integer ordyBy
							){
		AjaxJson a=	getA();
		SysDict po=sysDictRs.findOne(id);
		if(po!=null){
			po.setName(name);
			po.setDictValue(dictValue);
			po.setUpdateTime(DateUtil.now());
			po.setOrdyBy( ordyBy );
			sysDictRs.saveAndFlush(po);
			a.successMsg("修改成功");
		}else{
			a.errorMsg("修改失败", "-1");
		}
		return a;
	}
	@ApiOperation(value="删除模板信息")
	@ApiImplicitParam(name = "id", value = "模板id", dataType = "Long" ,paramType="form")
	@PostMapping("/del")
	public AjaxJson delStation(	
							@RequestParam(required = false) Long id
							){
		AjaxJson a=	getA();
		sysDictRs.delete(id);
		a.successMsg("删除成功");
		return a;
	}
}
