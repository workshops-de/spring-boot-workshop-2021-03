package de.todo42.bookdemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "bookdemo")
public class AppConfig {

    private String stringProp;
    private Integer numProp;

    private NestedProps nested;

    @Data
    public static class NestedProps {
        private String configParam;
    }
}