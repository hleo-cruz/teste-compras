package com.br.teste.compras.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DIConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
