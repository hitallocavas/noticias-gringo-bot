package com.noticiasgringo.domain.usecases;

import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.model.TweetCreateRequest;
import com.twitter.clientlib.model.TweetCreateRequestReply;
import jakarta.inject.Singleton;

@Singleton
public class RetweetUseCase {

    private final TwitterApi twitterApi;

    public RetweetUseCase(TwitterApi twitterApi) {
        this.twitterApi = twitterApi;
    }

    public String retweetById(String id, String message) throws ApiException {
        var reply = new TweetCreateRequestReply();
        reply.inReplyToTweetId(id);

        TweetCreateRequest tweetCreateRequest = new TweetCreateRequest();
        tweetCreateRequest.reply(reply);
        tweetCreateRequest.text(message);

        return twitterApi.tweets().createTweet(tweetCreateRequest).execute().getData().getId();
    }
}
