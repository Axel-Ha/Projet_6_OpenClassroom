//package com.openclassrooms.mddapi.configuration;
//
//import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
//import io.swagger.v3.oas.annotations.security.SecurityScheme;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.tags.Tag;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import static com.openclassrooms.mddapi.configuration.SwaggerConfig.NAME_SECURITY_REQUIREMENT;
//
//@Configuration
//@SecurityScheme(
//        name = NAME_SECURITY_REQUIREMENT,
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        scheme = "bearer"
//)
//public class SwaggerConfig {
//    public static final String NAME_SECURITY_REQUIREMENT = "Bearer Authentication";
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .info(new Info().title("Chatop API")
//                        .version("1.0")
//                        .description("Documentation de l'API Chatop"))
//                .addTagsItem(new Tag().name("Auth Controller"));
//
//    }
//}
