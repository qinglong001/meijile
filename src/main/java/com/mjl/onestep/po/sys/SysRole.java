package com.mjl.onestep.po.sys;

import java.util.ArrayList;
import java.util.Collection;
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
 * 
 * @author fcj
 *	用户角色主题类
 */
@Entity
@Table(name = "sys_role")
public class SysRole {
	
	

	/**
	 * 角色id 主键
	 */
	@Id
	@GeneratedValue
	@Column(nullable = false,name="role_id")
	private Long roleId;
	
	/**
	 * 角色名称
	 */
	@Column(nullable = false,name="role_name")
	private String roleName;

	/**
	 * 备注
	 */
	@Column(nullable = false,name="remark")
	private String remark;
	/**
	 * 创建者id
	 */
	@Column(nullable = false,name="create_user_id")
	private Long createUserId;
	/**
	 * 创建时间
	 */
	
	@Column(nullable = false,name="create_time")
	private String createTime;
	
	

	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getroleName() {
		return roleName;
	}
	public void setroleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
	
	
}
