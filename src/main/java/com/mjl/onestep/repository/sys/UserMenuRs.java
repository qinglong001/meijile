package com.mjl.onestep.repository.sys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mjl.onestep.po.sys.UserMenu;


/**
 * 用户 Repository类
 * @author fcj
 *
 */

public interface UserMenuRs extends JpaRepository<UserMenu, Long> {
	/**
	 * 根据用户id和类型获取下面的
	 * @param userId 用户id
	 * @param type  类型
	 * @return List<UserMenu>
	 */
	List<UserMenu> findUserMeunByUserIdAndType( Long userId ,int type);
	/**
	 * 根据用户id和父节点id和类型获取下面的
	 * @param userId 用户id
	 * @param type  类型
	 * @param parentId  父节点id
	 * @return List<UserMenu>
	 */
	List<UserMenu> findUserMeunByUserIdAndTypeAndParentIdOrderByOrderNumAsc( Long userId ,int type,Long parentId );
   
    
}
