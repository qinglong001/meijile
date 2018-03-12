package com.mjl.onestep.po.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 系统操作日志类
 * @author fcj
 *
 */
@Entity
@Table(name = "sys_log")
public class SysLog  {
	/**
	 * 主键id
	 */
	@Id
    @GeneratedValue
    @Column(nullable = false,name="id")
	private Long id;
	/**
	 * 用户名
	 */
	@Column(nullable = false,name="username")
	private String userName;
	/**
	 * 用户操作
	 */
	@Column(nullable = false,name="operation")
	private String operation;	
	/**
	 * 请求方法
	 */
	@Column(nullable = false,name="method")
	private String method;
	/**
	 * 请求参数
	 */
	@Column(nullable = false,name="params")
	private String params;
	/**
	 * 返回参数
	 */
	@Column(nullable = false,name="back_data")
	private String backData;
	/**
	 * ip地址
	 */
	@Column(nullable = false,name="ip")
	private String ip;
	/**
	 * 创建时间
	 */
	@Column(nullable = false,name="create_date")
	private Date createDate;
	/**
	 * 耗时
	 */
	@Column(nullable = false,name="spend_time")
	private Long spendTime;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	
	public String getBackData() {
		return backData;
	}
	public void setBackData(String backData) {
		this.backData = backData;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getSpendTime() {
		return spendTime;
	}
	public void setSpendTime(Long spendTime) {
		this.spendTime = spendTime;
	}
	
	
		
}
