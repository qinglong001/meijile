package com.mjl.onestep.po.sys;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * 用户Token
 * @author fcj
 * @date 2017-05-23 15:22:07
 */
@Entity
@Table(name = "os_token")
public class SysToken  {
	/**
	 * 用户id
	 */
	@Id
	@Column(nullable = false,name="user_id")
	private Long userId;
	/**
	 * token
	 */
	@Column(nullable = false)
	private String token;
	/**
	 * 过期时间
	 */
	@Column(nullable = false,name="expire_time")
	private Date expireTime;
	/**
	 * 更新时间
	 */
	@Column(name="update_time")
	private Date updateTime;	
	
	/**
	 * 用户名
	 */
	@Column(name="user_name")
	private String userName;

	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	


	
}
