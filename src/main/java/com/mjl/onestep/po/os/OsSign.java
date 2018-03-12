package com.mjl.onestep.po.os;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * 内容标志sign
 * @author fcj
 * @date 2017-05-23 15:22:07
 */
@Entity
@Table(name = "os_sign")
public class OsSign  {
	/**
	 * 内容标志主键id
	 */
	@Id
	@GeneratedValue
	@Column(nullable = false,name="id")
	private Long id;
	
	/**
	 * 内容标志英文名称
	 */
	@Column(nullable = false,name="en_name")
	private String enName;
	/**
	 * 内容标志中文名称
	 */
	@Column(nullable = false,name="zh_name")
	private String zhName;
	/** 
	 * 内容标志类型 1、文字2、图片3、链接
	 */
	@Column(nullable = false,name="content_type")
	private Integer contentType;
	/**
	 * 创建时间
	 */
	@Column(nullable = false,name="create_time")
	private String createTime;
	/**
	 * 更新时间
	 */
	@Column(nullable = false,name="update_time")
	private String updateTime;
	
	/**
	 * 创建人
	 */
	@Column(nullable = false,name="create_user_id")
	private Long createUserId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getZhName() {
		return zhName;
	}

	public void setZhName(String zhName) {
		this.zhName = zhName;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	
	
	
	
}
