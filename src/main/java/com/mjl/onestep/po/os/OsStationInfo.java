package com.mjl.onestep.po.os;
/**
 * 站点信息类
 * @author fcj
 * 
 */
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mjl.onestep.po.sys.SysRole;

@Entity
@Table(name = "os_station_info")
public class OsStationInfo {
	 /**
     * 站点id
     */
    @Id
    @GeneratedValue
    @Column(nullable = false,name="id")
    private Long id;
    /**
     * 站点创建人Id
     */
    @Column(nullable = false,name="station_user_id")
    private Long stationUserId;
    
    /**
     * 站点地址前缀
     */
    @Column(nullable = false,name="prefix")
    private String prefix;
    /**
     * 模板id
     */
    @Column(nullable = false,name="template_id")
    private Long templateId;
    
    /**
     * 站点名称
     */
    @Column(nullable = false,name="name")
    private String name;
    /**
     * 站点人姓名
     */
    @Column(nullable = false,name="station_user_name")
    private String stationUserName;
    /**
     * 创建时间
     */
    @Column(nullable = false,name="create_time")
    private Date createTime;
    /**
     * 站点状态 
     */
    @Column(nullable = false,name="state")
    private Integer state;
    /**
     * 站点类型
     */
    @Column(nullable = false,name="type")
    private Integer type;
   
    
    /**
     * 模板信息
     */
	@Transient
    private OsTemplate  osTemplate ;
	
	
	public OsTemplate getOsTemplate() {
		return osTemplate;
	}
	public void setOsTemplate(OsTemplate osTemplate) {
		this.osTemplate = osTemplate;
	}
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getStationUserId() {
		return stationUserId;
	}
	public void setStationUserId(Long stationUserId) {
		this.stationUserId = stationUserId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStationUserName() {
		return stationUserName;
	}
	public void setStationUserName(String stationUserName) {
		this.stationUserName = stationUserName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
    
    
}
