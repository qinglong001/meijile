package com.mjl.onestep.repository.sys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mjl.onestep.po.sys.SysDict;


/**
 * 系统字典Repository类
 * @author fcj
 *
 */

public interface SysDictRs extends JpaRepository<SysDict, Long> ,JpaSpecificationExecutor<SysDict>{
   
    
}
