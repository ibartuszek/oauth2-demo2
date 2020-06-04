package com.example.clienttest.authorization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = AccessToken.Builder.class)
public class AccessToken {

    private final String accessToken;

    public AccessToken(final Builder builder) {
        this.accessToken = builder.accessToken;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String toString() {
        return "AccessToken{"
            + "accessToken='" + accessToken + '\''
            + '}';
    }

    public static final class Builder {
        @JsonProperty("access_token")
        private String accessToken;

        private Builder() {
        }

        public Builder withAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public AccessToken build() {
            return new AccessToken(this);
        }
    }
}
