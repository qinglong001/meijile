package com.mjl.onestep.repository.sys;  
  
import org.springframework.data.repository.CrudRepository;

import com.mjl.onestep.po.sys.SysClientInfo;
  
/**
 * 客户端信息Repository类  
 * @author Administrator
 *
 */
public interface SysClientInfoRepository extends CrudRepository<SysClientInfo, String>{ 
	/**
	 * 根据客户端信息id获取ClientInfo
	 * @param clientId
	 * @return SysClientInfo
	 */
    SysClientInfo findClientByclientid(String clientId);  
} 