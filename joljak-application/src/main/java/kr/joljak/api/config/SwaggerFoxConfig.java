package kr.joljak.api.config;

import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerFoxConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
      .build()
      .securitySchemes(apiKey())
      .securityContexts(securityContext());
  }

  private List<SecurityContext> securityContext() {
    List<SecurityContext> securityContexts = new ArrayList<>();
    securityContexts.add(SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build());

    return securityContexts;
  }

  private List<SecurityReference> defaultAuth() {
    List<SecurityReference> securityReferences = new ArrayList<>();
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[] {new AuthorizationScope("global", "access All")};
    SecurityReference securityReference = new SecurityReference("JWT", authorizationScopes);
    securityReferences.add(securityReference);

    return securityReferences;
  }

  private List<ApiKey> apiKey() {
    List<ApiKey> apiKeys = new ArrayList<>();
    apiKeys.add(new ApiKey("JWT", "Authorization", "header"));

    return apiKeys;
  }


}
