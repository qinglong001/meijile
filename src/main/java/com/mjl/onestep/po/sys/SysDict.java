package com.mjl.onestep.po.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统字典类
 * @author fcj
 *
 */
@Entity
@Table(name = "sys_dict")
public class SysDict {
	 /**
     * 主键id
     */
    @Id
    @GeneratedValue
    @Column(nullable = false,name="id")
    private Long id;
    /**
     * 字典值名称
     */
    @Column(nullable = false,name="name")
    private String name;
    /**
     * 字典编码
     */
    @Column(nullable = false,name="bianma")
    private String bianma;
    /**
     * 字典值
     */
    @Column(nullable = false,name="dict_value")
    private Integer dictValue;
    /**
     * 字典排序序号
     */
    @Column(nullable = false,name="ordy_by")
    private Integer ordyBy;
   
    /**
     *创建时间
     */
    @Column(nullable = false,name="create_time")
    private String createTime;
    /**
     *更新时间
     */
    
    @Column(nullable = false,name="update_time")
    private String updateTime;
    /**
     * 创建人id
     */
    @Column(nullable = false,name="create_user_id")
    private Long createUserId;
    
    
    
    
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBianma() {
		return bianma;
	}
	public void setBianma(String bianma) {
		this.bianma = bianma;
	}
	public Integer getDictValue() {
		return dictValue;
	}
	public void setDictValue(Integer dictValue) {
		this.dictValue = dictValue;
	}
	public Integer getOrdyBy() {
		return ordyBy;
	}
	public void setOrdyBy(Integer ordyBy) {
		this.ordyBy = ordyBy;
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
    
    
}
