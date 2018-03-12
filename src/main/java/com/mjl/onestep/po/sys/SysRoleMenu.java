package com.mjl.onestep.po.sys;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;




/**
 * 角色与菜单对应关系
 * 
 * @author fcj
 */
@Entity
@Table(name = "sys_role_menu")
public class SysRoleMenu  {
	

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue
	@Column(nullable = false,name="id")
	private Long id;

	/**
	 * 角色ID
	 */
	@Column(nullable = false,name="role_id")
	private Long roleId;

	/**
	 * 菜单ID
	 */
	@Column(nullable = false,name="menu_id")
	private Long menuId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	
	


	
	
	
	
}
