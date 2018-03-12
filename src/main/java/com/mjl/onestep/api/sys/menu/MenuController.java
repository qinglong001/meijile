package com.mjl.onestep.api.sys.menu;

import com.mjl.onestep.common.base.BaseController;
import com.mjl.onestep.common.utils.AjaxJson;
import com.mjl.onestep.po.sys.SysMenu;
import com.mjl.onestep.po.sys.UserMenu;
import com.mjl.onestep.repository.sys.SysMenuRs;
import com.mjl.onestep.repository.sys.UserMenuRs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 菜单接口核心控制器类
 * 
 * @author fcj
 * 
 */
@RestController
@RequestMapping("/api/menu/")
@Api(value = "菜单相关接口MenuController", tags = { "SYS系统 菜单相关接口" })
public class MenuController extends BaseController {

	@Autowired
	private SysMenuRs sysMenuRs;
	@Autowired
	private UserMenuRs userMeunRs;

	@ApiOperation(value = "获取所有主菜单")
	@PostMapping("/allMainMenu")
	public AjaxJson allMainMenu() {
		AjaxJson a = getA();
		List<SysMenu> sysMenuList = sysMenuRs.findByTypeOrderByOrderNumAsc(0);
		a.put("data", sysMenuList);
		return a;
	}

	@ApiOperation(value = "根据父菜单id获取所有子菜单")
	@ApiImplicitParam(name = "parentId", value = "父菜单id",  dataType = "Long", paramType = "form")
	@PostMapping("/allChildrenMenu")
	public AjaxJson allChildrenMenu(
			@RequestParam(required = false) Long parentId) {
		AjaxJson a = getA();
		List<SysMenu> sysMenuList = sysMenuRs
				.findByParentIdAndTypeOrderByOrderNumAsc(parentId, 1);
		a.put("data", sysMenuList);
		return a;
	}

	@ApiOperation(value = "获取该用户权限的主菜单(准备废弃)")
	@PostMapping("/mainMenu")
	public AjaxJson mainMenu() {
		AjaxJson a = getA();
		List<SysMenu> sysMenuList = sysMenuRs.findByTypeOrderByOrderNumAsc(0);
		a.put("data", sysMenuList);
		return a;
	}

	@ApiOperation(value = "根据父菜单id获取该用户权限的子菜单列表 (准备废弃)")
	@ApiImplicitParam(name = "parentId", value = "父菜单id",  dataType = "Long", paramType = "form")
	@PostMapping("/childrenMenu")
	public AjaxJson childrenMenu(@RequestParam(required = false) Long parentId) {
		AjaxJson a = getA();
		List<SysMenu> sysMenuList = sysMenuRs
				.findByParentIdAndTypeOrderByOrderNumAsc(parentId, 1);
		a.put("data", sysMenuList);
		return a;
	}

	@ApiOperation(value = "获取该用户权限的主菜单")
	@PostMapping("/getUserMainMenu")
	public AjaxJson getUserMainMenu() {
		AjaxJson a = getA();
		// 获取用户菜单
		List<UserMenu> list = userMeunRs.findUserMeunByUserIdAndType(getUserId(), 0);
		a.put("data", list);
		return a;
	}

	@ApiOperation(value = "根据父菜单id获取该用户权限的子菜单列表")
	@ApiImplicitParam(name = "parentId", value = "父菜单id",  dataType = "Long", paramType = "form")
	@PostMapping("/getUserchildrenMenu")
	public AjaxJson getUserchildrenMenu(
			@RequestParam(required = false) Long parentId) {
		AjaxJson a = getA();
		// 获取用户菜单
		List<UserMenu> list = userMeunRs.findUserMeunByUserIdAndTypeAndParentIdOrderByOrderNumAsc(getUserId(), 1, parentId);
		a.put("data", list);
		return a;
	}
	@CacheEvict(value="SysMenu", allEntries=true)
	@ApiOperation(value = "新增系统菜单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "parentId", value = "父节点id",  dataType = "Long", paramType = "form"),
			@ApiImplicitParam(name = "name", value = "菜单名称",  dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "url", value = "菜单地址",  dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "type", value = "菜单类型   0：目录   1：菜单 ",  dataType = "Integer", paramType = "form"),
			@ApiImplicitParam(name = "icon", value = "菜单图标",  dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "orderNum", value = "排序序号",  dataType = "Integer", paramType = "form"),
			@ApiImplicitParam(name = "colour", value = "菜单颜色",  dataType = "String", paramType = "form") })
	@PostMapping("/add")
	public AjaxJson add(@RequestParam(required = false) Long parentId,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String url,
			@RequestParam(required = false) Integer type,
			@RequestParam(required = false) String icon,
			@RequestParam(required = false) Integer orderNum,
			@RequestParam(required = false) String colour) {
		AjaxJson a = getA();
		SysMenu po = new SysMenu();
		po.setParentId(parentId);
		po.setName(name);
		po.setUrl(url);
		po.setType(type);
		po.setIcon(icon);
		po.setOrderNum(orderNum);
		po.setColour(colour);
		sysMenuRs.save(po);
		a.successMsg("添加成功");

		return a;
	}
	@CacheEvict(value="SysMenu", allEntries=true)
	@ApiOperation(value = "修改系统菜单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "菜单id",  dataType = "Long", paramType = "form"),
			@ApiImplicitParam(name = "name", value = "菜单名称",  dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "url", value = "菜单地址",  dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "type", value = "菜单类型   0：目录   1：菜单   ",  dataType = "Integer", paramType = "form"),
			@ApiImplicitParam(name = "icon", value = "菜单图标",  dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "orderNum", value = "排序序号",  dataType = "Integer", paramType = "form"),
			@ApiImplicitParam(name = "colour", value = "菜单颜色",  dataType = "String", paramType = "form") })
	@PostMapping("/edit")
	public AjaxJson edit(@RequestParam(required = false) Long id,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String url,
			@RequestParam(required = false) Integer type,
			@RequestParam(required = false) String icon,
			@RequestParam(required = false) Integer orderNum,
			@RequestParam(required = false) String colour) {
		AjaxJson a = getA();
		SysMenu po = sysMenuRs.findOne(id);
		if (po != null) {
			po.setName(name);
			po.setUrl(url);
			po.setType(type);
			po.setIcon(icon);
			po.setOrderNum(orderNum);
			po.setColour(colour);
			sysMenuRs.saveAndFlush(po);
			a.successMsg("修改成功");
		} else {
			a.errorMsg("修改失败", "-1");
		}
		return a;
	}
	@CacheEvict(value="SysMenu", allEntries=true)
	@ApiOperation(value = "删除系统菜单信息")
	@ApiImplicitParam(name = "id", value = "菜单id",  dataType = "Long", paramType = "form")
	@PostMapping("/del")
	public AjaxJson del(@RequestParam(required = false) Long id) {
		AjaxJson a = getA();
		// 查询该菜单是否有子菜单 有子菜单先删除子菜单
		sysMenuRs.delete(id);
		a.successMsg("删除成功");
		return a;
	}
}
