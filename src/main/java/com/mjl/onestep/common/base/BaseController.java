package com.mjl.onestep.common.base;


import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mjl.onestep.common.utils.AjaxJson;
import com.mjl.onestep.po.sys.SysToken;
import com.mjl.onestep.po.sys.SysUser;
import com.mjl.onestep.repository.sys.SysTokenRs;
import com.mjl.onestep.repository.sys.SysUserRs;

/**
 * @author fcj
 * 所有Controller的基础类 实现了部分比较通用的方法
 * 
 * 
 */
public class BaseController {
	
	
	@Autowired
    private SysTokenRs sysTokenRs;
	@Autowired
    private SysUserRs sysUserRs;

	public String getImgId(){
		return getUserName()+"-"+ UUID.randomUUID().toString();
	}
	/**
	 * 得到返回对象AjaxJson
	 */
	public AjaxJson getA(){
		return new AjaxJson();
	}
	/**
	 * 根据token获取用户id
	 * @return token.getUserId() 用户id
	 */
	public Long getUserId(){
		String tokenStr = getRequest().getHeader("Authentication");
		SysToken token=	sysTokenRs.findTokenByToken(tokenStr);
		return token.getUserId();
	}
	/**根据token获取用户姓名
	 * @return userName
	 */
    protected String getUserNameByToken(){
		String tokenStr = getRequest().getHeader("Authentication");
		String userName="";
		if(tokenStr!=null){
			SysToken token=	sysTokenRs.findTokenByToken(tokenStr);
			userName=token.getUserName();
		}
		return userName;
	}
	/**
	 * 获取token对象
	 */
	public SysToken	getToken(){
		String tokenStr = getRequest().getHeader("Authentication");
        return sysTokenRs.findTokenByToken(tokenStr);
	}
	/**根据token获取用户对象
	 * @return SysUser user对象
	 */
	public SysUser getUser(){
		String tokenStr = getRequest().getHeader("Authentication");
		SysToken token=	sysTokenRs.findTokenByToken(tokenStr);
        return sysUserRs.getOne(token.getUserId());
	}
	/**
     * 根据token获取用户名
	 * @return 用户名 userName
	 */
	public String getUserName(){
		String userName="";
		String tokenStr = getRequest().getHeader("Authentication");
		if(tokenStr!=null){
			SysToken token=	sysTokenRs.findTokenByToken(tokenStr);
			SysUser user=	sysUserRs.getOne(token.getUserId());
			if(user!=null){
				userName=user.getUsername();
			}else{
				userName="未知用户";
			}
		}
		return userName;
	}
	

	/**
     * 得到request对象
	 * @return request  request对象
	 */
	public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}


}
