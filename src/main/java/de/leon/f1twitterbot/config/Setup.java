package de.leon.f1twitterbot.config;

import de.leon.f1twitterbot.model.AuthData;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Setup {

    public static TwitterFactory setupConfig(AuthData authData) {

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setDebugEnabled(true)
            .setOAuthConsumerKey(authData.getConsumerKey())
            .setOAuthConsumerSecret(authData.getAccessTokenSecret())
            .setOAuthAccessToken(authData.getConsumerKey())
            .setOAuthAccessTokenSecret(authData.getAccessTokenSecret());

        return new TwitterFactory(configurationBuilder.build());
    }

}
