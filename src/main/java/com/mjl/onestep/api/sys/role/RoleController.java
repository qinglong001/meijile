package com.mjl.onestep.api.sys.role;

import com.mjl.onestep.common.base.BaseController;
import com.mjl.onestep.common.utils.AjaxJson;
import com.mjl.onestep.po.sys.SysMenu;
import com.mjl.onestep.po.sys.SysRole;
import com.mjl.onestep.po.sys.SysRoleMenu;
import com.mjl.onestep.po.sys.SysUserRole;
import com.mjl.onestep.repository.sys.SysMenuRs;
import com.mjl.onestep.repository.sys.SysRoleMenuRs;
import com.mjl.onestep.repository.sys.SysRoleRs;
import com.mjl.onestep.repository.sys.SysUserRoleRs;
import com.xiaoleilu.hutool.convert.Convert;
import com.xiaoleilu.hutool.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
 * 系统角色管理核心控制器类
 * 
 * @author fcj
 * 
 */
@RestController
@RequestMapping("/api/role/")
@Api(value = "系统角色相关接口 TemplateController /api/role/", tags = { "SYS系统 角色管理相关接口" })
public class RoleController extends BaseController {

	@Autowired
	private SysRoleRs sysRoleRs;
	@Autowired
	private SysMenuRs sysMenuRs;
	@Autowired
	private SysRoleMenuRs sysRoleMenuRs;
	@Autowired
	private SysUserRoleRs sysUserRoleRs;

