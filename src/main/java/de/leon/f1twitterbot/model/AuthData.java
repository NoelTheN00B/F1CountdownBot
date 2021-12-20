package de.leon.f1twitterbot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class AuthData {
    @NonNull
    private String ConsumerKey;
    @NonNull
    private String ConsumerSecret;
    @NonNull
    private String AccessToken;
    @NonNull
    private String AccessTokenSecret;
}
