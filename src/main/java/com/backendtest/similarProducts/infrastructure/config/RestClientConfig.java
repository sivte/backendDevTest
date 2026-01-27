package com.backendtest.similarProducts.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * Infrastructure configuration for HTTP clients.
 */
@Configuration
public class RestClientConfig {

    @Value("${external.api.base-url}")
    private String baseUrl;

    @Bean
    public RestClient restClient() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(2000);  // 2 seconds
        factory.setReadTimeout(5000);     // 5 seconds
        
        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .build();
    }
}
