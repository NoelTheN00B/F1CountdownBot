package de.leon.f1twitterbot.config;

import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.signature.TwitterCredentials;
import java.io.File;
import java.io.IOException;

public class ConfiguredTwitter {

    public static TwitterClient get() {
        try {
            return new TwitterClient(
                TwitterClient.OBJECT_MAPPER.readValue(new File("AuthKeys.json"),
                    TwitterCredentials.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
