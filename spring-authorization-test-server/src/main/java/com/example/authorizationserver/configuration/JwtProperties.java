package com.example.authorizationserver.configuration;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

    private String keyStorePath;

    private String keyStorePassword;

    private Map<String, String> keyPairs;

    public String getKeyStorePath() {
        return keyStorePath;
    }

    public void setKeyStorePath(final String keyStorePath) {
        this.keyStorePath = keyStorePath;
    }

    public char[] getKeyStorePassword() {
        return keyStorePassword.toCharArray();
    }

    public void setKeyStorePassword(final String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    public Map<String, String> getKeyPairs() {
        return keyPairs;
    }

    public void setKeyPairs(final Map<String, String> keyPairs) {
        this.keyPairs = keyPairs;
    }
}
