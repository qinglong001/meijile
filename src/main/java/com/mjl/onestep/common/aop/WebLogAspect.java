package com.mjl.onestep.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mjl.onestep.common.base.BaseController;
import com.mjl.onestep.po.sys.SysLog;
import com.mjl.onestep.repository.sys.SysLogRs;
import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.json.JSONObject;
import com.xiaoleilu.hutool.json.JSONUtil;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;

/**
 * Web层日志切面
 *
 * @author fcj
 * 
 */
@Aspect
@Order(5)
@Component
public class WebLogAspect  extends BaseController{

	private final SysLogRs sysLogRs;
	/**
	 *日志最大存储字符 数量 
	 */
	private static final int  SIZE = 2000;

	private ThreadLocal<Long> startTime = new ThreadLocal<>();
    private ThreadLocal<SysLog> tolog = new ThreadLocal<>();

	@Autowired
	public WebLogAspect(SysLogRs sysLogRs) {
		this.sysLogRs = sysLogRs;
	}

	@Pointcut("execution(public * com.mjl.onestep.api..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
    	SysLog sysLog =new SysLog();
        startTime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes!=null){
        	HttpServletRequest request = attributes.getRequest();
            sysLog.setIp( request.getRemoteAddr() );
            sysLog.setMethod(request.getMethod()+":"+ joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            sysLog.setCreateDate(DateUtil.date());
            sysLog.setParams(Arrays.toString(joinPoint.getArgs()));
            sysLog.setUserName(getUserNameByToken());
            tolog.set(sysLog);	
        }
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
    	SysLog sysLog =tolog.get();
    	tolog.remove();
    	JSONObject jsonObject = JSONUtil.parseObj(ret);
    	if(startTime.get()!=null){
    		sysLog.setSpendTime(System.currentTimeMillis() - startTime.get());
    		startTime.remove();
    	}
    	if(jsonObject.toString().length() > SIZE){
    		String backData=jsonObject.toString().substring(0,SIZE);
    		sysLog.setBackData(backData);
    	}else{
    		sysLog.setBackData(jsonObject.toString());
    	}
    	
    	sysLogRs.save(sysLog);
    	
    
    }


}

