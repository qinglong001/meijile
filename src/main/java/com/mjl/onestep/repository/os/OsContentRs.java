package com.mjl.onestep.repository.os;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mjl.onestep.po.os.OsContent;


/**
 * 內容Repository类
 * @author fcj
 *
 */

public interface OsContentRs extends JpaRepository<OsContent, Long> ,JpaSpecificationExecutor<OsContent>{
   
    /**
     * 根据频道id获取该频道下的所有内容数据
     * @param stationId  站点id
     * @param templateId 模板id
     * @return List<OsContent>
     */

	List<OsContent> findByStationIdAndTemplateId(Long stationId,Long templateId);
	/**
	 * 根据频道id 模板id 标志  获取最新的内容
     * @param stationId  站点id
     * @param templateId 模板id
     * @param signId
     * @return List<OsContent>
	 */
	List<OsContent> findByStationIdAndTemplateIdAndSignIdOrderByCreateTime(Long stationId,Long templateId,String signId);
}
