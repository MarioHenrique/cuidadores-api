package br.com.softcare;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableTransactionManagement
public class CareGiverApiApplication{
	
	public static void main(String[] args) {
		SpringApplication.run(CareGiverApiApplication.class, args);
	}
	
	@Bean
	public Docket newsApi() {
		List<ApiKey> apiKey = new ArrayList<ApiKey>();
		apiKey.add(apiKey());

		return new Docket(DocumentationType.SWAGGER_2).groupName("SOFTCARE").securitySchemes(apiKey)
				.apiInfo(apiInfo()).select().paths(regex("/api.*")).build();
	}
	
	@Bean
	public SecurityConfiguration security() {
		return new SecurityConfiguration("client-id", "client-secret", "realm", "APILUC", "token", ApiKeyVehicle.HEADER,
				"token", ",");
	}
	
	private ApiKey apiKey() {
		return new ApiKey("token", "token", "header");
	}
	
	@Value("${version}")
	private String version;
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("REST API SOFTCARE")
				.description("Serviço para funcionalidade do app de cuidadores").license("Apache License Version 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").version(version).build();
	}

}
