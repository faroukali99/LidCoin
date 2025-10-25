// ============================================
// 9. SwaggerConfig.java - Configuration API Documentation
// ============================================
@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("LidCoin Blockchain API")
                .description("API REST pour la plateforme blockchain LidCoin")
                .version("1.0.0")
                .contact(new Contact()
                    .name("LidCoin Team")
                    .email("contact@lidcoin.com")
                    .url("https://lidcoin.com"))
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new Components()
                .addSecuritySchemes("bearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
    }
}
