package com.gcs.cmp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@SecurityScheme(
        name = "Basic Auth", // can be set to anything
        scheme = "basic", type = SecuritySchemeType.HTTP
)
@SecurityScheme(
        name = "Token", // can be set to anything
        type = SecuritySchemeType.APIKEY,in = SecuritySchemeIn.HEADER
)
@OpenAPIDefinition(
		  info = @Info(title = "Cloud Management API", version = "v1.0"),
	        security = {@SecurityRequirement(name = "Basic Auth"),@SecurityRequirement(name = "Token")}
		)
public class CMPApplication {

	public static void main(String[] args) {
		SpringApplication.run(CMPApplication.class, args);
	}
}
