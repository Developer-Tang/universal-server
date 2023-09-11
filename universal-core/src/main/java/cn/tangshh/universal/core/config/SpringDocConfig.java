package cn.tangshh.universal.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Data;
import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>SpringDoc Config</p>
 * <p>SpringDoc API文档配置</p>
 *
 * @author Tang
 * @version v1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "springdoc")
@ConditionalOnClass(SpringDocConfiguration.class)
public class SpringDocConfig {
    /** API docs author */
    private String author = "";
    /** API docs title */
    private String title = "API Development Docs";
    /** API version */
    private String version = "1.0";
    /** authorization request */
    private String authHeader = "Authorization";

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                // Describe info
                .info(new Info()
                        .title(title)
                        .version(version)
                        .contact(new Contact()
                                .name(author)
                        )
                )
                // Authentication component
                .schemaRequirement(authHeader,
                        new SecurityScheme()
                                .name(authHeader)
                                .scheme("Bearer")
                                .type(SecurityScheme.Type.HTTP)
                )
                .addSecurityItem(new SecurityRequirement().addList(authHeader));
    }
}
