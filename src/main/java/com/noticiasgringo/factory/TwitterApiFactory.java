package com.noticiasgringo.factory;

import com.twitter.clientlib.TwitterCredentialsBearer;
import com.twitter.clientlib.api.TwitterApi;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;

@Factory
public class TwitterApiFactory {

    @Value("${TWITTER_BEARER_TOKEN}")
    private String bearerToken;

    @Bean
    public TwitterApi makeTwitterApiFactory() {
        return new TwitterApi(
                new TwitterCredentialsBearer(this.bearerToken)
        );
    }

}
