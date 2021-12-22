package de.leon.f1twitterbot.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class AuthData {
    @NonNull
    private String ConsumerKey;
    @NonNull
    private String ConsumerSecret;
    @NonNull
    private String AccessToken;
    @NonNull
    private String AccessTokenSecret;


    public AuthData(@NonNull String consumerKey, @NonNull String consumerSecret,
        @NonNull String accessToken, @NonNull String accessTokenSecret) {
        ConsumerKey = consumerKey;
        ConsumerSecret = consumerSecret;
        AccessToken = accessToken;
        AccessTokenSecret = accessTokenSecret;
    }
}
