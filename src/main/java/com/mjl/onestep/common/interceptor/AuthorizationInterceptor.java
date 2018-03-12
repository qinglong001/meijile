package com.mjl.onestep.common.interceptor;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mjl.onestep.common.exception.MyException;
import com.mjl.onestep.po.sys.SysToken;
import com.mjl.onestep.repository.sys.SysTokenRs;

/**
 * 权限(Token)验证
 * @author fcj
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter  {
	
	
	
    private static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";
    @Override
 
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        IgnoreAuth annotation;
        //所有请求返回返回都返回的Origin
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Authentication");
        if(handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
        }else{
            return true;
        }
        //如果有@IgnoreAuth注解，则不验证token
        if(annotation != null){
            return true;
        }
        //从header中获取token
        String token = request.getHeader("Authentication");
        
        //如果header中不存在token，则从参数中获取token
        if(null==token){
            token = request.getParameter("token");
        }
        //token为空
        if(null==token){
           throw new MyException("token不能为空");
        }
        //查询token信息
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext()); 
        SysTokenRs  tokenRs= (SysTokenRs) factory.getBean("sysTokenRs"); 
        SysToken  tokenEntity = tokenRs.findByToken(token);
       if(tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()){
            throw new MyException("token失效，请重新登录");
        }
        request.setAttribute(LOGIN_USER_KEY, tokenEntity.getUserId());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,  
            HttpServletResponse response, Object handler, Exception ex) {
    }  
}
