package com.br.teste.compras.client.clientes;

import com.br.teste.compras.client.GenericRestTemplateClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Lazy
@Component
public class ClienteRestTemplateClient extends GenericRestTemplateClient {

    @Value("${client.url.cliente}")
    private String url;

    public List<ClienteModel> listarClientes() {

        final Map<String, Object> params = Map.of(

                "Content-Type", MediaType.APPLICATION_JSON,
                "Accept", MediaType.APPLICATION_JSON
        );

        final HttpHeaders headers = headers(params);

        final ResponseEntity<ClienteModel[]> response = request(url, HttpMethod.GET, headers, ClienteModel[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }
}
