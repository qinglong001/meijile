package com.mjl.onestep.repository.os;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mjl.onestep.po.os.OsChannel;


/**
 * 頻道Repository类
 * @author fcj
 *
 */

public interface OsChannelRs extends JpaRepository<OsChannel, Long> ,JpaSpecificationExecutor<OsChannel>{
   
	 /**
	  * 根据站点id  查询频道信息
	  * @param stationId
	  * @return List<OsChannel>
	  */

	@Cacheable(value = "Channel",key="#p0")
	List<OsChannel> findByStationlIdOrderByCreateTimeAsc(Long stationId); 
}