	@ApiOperation(value = "分页展示系统角色列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", value = "页数从0开始",  dataType = "Integer", paramType = "form"),
			@ApiImplicitParam(name = "size", value = "每页数量",  dataType = "Integer", paramType = "form") })
	@PostMapping("/list")
	public AjaxJson list(@RequestParam(required = false) Integer page,
						 @RequestParam(required = false) Integer size) {
		AjaxJson a = getA();
		Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC,
				"createTime");
		Page<SysRole> pagePo = sysRoleRs.findAll(pageable);
		a.put("data", pagePo);
		return a;
	}

	@ApiOperation(value = "新增系统角色")
	@ApiImplicitParams({ @ApiImplicitParam(name = "roleName", value = "系统角色名称",  dataType = "String", paramType = "form") })
	@PostMapping("/add")
	public AjaxJson add(@RequestParam(required = false) String roleName) {
		AjaxJson a = getA();
		SysRole po = new SysRole();
		po.setroleName(roleName);
		po.setCreateTime(DateUtil.now());
		po.setCreateUserId(getUserId());
		sysRoleRs.save(po);
		a.successMsg("添加成功");

		return a;
	}

	@ApiOperation(value = "修改系统角色名称")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "模板id",  dataType = "Long", paramType = "form"),
			@ApiImplicitParam(name = "roleName", value = "系统角色名称",  dataType = "String", paramType = "form") })
	@PostMapping("/edit")
	public AjaxJson edit(@RequestParam(required = false) Long id,
			@RequestParam(required = false) String roleName) {
		AjaxJson a = getA();
		SysRole po = sysRoleRs.findOne(id);
		if (po != null) {
			po.setroleName(roleName);
			sysRoleRs.saveAndFlush(po);
			a.successMsg("修改成功");
		} else {
			a.errorMsg("修改失败", "-1");
		}
		return a;
	}
	@CacheEvict(value = "SysMenu", allEntries = true)
	@ApiOperation(value = "根据角色 id获取所有菜单和对应关系")
	@ApiImplicitParams({ @ApiImplicitParam(name = "roleId", value = "角色id",  dataType = "Long", paramType = "form"), })
	@PostMapping("/getMenuByroleId")
	public AjaxJson edit(@RequestParam(required = false) Long roleId) {
		AjaxJson a = getA();
		// 查询一级菜单
		List<SysMenu> sysMenuList = sysMenuRs.findByTypeOrderByOrderNumAsc(0);
		// 查询菜单角色对应关系表
		List<SysRoleMenu> sysRoleMenuList = sysRoleMenuRs.findByRoleIdOrderById(roleId);
		// 查询一级菜单下面子菜单
		for (SysMenu aSysMenuList : sysMenuList) {
			if (sysRoleMenuList.size() > 0) {
				aSysMenuList.setFlag( false );
				for (SysRoleMenu aSysRoleMenuList : sysRoleMenuList) {
					if (aSysRoleMenuList.getMenuId().equals( aSysMenuList.getMenuId() )) {
						aSysMenuList.setFlag( true );
					}
				}
			}
			List<SysMenu> subMenus = sysMenuRs.findByParentIdAndTypeOrderByOrderNumAsc( aSysMenuList.getMenuId(), 1 );
			for (int j = 0; j < subMenus.size(); j++) {
				subMenus.get( j ).setFlag( false );
				if (sysRoleMenuList.size() > 0) {
					for (SysRoleMenu aSysRoleMenuList : sysRoleMenuList) {
						if (aSysRoleMenuList.getMenuId().equals( subMenus.get( j ).getMenuId() )) {
							subMenus.get( j ).setFlag( true );
						}
					}
				}
				aSysMenuList.setSubMenus( subMenus );
			}
		}
		a.put("SysMenuList", sysMenuList);
		return a;
	}

	@CacheEvict(value = "SysMenu", allEntries = true)
	@ApiOperation(value = "更新角色权限")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "roleId", value = "角色id",  dataType = "Long", paramType = "form"),
			@ApiImplicitParam(name = "menuIds", value = "菜单id集合",  dataType = "String", paramType = "form") })
	@PostMapping("/UpdateRole")
	public AjaxJson updateRole(@RequestParam(required = false) Long roleId,
			@RequestParam(required = false) String menuIds) {
		AjaxJson a = getA();
		List<SysRoleMenu> sysRoleMenus = sysRoleMenuRs.findByRoleIdOrderById(roleId);
		sysRoleMenuRs.deleteInBatch(sysRoleMenus);
		String[] menuIdArray;
		menuIdArray = convertStrToArray(menuIds);
		List<SysRoleMenu> saveSysRoleMenu = new ArrayList<>();
		for (String aMenuIdArray : menuIdArray) {
			SysRoleMenu sysRoleMenu = new SysRoleMenu();
			sysRoleMenu.setMenuId( Convert.toLong( aMenuIdArray ) );
			sysRoleMenu.setRoleId( roleId );
			saveSysRoleMenu.add( sysRoleMenu );
		}
		sysRoleMenuRs.save(saveSysRoleMenu);
		a.successMsg("更新成功");
		return a;
	}

	@CacheEvict(value = "SysMenu", allEntries = true)
	@ApiOperation(value = "删除系统角色信息")
	@ApiImplicitParam(name = "roleId", value = "角色id",  dataType = "Long", paramType = "form")
	@PostMapping("/del")
	public AjaxJson del(@RequestParam(required = false) Long roleId) {
		AjaxJson a = getA();
		// 查询该角色下的所有用户如果没有用户就删除角色 。由用户提示先删除角色下面的用户
		List<SysUserRole> sysUserRoles = sysUserRoleRs
				.findByRoleIdOrderById(roleId);
		if (sysUserRoles.size() > 0) {
			a.errorMsg("请先清理该角色下面的用户", null);
		} else {
			sysRoleRs.delete(roleId);
			a.successMsg("删除成功");
		}

		return a;
	}

	// 使用String的split 方法
	/**
	 * 分隔逗号字符串
	 * @param str 待分隔字符串
	 * @return String[]
	 */
	@org.jetbrains.annotations.NotNull
	private static String[] convertStrToArray(String str) {
		// 拆分字符为"," ,然后把结果交给数组strArray
		return str.split(",");
	}
}
