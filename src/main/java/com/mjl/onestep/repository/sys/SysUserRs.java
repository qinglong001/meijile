package com.mjl.onestep.repository.sys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mjl.onestep.po.sys.SysUser;


/**
 * 用户 Repository类
 * @author fcj
 *
 */

public interface SysUserRs extends JpaRepository<SysUser, Long> {
	/**
	 * 根据手机号和密码查询用户
	 * @param mobile
	 * @param password
	 * @return SysUser
	 */
    SysUser findUserByMobileAndPassword( String mobile, String password);
    /**
     * 根据手机号获取用户
     * @param mobile
     * @return SysUser
     */
    SysUser findUserByMobile( String mobile);
    
}
