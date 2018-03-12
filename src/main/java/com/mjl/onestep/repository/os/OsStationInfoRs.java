package com.mjl.onestep.repository.os;


import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mjl.onestep.po.os.OsStationInfo;


/**
 * 站点信息Repository类
 * @author fcj
 * @version 1.0.0
 */
@CacheConfig(cacheNames = "stationInfo")
public interface OsStationInfoRs extends JpaRepository<OsStationInfo, Long> ,JpaSpecificationExecutor<OsStationInfo> {
   
   
	 /**
	  * 根据stationUserId查询分页站点信息
	  * @param stationUserId 站点用户id
	  * @param pageable	分页对象
	  * @return Page<OsStationInfo>
	  */

	 Page<OsStationInfo> findByStationUserIdOrderByCreateTimeAsc(Long stationUserId,Pageable pageable); 
	 
	 
	 /**
	  * 根据stationUserId查询分页站点信息
	  * @param stationUserId 站点用户id
	  * @return  List<OsStationInfo>
	  */

	 List<OsStationInfo> findByStationUserId(Long stationUserId); 
	 /**
	  * 根据站点地址前缀
	  * @param prefix  站点地址前缀
	  * @return OsStationInfo
	  */
	OsStationInfo findByPrefix(String prefix); 
	/**
	 * 根据站点地址前缀和用户id
	 * @param prefix 站点前缀
	 * @param userId 用户id
	 * @return OsStationInfo
	 */

	OsStationInfo findByPrefixAndStationUserId(String prefix,Long userId); /**
	  * 根据stationUserId获取站点列表
	  * @param stationUserId 站点用户id
	  *
	  */

	 List<OsStationInfo> findByStationUserIdOrderByCreateTimeAsc(Long stationUserId); 
}
