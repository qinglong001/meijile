package com.mjl.onestep.repository.sys;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mjl.onestep.po.sys.SysToken;
/**
 * Token Repository类
 * @author fcj
 *
 */
public interface SysTokenRs  extends JpaRepository<SysToken, Long> {
	  /**
	   * 根据token获取token对象
	   * @param token
	   * @return SysToken
	   */
	  @Cacheable(value = "Token",key="#p0")
	  SysToken findByToken(String token);
	  /**
	   * 根据用户名id获取token 对象
	   * @param userId
	   * @return
	   */
	  @Cacheable(value = "Token",key="#p0")
	  @Query("from SysToken u where u.userId=:userId")
	  SysToken findTokenByUserId(@Param("userId") Long userId);
	  /**
	   * 根据token获取token对象
	   * @param token
	   * @return
	   */
	  @Cacheable(value = "Token",key="#p0")
	  SysToken findTokenByToken(String token);
}
