package de.leon.f1twitterbot.jobs;

import static java.time.temporal.ChronoUnit.DAYS;

import de.leon.f1twitterbot.Races;
import de.leon.f1twitterbot.TweetText;
import de.leon.f1twitterbot.config.Texts;
import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.dto.tweet.Tweet;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.TimerTask;

public class TweetTask extends TimerTask {

    TwitterClient twitterClient;
    LocalDate testDate = LocalDate.of(2021, 6, 1);

    public TweetTask(TwitterClient twitterClient) {
        this.twitterClient = twitterClient;
    }

    @Override
    public void run() {
        Races races = new Races(testDate);
        TweetText tweetText = new TweetText(twitterClient, races, testDate);

        Tweet postedTweet = tweetText.post();

        System.out.println("Bot tweeted! (" + postedTweet.getId() + ")");
    }
}
