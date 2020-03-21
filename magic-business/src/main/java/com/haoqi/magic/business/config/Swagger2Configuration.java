package com.haoqi.magic.business.config;

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
                regex("/csAccountDetail.*"),
                regex("/payment.*"),
                regex("/carInfo.*"),
                regex("/carAudit.*"),
                regex("/carFile.*"),
                regex("/carCheck.*"),
                regex("/carConfig.*"),
                regex("/carProcedure.*"),
                regex("/csLoanCredit.*"),
                regex("/csCustomBuilt.*"),
                regex("/console.*"),
                regex("/car/home.*"),
                regex("/csCarDealer.*"),
                regex("/csFilter.*"),
                regex("/csHitTagRelative.*"),
                regex("/csParam.*"),
                regex("/csVip.*"),
                regex("/csTag.*"),
                regex("/csServiceFee.*"),
                regex("/csTransferRecord.*"),
                regex("/carSource.*"),
                regex("/csDisputeItem.*"),
                regex("/csPayConfig.*"),
                regex("/pushMessage.*"),
                regex("/csCarOrder.*"),
                regex("/csOrderRecheckFile.*"),
                regex("/car/app.*"),
                regex("/csOrderFile.*"),
                regex("/csCarSeller.*"),
                regex("/open.*"),
                regex("/csCash.*"),
                regex("/csFinancePayMoney.*"),
                regex("/csUserVip.*"),
                regex("/csUserBankCard.*"),
                regex("/test.*"));
    }

    private ApiInfo apiInfo() {
        String title = "magic-business";
        String termsOfServiceUrl = "";
        String version = "1.0-SNAPSHOT";
        Contact contact = new Contact("车源平台 ", "", "");
        ApiInfo apiInfo = new ApiInfoBuilder().title(title).termsOfServiceUrl(termsOfServiceUrl)
                .contact(contact).version(version).build();
        return apiInfo;
    }
}
