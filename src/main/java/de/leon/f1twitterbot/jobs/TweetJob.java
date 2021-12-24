package de.leon.f1twitterbot.jobs;

import static java.time.temporal.ChronoUnit.DAYS;

import de.leon.f1twitterbot.Races;
import de.leon.f1twitterbot.TweetText;
import de.leon.f1twitterbot.config.ConfiguredTwitter;
import de.leon.f1twitterbot.config.Texts;
import io.github.redouane59.twitter.dto.tweet.Tweet;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class TweetJob implements Job {

    LocalDate testDate = LocalDate.of(2021, 6, 1);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        Races races = new Races(testDate);
        TweetText tweetText = new TweetText(ConfiguredTwitter.get(), races, testDate);

        Tweet postedTweet = tweetText.post();

        System.out.println("Bot tweeted! (" + postedTweet.getId() + ")");
    }
}
