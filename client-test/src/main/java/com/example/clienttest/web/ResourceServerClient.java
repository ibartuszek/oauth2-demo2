package com.example.clienttest.web;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.clienttest.domain.ExampleResponse;

@Component
public class ResourceServerClient {

    private static final String resourceServerExampleUrl = "http://localhost:7001/resource/example";
    private static final String SOMETHING_WENT_WRONG = "Something went wrong";

    private final RestTemplate restTemplate = new RestTemplate();

    ExampleResponse getExampleResponse() {
        ExampleResponse responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(resourceServerExampleUrl, ExampleResponse.class).getBody();
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
