package com.mjl.onestep.repository.sys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.mjl.onestep.po.sys.SysLog;


/**
 * 系统日志Repository类
 * @author fcj
 *
 */

public interface SysLogRs extends JpaRepository<SysLog, Long> ,JpaSpecificationExecutor<SysLog>{
   
	
	/**
	 * 获取ip数据
	 * @return List<SysLog>
	 */
	
	@Query("SELECT syslog.ip	FROM	SysLog syslog	GROUP BY	syslog.ip")
	List<SysLog>	getLogIpData();
}
