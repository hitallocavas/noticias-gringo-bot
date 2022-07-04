package com.noticiasgringo.domain.usecases;

import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.model.Tweet;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class GetTweetsByUserUseCase {
    private final TwitterApi twitterApi;

    @Inject
    public GetTweetsByUserUseCase(TwitterApi twitterApi) {
        this.twitterApi = twitterApi;
    }

    public List<Tweet> getTweetsByUserid(String userId) throws ApiException {
        return twitterApi.tweets().usersIdTweets(userId).execute(4).getData();
    }
}
