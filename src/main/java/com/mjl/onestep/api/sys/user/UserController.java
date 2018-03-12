package com.mjl.onestep.api.sys.user;

import com.mjl.onestep.common.base.BaseController;
import com.mjl.onestep.common.utils.AjaxJson;
import com.mjl.onestep.po.sys.SysRole;
import com.mjl.onestep.po.sys.SysUser;
import com.mjl.onestep.po.sys.SysUserRole;
import com.mjl.onestep.repository.sys.SysRoleRs;
import com.mjl.onestep.repository.sys.SysUserRoleRs;
import com.mjl.onestep.repository.sys.SysUserRs;
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

import java.util.ArrayList;
import java.util.List;
/**
 * 系统用户管理核心控制器类
 * @author fcj
 *
 */
@RestController
@RequestMapping("/api/user/")
@Api(value="系统用户相关接口 TemplateController /api/user/",tags={"SYS系统 用户管理相关接口"})

public class UserController extends BaseController {
	
	@Autowired
    private SysUserRs sysUserRs;
	@Autowired
    private SysUserRoleRs sysUserRoleRs;
	@Autowired
    private SysRoleRs sysRoleRs;
	
	
	@ApiOperation(value="获取所有系统用户")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", value = "页数从0开始",  dataType = "Integer" ,paramType="form"),
		@ApiImplicitParam(name = "size", value = "每页数量",  dataType = "Integer" ,paramType="form")
	})
	@PostMapping("/list")
	public AjaxJson mainMenu(@RequestParam(required = false) Integer page,
							 @RequestParam(required = false) Integer size
	){
		AjaxJson a=	getA();
		Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "createTime"); 
		Page<SysUser> pagePo=	sysUserRs.findAll(pageable);
		
		for (int i = 0; i < pagePo.getContent().size(); i++) {
			List<SysRole> sysRoles = new ArrayList<>();
			List<SysUserRole> sysUserRoles	=sysUserRoleRs.findByUserIdOrderById(	pagePo.getContent().get(i).getUserId());
			for (SysUserRole sysUserRole : sysUserRoles) {
				System.out.println( sysUserRole.getRoleId() );
				List<SysRole> sysRoleList = sysRoleRs.findByRoleId( sysUserRole.getRoleId() );
				if (sysRoleList.size() > 0) {
					sysRoles.add( sysRoleList.get( 0 ) );
				}

			}
			pagePo.getContent().get(i).setRoles(sysRoles);
		}
		a.put("data", pagePo);
		return a;
	}
	
	@ApiOperation(value="更新用户角色")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "roleId", value = "角色id",  dataType = "Long" ,paramType="form"),
		@ApiImplicitParam(name = "userId", value = "用户id",  dataType = "Long" ,paramType="form")
	})
	@PostMapping("/edit")
	public AjaxJson editStation(
								@RequestParam(required = false) Long roleId,
								@RequestParam(required = false) Long userId
							){
		AjaxJson a=	getA();
		List<SysUserRole> 	sysUserRoleList=sysUserRoleRs.findByUserIdOrderById(userId);
		sysUserRoleRs.delete(sysUserRoleList);
		SysUserRole sysUserRole=new SysUserRole();
		sysUserRole.setRoleId(roleId);
		sysUserRole.setUserId(userId);
		sysUserRoleRs.save(sysUserRole);
		
		return a;
	}
	@ApiOperation(value="删除用户信息")
	@ApiImplicitParam(name = "id", value = "模板id",  dataType = "Long" ,paramType="form")
	@PostMapping("/del")
	public AjaxJson delStation(	
							@RequestParam(required = false) Long id
							){
		AjaxJson a=	getA();
		sysUserRs.delete(id);
		a.successMsg("删除成功");
		return a;
	}
}
