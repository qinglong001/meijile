package com.mjl.onestep.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
/**
 * 站点相关的辅助类
 * @author fcj
 *
 */
@Component
@Service
public class StationService {
	
	@Value("${domain.name}")
	private  String  domain;
	/**
	 *日志最大存储字符 数量 
	 */
	private static final String  NO_PREFIX_STR = "127.0.0.1/";
	
	
	public String getPrefix(HttpServletRequest request){
		StringBuffer url = request.getRequestURL();  
		String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString(); 
		//截取域名获取站点前缀
		String prefix=tempContextUrl.replace(domain, "");
		prefix=	prefix.replaceAll("http://","");
		if(NO_PREFIX_STR.equals(prefix)){
			prefix = null;
		}
		return prefix;
		
	}

}
