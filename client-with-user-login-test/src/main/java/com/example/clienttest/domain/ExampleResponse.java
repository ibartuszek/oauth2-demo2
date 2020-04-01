package com.example.clienttest.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = ExampleResponse.Builder.class)
public class ExampleResponse {

    private final String message;

    private ExampleResponse(final Builder builder) {
        message = builder.message;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getMessage() {
        return message;
    }

    /**
     * {@code ExampleResponse} builder static inner class.
     */
    public static final class Builder {
        private String message;

        private Builder() {
        }

        /**
         * Sets the {@code message} and returns a reference to this Builder so that the methods can be chained together.
         * @param message the {@code message} to set
         * @return a reference to this Builder
         */
        public Builder withMessage(final String message) {
            this.message = message;
            return this;
        }

        /**
         * Returns a {@code ExampleResponse} built from the parameters previously set.
         *
         * @return a {@code ExampleResponse} built with parameters of this {@code ExampleResponse.Builder}
         */
        public ExampleResponse build() {
            return new ExampleResponse(this);
        }
    }
}
