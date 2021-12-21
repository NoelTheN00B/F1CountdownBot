package de.leon.f1twitterbot.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.Data;
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

    public AuthData() {
        try (BufferedReader br = new BufferedReader(new FileReader("keys"))) {
            String[] lines = (String[]) br.lines().toArray();

            this.ConsumerKey = lines[0];
            this.ConsumerSecret = lines[1];
            this.AccessToken = lines[2];
            this.AccessTokenSecret = lines[3];

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
