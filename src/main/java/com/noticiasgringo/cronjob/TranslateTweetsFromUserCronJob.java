package com.noticiasgringo.cronjob;

import com.noticiasgringo.domain.usecases.GetTweetsByUserUseCase;
import com.noticiasgringo.domain.usecases.GetTwitterUserIdsUseCase;
import com.noticiasgringo.domain.usecases.RetweetUseCase;
import com.twitter.clientlib.ApiException;
import io.micronaut.scheduling.annotation.Scheduled;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class TranslateTweetsFromUserCronJob {
    private static final InternalLogger LOG = InternalLoggerFactory.getInstance(TranslateTweetsFromUserCronJob.class);

    private final GetTwitterUserIdsUseCase getTwitterUserIdsUseCase;
    private final GetTweetsByUserUseCase getTweetsByUserUseCase;
    private final RetweetUseCase retweetUseCase;

    @Inject
    public TranslateTweetsFromUserCronJob(GetTwitterUserIdsUseCase getTwitterUserIdsUseCase, GetTweetsByUserUseCase getTweetsByUserUseCase, RetweetUseCase retweetUseCase) {
        this.getTwitterUserIdsUseCase = getTwitterUserIdsUseCase;
        this.getTweetsByUserUseCase = getTweetsByUserUseCase;
        this.retweetUseCase = retweetUseCase;
    }

    @Scheduled(fixedDelay = "1m")
    public void translateTweetsFromUserJob(){
        getTwitterUserIdsUseCase.getTwitterUserIdsFromConfig().forEach(id -> {
            try {
                var tweets = getTweetsByUserUseCase.getTweetsByUserid(id);
                tweets.forEach(tweet -> {
                    try {
                        var newPostId = retweetUseCase.retweetById(tweet.getId(), "Nova postagem do brabo!");
                        LOG.info("Novo tweet postado: " + newPostId);
                    } catch (ApiException e) {
                        LOG.error(e.getResponseBody());
                    }
                });
            } catch (ApiException e) {
                LOG.error(e.getResponseBody());
            }
        });

        LOG.info("Job executado com sucesso");
    }

}
