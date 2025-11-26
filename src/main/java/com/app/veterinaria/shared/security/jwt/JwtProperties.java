package com.app.veterinaria.shared.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret;
    private Long expiration = 86400000L;
    private Long refreshExpiration = 604800000L;
}