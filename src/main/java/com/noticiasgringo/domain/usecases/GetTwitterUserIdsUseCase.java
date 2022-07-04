package com.noticiasgringo.domain.usecases;

import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class GetTwitterUserIdsUseCase {

    @Value("${twitterUsersId}")
    private List<String> usersId;

    public List<String> getTwitterUserIdsFromConfig(){
        return this.usersId;
    }
}
