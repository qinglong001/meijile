package com.mjl.onestep.repository.sys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mjl.onestep.po.sys.SysRole;
import java.lang.Long;
import java.util.List;


/**
 * 系统角色Repository类
 * @author fcj
 *
 */

public interface SysRoleRs extends JpaRepository<SysRole, Long> ,JpaSpecificationExecutor<SysRole>{
	
	/**
	 * 根据角色id获取角色列表
	 * @param roleid 角色id
	 * @return List<SysRole>
	 */

    List<SysRole> findByRoleId(Long roleid);
}
