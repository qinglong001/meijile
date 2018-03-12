package com.mjl.onestep.repository.sys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mjl.onestep.po.sys.SysUserRole;

/**
 * 系统用户角色关联Repository类
 * 
 * @author fcj
 * 
 */

public interface SysUserRoleRs extends JpaRepository<SysUserRole, Long>,
		JpaSpecificationExecutor<SysUserRole> {

	/**
	 * 根据用户id获取用户和角色的对应关系
	 * 
	 * @param userId
	 * @return List<SysUserRole>
	 */
	List<SysUserRole> findByUserIdOrderById(Long userId);

	/**
	 * 根据角色id获取用户和角色的对应关系
	 *
	 * @param roleId
	 * @return List<SysUserRole>
	 */
	List<SysUserRole> findByRoleIdOrderById(Long roleId);

}
