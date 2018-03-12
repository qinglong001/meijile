package com.mjl.onestep.repository.sys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mjl.onestep.po.sys.SysMenu;
/**
 * 菜单目录Repository类
 * @author fcj
 *
 */
public interface SysMenuRs extends JpaRepository<SysMenu, Long>{
	/**
	 * 根据类型获取目录
	 * @param type 
	 * @return List<SysMenu>
	 */
	 List<SysMenu> findByTypeOrderByOrderNumAsc(int type);
	 /**
	  * 根据父目录的id和类型获取子目录信息
	  * @param parentId
	  * @param type
	  * @return  List<SysMenu>
	  */
	 List<SysMenu> findByParentIdAndTypeOrderByOrderNumAsc(Long parentId,int type);
}
