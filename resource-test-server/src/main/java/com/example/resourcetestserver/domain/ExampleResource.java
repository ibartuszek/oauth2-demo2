package com.example.resourcetestserver.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = ExampleResource.Builder.class)
public class ExampleResource {

    private final String message;

    private ExampleResource(final Builder builder) {
        message = builder.message;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getMessage() {
        return message;
    }

    /**
     * {@code ExampleResource} builder static inner class.
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
         * Returns a {@code ExampleResource} built from the parameters previously set.
         *
         * @return a {@code ExampleResource} built with parameters of this {@code ExampleResource.Builder}
         */
        public ExampleResource build() {
            return new ExampleResource(this);
        }
    }
}
