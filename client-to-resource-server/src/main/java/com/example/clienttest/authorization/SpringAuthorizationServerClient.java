package com.example.clienttest.authorization;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class SpringAuthorizationServerClient {

    @Value("${url.authorization-server}")
    private String authorizationServerUrl;

    private static final String TOKEN_ENDPOINT = "oauth/token";
    private static final String GRANT_TYPE = "grant_type";

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringAuthorizationServerClient.class);

    private final RestTemplate restTemplate = new RestTemplate();

    public AccessToken getAccessToken(final HttpHeaders authorizationHeader) {
        return getExampleResponse(createUri(), authorizationHeader);
    }

    public AccessToken getAccessToken(final HttpHeaders authorizationHeader, final String username, final String password) {
        return getExampleResponse(createUri(username, password), authorizationHeader);
    }

    private AccessToken getExampleResponse(final URI uri, final HttpHeaders authorizationHeader) {
        AccessToken accessToken;
        try {
            LOGGER.info("Request is sent to authorization server: " + uri.toString());
            accessToken = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(authorizationHeader), AccessToken.class).getBody();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
        return accessToken;
    }

    private URI createUri(final String username, final String password) {
        return UriComponentsBuilder.fromHttpUrl(authorizationServerUrl + TOKEN_ENDPOINT)
            .queryParam(GRANT_TYPE, "password")
            .queryParam("username", username)
            .queryParam("password", password)
            .queryParam("scope", "read")
            .build()
            .toUri();
    }

    private URI createUri() {
        return UriComponentsBuilder.fromHttpUrl(authorizationServerUrl + TOKEN_ENDPOINT)
            .queryParam(GRANT_TYPE, "client_credentials")
            .build()
            .toUri();
    }

}
