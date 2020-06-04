package com.example.clienttest.authorization;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationHeaderProvider {

    @Value("${secrets.client-id}")
    private String clientId;

    @Value("${secrets.client-secret}")
    private String clientSecret;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationHeaderProvider.class);

    @Autowired
    private SpringAuthorizationServerClient authorizationServerClient;

    public HttpHeaders provide(final String username, final String password) {
        HttpHeaders clientAuthorization = createClientBasicAuthHeader();
        AccessToken accessToken = authorizationServerClient.getAccessToken(clientAuthorization, username, password);
        return createBearerAuthorizationHeader(accessToken);
    }

    public HttpHeaders provide() {
        HttpHeaders clientAuthorization = createClientBasicAuthHeader();
        AccessToken accessToken = authorizationServerClient.getAccessToken(clientAuthorization);
        return createBearerAuthorizationHeader(accessToken);
    }

    private HttpHeaders createClientBasicAuthHeader() {
        HttpHeaders headers = new HttpHeaders();
        String value =  "Basic " + new String(Base64.getEncoder().encode((clientId + ":" + clientSecret).getBytes()));
        headers.set(HttpHeaders.AUTHORIZATION, value);
        LOGGER.info("Authorization header for bearer token request: " + value);
        return headers;
    }

    private HttpHeaders createBearerAuthorizationHeader(final AccessToken accessToken) {
        HttpHeaders result = new HttpHeaders();
        result.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken.getAccessToken());
        LOGGER.info("The bearer token: " + accessToken.getAccessToken());
        return result;
    }

}
