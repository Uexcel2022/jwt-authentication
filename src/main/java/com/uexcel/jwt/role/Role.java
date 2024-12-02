package com.uexcel.jwt.role;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "roles")
public record Role(String user, String admin) {
}
