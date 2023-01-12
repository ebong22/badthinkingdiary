package ebong.badthinkingdiary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(false)
//                .globalRequestParameters(parameterList())
                .select()
                .apis(RequestHandlerSelectors.basePackage("ebong.badthinkingdiary"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                ;
    }

    public List<RequestParameter> parameterList(){
        RequestParameterBuilder parameterBuilder = new RequestParameterBuilder();
        List<RequestParameter> params = new ArrayList<>();

        setParamAccessToken(parameterBuilder, params); // 추후 다른 parameter 추가 필요시 동일하게 추가

        return params;
    }

    private List<RequestParameter> setParamAccessToken(RequestParameterBuilder parameterBuilder, List<RequestParameter> params) {
         params.add ( parameterBuilder.name("X-AUTH-TOKEN")
                                     .description("Access Token")
                                     .in(ParameterType.HEADER)
                                     .build()
                    );
         return params;
    }

    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("BadThinkingDiary")
                .description("BadThinkingDiary Apis")
                .version("1.0")
                .build();
    }

    private ApiKey apiKey(){
     return new ApiKey("X-AUTH-TOKEN", "X-AUTH-TOKEN", "header");
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = new AuthorizationScope("global", "accessEverything");
        return List.of(new SecurityReference("X-AUTH-TOKEN", authorizationScopes));
    }

}
