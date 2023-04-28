package com.havad.smartcampusmanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: SmartCampusManagementSystem
 * @description: Swagger2配置类
 * @author: Havad
 * @create: 2023-04-22 21:33
 **/

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket webApiConfig(){
        List<Parameter> parameters = new ArrayList<>();

        // 设置header -> start
        ParameterBuilder tokenParameters = new ParameterBuilder();
        tokenParameters.name("userId")
                .description("用户Id")
                .defaultValue("1")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        parameters.add(tokenParameters.build());

        ParameterBuilder tokenTempParameters = new ParameterBuilder();
        tokenTempParameters.name("tempUserId")
                .description("临时用户Id")
                .defaultValue("1")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        parameters.add(tokenTempParameters.build());

        // 设置header -> end
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("scmsWebApi")
                .apiInfo(scmsWebApiInfo())
                .select()
                // 可以测试请求头中：输入token
                //.apis(RequestHandlerSelectors.withClassAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("com.havad.smartcampusmanagementsystem.controller"))
                // 过滤admin下所有的页面
                //.paths(Predicates.and(PathSelectors.regex("/scms/.*")))
                // 过滤所有的error页面
                //.paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build()
                .globalOperationParameters(parameters);

    }

    private ApiInfo scmsWebApiInfo() {

        return new ApiInfoBuilder()
                .title("智慧校园系统-Api文档")
                .description("本文档为该项目所提供的接口定义")
                .version("1.0")
                .contact(new Contact("havad", "www.HavadKing.github.io", "bobojiner@foxmail.com"))
                .build();

    }

    private ApiInfo scmsAdminWebApiInfo() {

        return new ApiInfoBuilder()
                .title("智慧校园系统-管理员Api文档")
                .description("本文档为该项目所提供管理的接口定义")
                .version("1.0")
                .contact(new Contact("havad", "www.HavadKing.github.io", "bobojiner@foxmail.com"))
                .build();

    }


}
