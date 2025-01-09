package com.sales.authorization.config;

import org.springdoc.webmvc.ui.SwaggerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SwaggerConfig.class})
public class AuthorizationConfig {
}
