package com.example.resourcetestserver.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.resourcetestserver.domain.ExampleResource;

@RestController
public class ExampleResourceController {

    private static final String TEST_RESPONSE = "Test response from resource server";
    private static final String APPLICATION_JSON = "application/json";

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleResourceController.class);

    @Value("${spring.application.name:application}")
    private String applicationName;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "oauth2example/resource/exampleForUsers", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public ExampleResource getTestResponseForUsers() {
        LOGGER.info("{} returns '{}' test response (for users)", applicationName, TEST_RESPONSE);
        return ExampleResource.builder()
                .withMessage(TEST_RESPONSE)
                .build();
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @RequestMapping(value = "oauth2example/resource/exampleForClients", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public ExampleResource getTestResponseForClients() {
        LOGGER.info("{} returns '{}' test response (for clients)", applicationName, TEST_RESPONSE);
        return ExampleResource.builder()
            .withMessage(TEST_RESPONSE)
            .build();
    }

}
