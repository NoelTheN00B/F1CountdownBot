package de.leon.f1twitterbot.config;

import de.leon.f1twitterbot.model.AuthData;
import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.signature.TwitterCredentials;

public class Setup {

    public static TwitterClient setupConfig(AuthData authData) {

        return new TwitterClient(TwitterCredentials.builder()
            .accessToken(authData.getAccessToken())
            .accessTokenSecret(authData.getAccessTokenSecret())
            .apiKey(authData.getConsumerKey())
            .apiSecretKey(authData.getConsumerSecret()).build());
    }

}
