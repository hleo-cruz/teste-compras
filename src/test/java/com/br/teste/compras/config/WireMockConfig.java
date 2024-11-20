package com.br.teste.compras.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.ClassRule;
import org.junit.Rule;

public class WireMockConfig {

    protected ObjectMapper objectMapper = new ObjectMapper();

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(
            WireMockConfiguration.options()
                    .port(8082)
                    .extensions(NoKeepAliveTransformer.class)
                    .notifier(new ConsoleNotifier(true))
    );

    @Rule
    public WireMockClassRule instanceRule = wireMockRule;



}
