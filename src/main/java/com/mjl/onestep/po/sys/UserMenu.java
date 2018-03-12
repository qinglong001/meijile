package com.mjl.onestep.po.sys;

import java.io.Serializable;
import javax.persistence.*;


/**
 * @author fcj
 * 
 */
@Entity
@Table(name="user_menu")
@NamedQuery(name="UserMenu.findAll", query="SELECT u FROM UserMenu u")
public class UserMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	
	
	private String colour;

	private String icon;
	@Id
	@Column(name="menu_id")
	private Long menuId;
	@Column(name="name")
	private String name;

	@Column(name="order_num")
	private int orderNum;

	@Column(name="parent_id")
	private Long parentId;

	@Column(name="role_id")
	private Long roleId;

	private int type;

	private String url;

	@Column(name="user_id")
	private Long userId;

	public UserMenu() {
	}

	public String getColour() {
		return this.colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}