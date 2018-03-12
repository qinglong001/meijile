package com.mjl.onestep.repository.sys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mjl.onestep.po.sys.SysRoleMenu;


/**
 * 系统角色菜单关系类
 * @author fcj
 *
 */

public interface SysRoleMenuRs extends JpaRepository<SysRoleMenu, Long> ,JpaSpecificationExecutor<SysRoleMenu>{
   
	
	/**
	 * 根据角色id获取角色和菜单的对应关系
	 * @param roleId 
	 * @return List<SysRoleMenu>
	 */
	 List<SysRoleMenu> findByRoleIdOrderById(Long roleId);
    
}
