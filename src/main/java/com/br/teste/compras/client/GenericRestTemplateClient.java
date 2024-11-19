package com.br.teste.compras.client;

import com.br.teste.compras.error.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class GenericRestTemplateClient {

    protected static Logger logger = LoggerFactory.getLogger(GenericRestTemplateClient.class);

    @Autowired
    protected RestTemplate restTemplate;

    protected HttpHeaders headers(Map<String, Object> headerValues) {

        final HttpHeaders headers = new HttpHeaders();

        headerValues.forEach((key, value) -> {

            if (value instanceof MediaType) {
                headers.setContentType((MediaType) value);
            }
            headers.set(key, String.valueOf(value));
        });

        return headers;
    }

    protected <T> ResponseEntity<T> request(String url, HttpMethod httpMethod, HttpHeaders headers, Class<T> responseBody) {

        ResponseEntity<T> response = null;

        final HttpEntity<T> httpEntity = new HttpEntity<>(headers);

        logRequest(url, httpMethod, headers);

        try {

            response = restTemplate.exchange(url, httpMethod, httpEntity, responseBody);
            logResponse(url, response);

            return response;

        } catch (Exception e) {

            final HttpStatus httpStatus = HttpStatus.valueOf(response.getStatusCode().value());
            logResponseError(url, response);
            throw new CustomException(e, httpStatus);
        }

    }

    protected void logRequest(String url, HttpMethod httpMethod, HttpHeaders headers) {

        final long threadId = Thread.currentThread().threadId();

        final StringBuilder sb = new StringBuilder();

        sb.append("\n --------------------------------------------------------------------------------------- \n");
        sb.append(String.format("| #################################### REQUEST - %s #################################### \n", threadId));
        sb.append(String.format("| URL - %s \n", url));
        sb.append(String.format("| HttpMethod - %s \n", httpMethod));

        if (headers != null) {
            sb.append("| Headers {                                                                |\n");
            headers.forEach((key, values) -> {
                sb.append(String.format("| %s - %s \n", key, String.join(",", values)));
            });
            sb.append("|   }  \n");
        }

        sb.append(String.format("| #################################### REQUEST - %s #################################### \n", threadId));
        sb.append(" --------------------------------------------------------------------------------------- \n");

        logger.info(sb.toString());
    }

    protected <R> void logResponse(String url, ResponseEntity<R> responseEntity) {

        final long threadId = Thread.currentThread().threadId();

        String sb = "\n --------------------------------------------------------------------------------------- \n" +
                String.format("| #################################### RESPONSE - %s #################################### \n", threadId) +
                String.format("| URL - %s \n", url) +
                String.format("| HttpStatus - %s \n", responseEntity.getStatusCode()) +
                String.format("| HasBody - %s \n", responseEntity.hasBody()) +
                String.format("| #################################### RESPONSE - %s #################################### \n", threadId) +
                " --------------------------------------------------------------------------------------- \n";

        logger.info(sb);
    }

    protected <R> void logResponseError(String url, ResponseEntity<R> response) {

        final long threadId = Thread.currentThread().threadId();
        final HttpStatusCode statusCode = response.getStatusCode();
        final Object errorBody = response.getBody();

        String sb = "\n --------------------------------------------------------------------------------------- \n" +
                String.format("| #################################### RESPONSE ERROR - %s #################################### \n", threadId) +
                String.format("| URL - %s \n", url) +
                String.format("| HttpStatus - %s \n", statusCode) +
                String.format("| ErrorBody - %s \n", errorBody) +
                String.format("| #################################### RESPONSE ERROR - %s #################################### \n", threadId) +
                " --------------------------------------------------------------------------------------- \n";

        logger.info(sb);
    }

}