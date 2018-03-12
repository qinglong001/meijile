package com.mjl.onestep.po.sys;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 系统目录类
 * @author fcj
 * 
 */
@Entity
@Table(name = "sys_menu")
public class SysMenu implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键menuId
	 */
	@Id
    @GeneratedValue
    @Column(nullable = false,name="menu_id")
	private Long menuId;
	
	/**
	 * 父菜单ID，一级菜单为0
	 */
	@Column(nullable = false,name="parent_id")
	private Long parentId;
	/**
	 *菜单名称 
	 */
	@Column(nullable = false)
	private String name;
	/**
	 * 菜单URL
	 */
	@Column(nullable = false)
	private String url;
	/**
	 * 授权(多个用逗号分隔，如：user:list,user:create)
	 */
	@Column(nullable = false)
	private String perms;
	/**
	 * 类型     0：目录   1：菜单   2：按钮
	 */
	@Column(nullable = false)
	private Integer type;
	/**
	 * 菜单图标
	 */
	@Column(nullable = false)
	private String icon;
	/**
	 * 排序号
	 */
	@Column(nullable = false,name="order_num")
	private Integer orderNum;
	/**
	 * 颜色
	 */
	@Column(nullable = false,name="colour")
	private String colour;
	
    /**
     * 菜单权限标志
     */
	@Transient
    private boolean flag ;
    
    
    
	/**
     * 子菜单
     */
	@Transient
    private List<SysMenu> subMenus ;
	
	
	
	
	

	public List<SysMenu> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<SysMenu> subMenus) {
		this.subMenus = subMenus;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	
	
}
