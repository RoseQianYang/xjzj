package com.yunqi.jhf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@PropertySource("classpath:swagger.properties")
@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {
	ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("季候风项目 API 定义")
				.description("全局返回{code: '',info: '',data: ''}，1代表请求未成功处理，0代表成功处理，-1表示登陆过期"
						+ "info为错误信息，正确处理后信息为success，" + "data描述位于操作下,只给出结构以及属性名描述，细节描述对照文档")
				.license("").licenseUrl("").termsOfServiceUrl("").version("1.0").build();
	}

	@Bean
	public Docket addUserDocket() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2);
		docket.select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build().apiInfo(apiInfo());
		return docket;
	}

}
