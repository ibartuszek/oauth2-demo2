package com.example.clienttest.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.clienttest.domain.ExampleResponse;

@Component
public class ResourceServerClient {

    @Value("${url.resource-server}")
    private String resourceServerUrl;

    private static final String RESOURCE_ENDPOINT_FOR_USERS = "oauth2example/resource/exampleForUsers";
    private static final String RESOURCE_ENDPOINT_FOR_CLIENTS = "oauth2example/resource/exampleForClients";
    private static final String SOMETHING_WENT_WRONG = "Something went wrong";

    private final RestTemplate restTemplate = new RestTemplate();

    ExampleResponse getExampleResponseForUsers(final HttpHeaders authorizationHeader) {
        return getExampleResponse(RESOURCE_ENDPOINT_FOR_USERS, authorizationHeader);
    }

    ExampleResponse getExampleResponseForClients(final HttpHeaders authorizationHeader) {
        return getExampleResponse(RESOURCE_ENDPOINT_FOR_CLIENTS, authorizationHeader);
    }

    private ExampleResponse getExampleResponse(final String endpoint, final HttpHeaders authorizationHeader) {
        ExampleResponse responseEntity;
        try {
            responseEntity = restTemplate.exchange(resourceServerUrl + endpoint, HttpMethod.GET, new HttpEntity<>(authorizationHeader), ExampleResponse.class).getBody();
        } catch (Exception e) {
            // LOG exception...
            responseEntity = createError();
        }
        return responseEntity;
    }

    private ExampleResponse createError() {
        return ExampleResponse.builder()
                .withMessage(SOMETHING_WENT_WRONG)
                .build();
    }

}
