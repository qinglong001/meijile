package com.mjl.onestep;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.mjl.onestep.common.interceptor.AuthorizationInterceptor;
/**
 * 项目核心配置启动类
 * @author fcj
 *
 */
@SpringBootApplication
@ServletComponentScan
@EnableCaching
public class ApplicationConfig extends WebMvcConfigurerAdapter {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationConfig.class, args);
	}

    /**
     * SocketIO 地址
     */
	@Value("${wss.server.host}")
	private String host;
    /**
     * SocketIO 端口
     */
	@Value("${wss.server.port}")
	private Integer port;

    /**
     * 配置SocketIO服务端
     * @return SocketIOServer
     */
	@Bean
	public SocketIOServer socketIOServer() {
		Configuration config = new Configuration();
		config.setPort(port);
		// 该处可以用来进行身份验证
		config.setAuthorizationListener(new AuthorizationListener() {
			@Override
			public boolean isAuthorized(HandshakeData data) {
				return true;
			}
		});
		return new SocketIOServer(config);
	}

    /**
     * 配置SocketIO服务端
     * @param socketServer
     * @return SpringAnnotationScanner
     */
	@Bean
	public SpringAnnotationScanner springAnnotationScanner(
			SocketIOServer socketServer) {
		return new SpringAnnotationScanner(socketServer);
	}


    
	/**
	 * 配置拦截器 api目录下面接口需要权限(Token)验证
	 * @author lance
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthorizationInterceptor())
				.addPathPatterns("/api/**");
		super.addInterceptors(registry);
	}
	
	 /**  
     * 文件上传配置  
     * @return  
     */  
    @Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        //单个文件最大  
        factory.setMaxFileSize("10240KB"); //KB,MB  
        /// 设置总上传数据总大小  
        factory.setMaxRequestSize("102400KB");  
        return factory.createMultipartConfig();  
    }  
}
