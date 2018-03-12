package com.mjl.onestep.api.sys.log;
import com.mjl.onestep.common.base.BaseController;
import com.mjl.onestep.common.utils.AjaxJson;
import com.mjl.onestep.po.sys.SysLog;
import com.mjl.onestep.repository.sys.SysLogRs;
import com.xiaoleilu.hutool.convert.Convert;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.*;
import java.sql.Date;
import java.util.List;
/**
 * 日志记录核心控制器类
 * @author fcj
 *
 */
@RestController
@RequestMapping("/api/log/")
@Api(value="日志相关接口  LogController",tags={"SYS系统 日志相关接口"})
public class LogController extends BaseController {
	
	@Autowired
    private SysLogRs sysLogRs;
	
	
	@ApiOperation(value="获取日志记录分页数据", notes="获取日志记录分页数据")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", value = "页数从0开始", dataType = "Integer" ,paramType="form"),
		@ApiImplicitParam(name = "size", value = "每页数量",  dataType = "Integer" ,paramType="form"),
		@ApiImplicitParam(name = "ip", value = "查询参数ip地址",  dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "startTime", value = "查询参数开始时间",  dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "endTime", value = "查询参数结束时间", dataType = "String" ,paramType="form"),
		@ApiImplicitParam(name = "spendTime", value = "查询参数耗时", dataType = "String" ,paramType="form")
	})
	@PostMapping("/list")
	public AjaxJson list(@RequestParam(required = false) Integer page,
						 @RequestParam(required = false) Integer size,
						 @RequestParam(required = false) final String ip,
						 @RequestParam(required = false) final String startTime,
						 @RequestParam(required = false) final String endTime,
						 @RequestParam(required = false) final String spendTime
	){
		AjaxJson a=	getA();
		Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "createDate"); 
		
		Page<SysLog> sysLogPage=	sysLogRs.findAll(new Specification<SysLog>() {
			@Override
			public Predicate toPredicate(
					Root<SysLog> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Path<String> ipPath = root.get("ip");  
				Path<Date> createDatePath = root.get("createDate");  
				Expression<? extends Number> spendTimePath = root.get("spendTime"); 
				if(StrUtil.isNotBlank(ip)){
					 query.where(cb.equal(ipPath, StrUtil.trim(ip))); 
				}
				if(StrUtil.isNotBlank(startTime)&&StrUtil.isNotBlank(endTime)){
					Date sdata=new Date(DateUtil.parse( startTime+" 00:00:00").getTime());
					Date edata=new Date(DateUtil.parse( endTime+" 23:59:59").getTime())	;
					query.where(cb.between(createDatePath,sdata,edata)); 
				}
				if(StrUtil.isNotBlank(spendTime)){
					 query.where(cb.gt(spendTimePath,Convert.toNumber(spendTime) )); 
				}
			    return null;  
			}
		}, pageable);
		a.put("data", sysLogPage);
		return a;
	}
	
	@ApiOperation(value="获取所有日志ip数据", notes="获取所有日志ip数据")
	@ApiImplicitParams({})
	@PostMapping("/getLogIpData")
	public AjaxJson getLogIpData(){
		AjaxJson a=	getA();
		List<SysLog> sysLogs=sysLogRs.getLogIpData();
		a.put("data", sysLogs);
		return a;
	}
}
