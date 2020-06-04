package com.example.clienttest.web;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.clienttest.authorization.AuthorizationHeaderProvider;
import com.example.clienttest.domain.ExampleResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(tags = "Resource endpoints", value = "ExampleClientController")
@ApiModel(value = "Example resource", description = "Endpoint for getting protected resource by client or user.")
public class ExampleClientController {

    private static final String APPLICATION_JSON = "application/json";

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleClientController.class);

    @Autowired
    private ResourceServerClient resourceServerClient;

    @Autowired
    private AuthorizationHeaderProvider authorizationHeaderProvider;

    @ApiOperation(value = "Get protected resource by username and password")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 409, message = "Conflict"),
        @ApiResponse(code = 424, message = "Failed dependency")
    })
    @RequestMapping(value = "oauth2example/resource/by_user", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public ExampleResponse getTestResponseByUser(
        @ApiParam(name = "username", required = true, value = "username", example = "user")
        @NotNull @RequestParam final String username,
        @ApiParam(name = "password", required = true, value = "password", example = "pass")
        @NotNull @RequestParam final String password) {
        LOGGER.info("Get bearer token from authorization server");
        HttpHeaders authorizationHeader = authorizationHeaderProvider.provide(username, password);
        LOGGER.info("Client with user credentials sends request to resource server");
        ExampleResponse response = resourceServerClient.getExampleResponseForUsers(authorizationHeader);
        LOGGER.info("Client with user credentials received response from resource server: \'" + response.getMessage() + "\'");
        return response;
    }

    @ApiOperation(value = "Get protected resource by client id and client secret")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 409, message = "Conflict"),
        @ApiResponse(code = 424, message = "Failed dependency")
    })
    @RequestMapping(value = "oauth2example/resource/by_client", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public ExampleResponse getTestResponseByClient() {
        LOGGER.info("Get bearer token from authorization server");
        HttpHeaders authorizationHeader = authorizationHeaderProvider.provide();
        LOGGER.info("Client with client credentials sends request to resource server");
        ExampleResponse response = resourceServerClient.getExampleResponseForClients(authorizationHeader);
        LOGGER.info("Client with client credentials received response from resource server: \'" + response.getMessage() + "\'");
        return response;
    }

}
