package com.example.clienttest.web;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.clienttest.domain.ExampleResponse;

@RestController
public class ExampleClientController {

    private static final String APPLICATION_JSON = "application/json";

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleClientController.class);

    @Autowired
    private ResourceServerClient resourceServerClient;

    @RequestMapping(value = "oauth2example/client/example", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public ExampleResponse getTestResponse() {
        LOGGER.info("Client with user credentials sends request to resource server");
        ExampleResponse response = resourceServerClient.getExampleResponse();
        LOGGER.info("Client with user credentials received response from resource server: \'" + response.getMessage() + "\'");
        return response;
    }

}
