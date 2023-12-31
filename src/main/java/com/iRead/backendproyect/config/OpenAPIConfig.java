package com.iRead.backendproyect.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI myOpenAPI() {

        Contact contact = new Contact();
        contact.setEmail("iReadProject@gmail.com");
        contact.setName("Development Team iRead");
        contact.setUrl("https://www.google.com/");

        License mitLicense = new License().name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Application Web iRead API")
                .version("1.0")
                .contact(contact)
                .description("This API sets out endpoints for handling requests.")
                .termsOfService("https://www.google.com/")
                .license(mitLicense);

        return new OpenAPI().info(info);
    }

}
