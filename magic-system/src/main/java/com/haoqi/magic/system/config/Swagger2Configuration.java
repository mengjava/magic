package com.haoqi.magic.system.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @author yanhao
 * @date 2018/7/17 11:23
 * swagger 接口文档
 * @return
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(paths())
                .build()
                .apiInfo(apiInfo());
    }

    private Predicate<String> paths() {
        return or(
                regex("/sysAdvertConfig.*"),
		        regex("/smsSend.*"),
		        regex("/sysArea.*"),
		        regex("/base.*"),
		        regex("/user.*"),
		        regex("/sysConfig.*"),
		        regex("/buyerUser.*"),
		        regex("/sysRegion.*"));
    }

    private ApiInfo apiInfo() {
        String title = "magic-system-service";
        String termsOfServiceUrl = "";
        String version = "1.0-SNAPSHOT";
        Contact contact = new Contact("车源平台 ", "", "");
        ApiInfo apiInfo = new ApiInfoBuilder().title(title).termsOfServiceUrl(termsOfServiceUrl)
                .contact(contact).version(version).build();
        return apiInfo;
    }
}
