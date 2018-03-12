package com.mjl.onestep.po.sys;

import java.util.HashSet;
import java.util.Set;

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
 * 用户与角色对应关系
 * 
 * @author fcj
 * 
 */
@Entity
@Table(name = "sys_user_role")
public class SysUserRole {

	
	/**
	 * 获取菜单信息
	 */
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private Set<SysUserRole> roles = new HashSet<SysUserRole>();
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue
	@Column(nullable = false,name="id")
	private Long id;

	/**
	 * 用户ID
	 * 
	 */
	@Column(nullable = false,name="user_id")
	private Long userId;

	/**
	 * 角色ID
	 */
	@Column(nullable = false,name="role_id")
	private Long roleId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	
	
	
}
