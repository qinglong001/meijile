package com.mjl.onestep.po.os;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * 频道channel
 * @author fcj
 * @date 2017-05-23 15:22:07
 */
@Entity
@Table(name = "os_channel")
public class OsChannel  {
	/**
	 * 频道主键id
	 */
	@Id
	@GeneratedValue
	@Column(nullable = false, name = "id")
	private Long id;
	
	/**
	 * 站点id
	 */
	@Column(nullable = false,name="stationl_id")
	private Long stationlId;
	
	/**
	 * 模板id
	 */
	@Column(nullable = false,name="template_id")
	private Long templateId;
	/**
	 * 频道英文名称
	 */
	@Column(nullable = false,name="en_name")
	private String enName;
	/**
	 * 频道中文名称
	 */
	@Column(nullable = false,name="zh_name")
	private String zhName;
	/**
	 * 频道位置
	 */
	@Column(nullable = false,name="position")
	private Integer position;
	/**
	 * 链接地址
	 */
	@Column(nullable = false,name="linkurl")
	private String linkurl;
	/**
	 * 频道父id
	 */
	@Column(nullable = false,name="parent_id")
	private Long parentId;
	/**
	 * 频道等级
	 */
	@Column(nullable = false,name="level")
	private Integer level;
	/**
	 * 创建时间
	 */
	@Column(nullable = false,name="create_time")
	private String createTime;
	/**
	 *  开启状态1开启2关闭3维护
	 */
	@Column(nullable = false,name="status")
	private Integer status;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getStationlId() {
		return stationlId;
	}
	public void setStationlId(Long stationlId) {
		this.stationlId = stationlId;
	}
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
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
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public String getLinkurl() {
		return linkurl;
	}
	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
