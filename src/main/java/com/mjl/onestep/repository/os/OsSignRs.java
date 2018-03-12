package com.mjl.onestep.repository.os;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mjl.onestep.po.os.OsSign;


/**
 * 頻道Repository类
 * @author fcj
 *
 */

public interface OsSignRs extends JpaRepository<OsSign, Long> ,JpaSpecificationExecutor<OsSign>{
   
}
