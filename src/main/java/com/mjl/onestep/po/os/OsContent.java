package com.mjl.onestep.po.os;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * 内容表 os_content
 * @author fcj
 * @date 2017-05-23 15:22:07
 */
@Entity
@Table(name = "os_content")
public class OsContent  {
	/**
	 * 内容主键id
	 */
	@Id
	@GeneratedValue
	@Column(nullable = false,name="id")
	private Long id;
	/**
	 * 站点id
	 */
	@Column(nullable = false,name="station_id")
	private Long stationId;
	/**
	 * 模板id
	 */
	@Column(nullable = false,name="template_id")
	private Long templateId;
	/**
	 *频道id
	 */
	@Column(nullable = false,name="channel_id")
	private Long channelId;
	/**
	 *内容标志id
	 */
	@Column(nullable = false,name="sign_id")
	private String signId;
	
	/**
	 * 创建人
	 */
	@Column(nullable = false,name="user_id")
	private Long userId;
		
	/**
	 * 内容
	 */
	@Column(nullable = false,name="content_info")
	private String contentInfo;
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
	 * 内容描述说明
	 */
	@Column(nullable = false,name="content_remark")
	private String contentRemark;
	/**
	 * 内容描述说明
	 */
	@Column(nullable = false,name="type")
	private Integer type;

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getChannelId() {
		return channelId;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}



	public String getSignId() {
		return signId;
	}


	public void setSignId(String signId) {
		this.signId = signId;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getContentInfo() {
		return contentInfo;
	}


	public void setContentInfo(String contentInfo) {
		this.contentInfo = contentInfo;
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


	public String getContentRemark() {
		return contentRemark;
	}


	public void setContentRemark(String contentRemark) {
		this.contentRemark = contentRemark;
	}


	public Long getStationId() {
		return stationId;
	}


	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}


	public Long getTemplateId() {
		return templateId;
	}


	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	
	
	
}
