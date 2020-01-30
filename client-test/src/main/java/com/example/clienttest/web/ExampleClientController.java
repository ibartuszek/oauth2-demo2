package com.example.clienttest.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.clienttest.domain.ExampleResponse;

@RestController
public class ExampleClientController {

    private static final String APPLICATION_JSON = "application/json";

    @Autowired
    private ResourceServerClient resourceServerClient;

    @RequestMapping(value = "/client/example", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public ExampleResponse getTestResponse() {
        return resourceServerClient.getExampleResponse();
    }

}
