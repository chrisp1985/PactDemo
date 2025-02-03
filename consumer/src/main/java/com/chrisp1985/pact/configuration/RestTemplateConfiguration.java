package com.chrisp1985.pact.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestTemplateConfiguration {

    @Bean(name = "myRestClient")
    public RestClient myRestClient(
            @Value("${spring.urls.base}") String baseUrl
    ) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("method", "GET")
                .defaultHeader("scheme", "http")
                .build();
    }
}
