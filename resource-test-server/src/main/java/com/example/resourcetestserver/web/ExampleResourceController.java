package com.example.resourcetestserver.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.resourcetestserver.domain.ExampleResource;

@RestController
public class ExampleResourceController {

    private static final String TEST_RESPONSE = "Test response from resource server";
    private static final String APPLICATION_JSON = "application/json";

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleResourceController.class);

    @RequestMapping(value = "oauth2example/resource/example", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public ExampleResource getTestResponse() {
        LOGGER.info("Resource server returns: \'" + TEST_RESPONSE + "\' test response");
        return ExampleResource.builder()
                .withMessage(TEST_RESPONSE)
                .build();
    }

}
