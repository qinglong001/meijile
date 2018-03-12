package com.mjl.onestep.po.os;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



/**
 * 模板template
 * @author fcj
 * @date 2017-05-23 15:22:07
 * 
 */
@Entity
@Table(name = "os_template")
@ApiModel(value="模板对象",description="模板对象template")
public class OsTemplate  {
	/**
	 * 模板主键id   主鍵
	 */
	@Id
	@GeneratedValue
	@Column(nullable = false,name="id")
	@ApiModelProperty(value="主键id",name="id",example="1")
	private Long id;
	
	/**
	 * 模板标题
	 */
	@Column(nullable = false,name="title")
	private String title;
	/**
	 * 模板介绍
	 */
	@Column(nullable = false,name="info")
	private String info;
	/**
	 * 模板预览地址
	 */
	@Column(nullable = false,name="linkurl")
	private String linkurl;
	/**
	 *模板类型 1、个人2、企业、3、销售
	 */
	@Column(nullable = false,name="type")
	private Integer type;
	/**
	 * 开启状态1开启2关闭
	 */
	@Column(nullable = false,name="status")
	private Integer status;
	/**
	 * 缩略图1
	 */
	@Column(nullable = false,name="pic1")
	private String pic1;
	/**
	 * 缩略图2
	 */
	@Column(nullable = false,name="pic2")
	private String pic2;
	/**
	 * 缩略图3
	 */
	@Column(nullable = false,name="pic3")
	private String pic3;
	
	/**
	 * 创建时间
	 */
	@Column(nullable = false,name="create_time")
	private String createTime;
	/**
	 * 创建人
	 */
	@Column(nullable = false,name="create_user_id")
	private Long createUserId;
	
	/**
	 * 最后更新时间
	 */
	@Column(nullable = false,name="update_time")
	private String updateTime;
	/**
	 * 最后更新人id
	 */
	@Column(nullable = false,name="update_user_id")
	private Long updateUserId;
	
	/**
     * 模板预览路径
     */
	@Transient
    private String linkUrlstr ;
	
	/**
     * 模板预览路径
     */
	@Transient
    private String typeStr ;
	/**
     * 图片外链地址
     */
	@Transient
    private String pic1Str ;
	/**
     * 模板预览路径
     */
	@Transient
    private String statusStr ;
	
	//get set方法-----------------------------------------------------------------
	
	
	public String getUpdateTime() {
		return updateTime;
	}
	public String getTypeStr() {
		return typeStr;
	}
	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}
	public String getPic1Str() {
		return pic1Str;
	}
	public void setPic1Str(String pic1Str) {
		this.pic1Str = pic1Str;
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	public String getLinkUrlstr() {
		return linkUrlstr;
	}
	public void setLinkUrlstr(String linkUrlstr) {
		this.linkUrlstr = linkUrlstr;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Long getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}
	public String getPic1() {
		return pic1;
	}
	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}
	public String getPic2() {
		return pic2;
	}
	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}
	public String getPic3() {
		return pic3;
	}
	public void setPic3(String pic3) {
		this.pic3 = pic3;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getLinkurl() {
		return linkurl;
	}
	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
