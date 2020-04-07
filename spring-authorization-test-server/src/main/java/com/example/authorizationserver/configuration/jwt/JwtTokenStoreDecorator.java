package com.example.authorizationserver.configuration.jwt;

import java.security.KeyPair;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

public class JwtTokenStoreDecorator extends JwtTokenStore {

    private JwtAccessTokenConverter jwtAccessTokenConverter;
    private KeyStoreKeyFactory keyStoreKeyFactory;
    private JwtProperties jwtProperties;


    private JwtTokenStoreDecorator(final JwtAccessTokenConverter jwtTokenEnhancer) {
        super(jwtTokenEnhancer);
    }

    private JwtTokenStoreDecorator(final Builder builder) {
        this(builder.jwtAccessTokenConverter);
        this.jwtAccessTokenConverter = builder.jwtAccessTokenConverter;
        this.jwtProperties = builder.jwtProperties;
        this.keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(this.jwtProperties.getKeyStorePath()), this.jwtProperties.getKeyStorePassword());
        initKeys();
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initKeys() {
        for (Map.Entry<String, String> entry : jwtProperties.getKeyPairs().entrySet()) {
            jwtAccessTokenConverter.setKeyPair(createKeyPair(entry));
        }
    }

    private KeyPair createKeyPair(Map.Entry<String, String> entry) {
        return keyStoreKeyFactory.getKeyPair(entry.getKey(), entry.getValue().toCharArray());
    }

    public static final class Builder {

        private JwtAccessTokenConverter jwtAccessTokenConverter;
        private JwtProperties jwtProperties;

        private Builder() {
        }

        public Builder withJwtAccessTokenConverter(final JwtAccessTokenConverter jwtAccessTokenConverter) {
            this.jwtAccessTokenConverter = jwtAccessTokenConverter;
            return this;
        }

        public Builder withJwtProperties(final JwtProperties jwtProperties) {
            this.jwtProperties = jwtProperties;
            return this;
        }

        public JwtTokenStoreDecorator build() {
            return new JwtTokenStoreDecorator(this);
        }
    }
}
