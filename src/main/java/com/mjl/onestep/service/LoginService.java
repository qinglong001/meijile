package com.mjl.onestep.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.mjl.onestep.po.sys.SysToken;
import com.mjl.onestep.repository.sys.SysTokenRs;
/**
 * 登录Service操作
 * @author fcj
 *
 */
@Service
public class LoginService {
	
	@Autowired
    private SysTokenRs sysTokenRs;
	@CacheEvict(value="Token", allEntries=true)
	public SysToken createToken(Long userId,String userName) {
		//生成一个token
		String token = UUID.randomUUID().toString();
		//当前时间
		Date now = new Date();
		 final int  expire = 3600 * 12;
		//过期时间
		Date expireTime = new Date(now.getTime() + expire * 1000);
		//判断是否生成过token
		SysToken tokenEntity = sysTokenRs.findTokenByUserId(userId);
		if(tokenEntity == null){
			SysToken	addToken = new SysToken();
			addToken.setUserId(userId);
			addToken.setToken(token);
			addToken.setUpdateTime(now);
			addToken.setExpireTime(expireTime);
			addToken.setUserName(userName);
			//保存token
			addToken=sysTokenRs.save(addToken);
			return addToken;
		}else{
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);
			tokenEntity.setUserName(userName);
			//更新token
			tokenEntity=	sysTokenRs.save(tokenEntity);
			return tokenEntity;
		}

	}
}
