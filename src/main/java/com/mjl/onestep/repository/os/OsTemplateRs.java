package com.mjl.onestep.repository.os;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mjl.onestep.po.os.OsTemplate;


/**
 * 模板Repository类
 * @author fcj
 *
 */

public interface OsTemplateRs extends JpaRepository<OsTemplate, Long> ,JpaSpecificationExecutor<OsTemplate>{
   
	/**
	 * 根据类型获取模板信息
	 * @param type
	 * @param pageable
	 * @return Page<OsTemplate>
	 */
	Page<OsTemplate> findByType(Integer type,Pageable pageable);
    
}
