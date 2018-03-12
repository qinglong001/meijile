package com.mjl.onestep.po.sys;


import java.util.HashSet;
import java.util.List;
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
import javax.persistence.Transient;



/**
 *	oneStep系统用户类
 * @author fcj
 *
 */
@Entity
@Table(name = "os_user")
public class SysUser {
	

	/**
	 * 用户名id
	 */
    @Id
    @GeneratedValue
    @Column(nullable = false,name="user_id")
    private Long userId;
    /**
     * 用户名
     */
    @Column(nullable = false)
    private String username;
    /**
     * 手机
     */
	@Column(nullable = false)
    private String mobile;
	 /**
     * 密码
     */
    @Column(nullable = false)
    private String password;
    /**
     * 创建时间
     */
    @Column(nullable = false,name="create_time")
    private String createTime;
    
    
  
	/**
     * 用户对应角色
     */
	@Transient
    private List<SysRole> roles ;
	
    
    
    
    
  

	
	public List<SysRole> getRoles() {
		return roles;
	}
	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
