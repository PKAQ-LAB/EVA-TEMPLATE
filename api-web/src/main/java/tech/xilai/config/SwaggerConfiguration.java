package tech.xilai.config;

import com.google.common.base.Predicate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * swagger配置类
 * Datetime: 2016-11-25 11:44
 * @author PKAQ
 * @Profile()
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "spring.profiles", name = "active", havingValue = "dev")
public class SwaggerConfiguration {
    //TODO 需要修改方法名以及内部的相关配置,一般为 "项目名API"
    @Bean
    public Docket veinApi() {
        Predicate<RequestHandler> vein = RequestHandlerSelectors.basePackage("com.template");

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Template")
                .securitySchemes(List.of(
                        new ApiKey("Authorization", "Authorization", "header")))
                .apiInfo(apiInfo())
                .select()
                .apis(vein)
                .paths(PathSelectors.any())
                .build();
    }
    //TODO 需要修改配置
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("模板项目")
                .description("http://xilai.tech")
                .termsOfServiceUrl("http://xilai.tech/")
                .contact(new Contact("W.F","http://xilai.tech","pkaq@msn.com"))
                .version("1.0")
                .build();
    }
}
