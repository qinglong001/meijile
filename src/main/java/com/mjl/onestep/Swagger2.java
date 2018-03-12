package com.mjl.onestep;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * api 接口配置类
 * @author fcj
 *
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

	@Bean  
    public Docket api(){  
        ParameterBuilder tokenPar = new ParameterBuilder();  
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("Authentication").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();  
        pars.add(tokenPar.build());  
        return new Docket(DocumentationType.SWAGGER_2)  
            .select()  
            .apis(RequestHandlerSelectors.any())  
            .paths(PathSelectors.regex("/api/.*"))  
            .build()  
            .globalOperationParameters(pars)  
            .apiInfo(apiInfo()).select()
    				.apis(RequestHandlerSelectors.basePackage("com.mjl.onestep"))
    				.paths(PathSelectors.any()).build();  
    }
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("一步建站系统API接口文档")
				.description("项目地址请访问")
				.termsOfServiceUrl("http://www.meijile.xin/").description( "傅长江" ).version("1.0").build();
	}
}
