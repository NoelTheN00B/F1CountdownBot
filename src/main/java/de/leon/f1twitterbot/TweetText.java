package de.leon.f1twitterbot;

import static java.time.temporal.ChronoUnit.DAYS;

import de.leon.f1twitterbot.config.Texts;
import de.leon.f1twitterbot.model.RaceInfo;
import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.dto.tweet.Tweet;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class TweetText {

    private final TwitterClient twitterClient;
    private final Races races;
    private final LocalDate date;

    public TweetText(TwitterClient twitterClient, Races races, LocalDate date) {
        this.twitterClient = twitterClient;
        this.races = races;
        this.date = date;
    }

    public Tweet post() {
        Tweet lastTweet;
        RaceInfo nextRace = races.getNextRace();
        Integer daysUntilRace = (int) DAYS.between(date, nextRace.getRaceDateTime().toLocalDate());

        if (daysUntilRace.equals(6)) {

            if (nextRace.getCountry().equals("Italy")) {
                lastTweet = twitterClient.postTweet(Texts.raweCeek(nextRace.getCity(), nextRace.getGrandPrixName()));
            } else {
                lastTweet = twitterClient.postTweet(Texts.raceWeek(nextRace.getCity(), nextRace.getGrandPrixName()));
            }
        } else if (daysUntilRace.equals(0)) {
            lastTweet = twitterClient.postTweet(Texts.raceDay(nextRace.getCircuitName()));
        } else {
            lastTweet = twitterClient.postTweet(Texts.daysUntil(daysUntilRace, nextRace.getGrandPrixName())); //currently, not working
        }

        addToList(lastTweet);
        return lastTweet;
    }

    private void addToList(Tweet tweet) {

        try {
            File tweetsThisWeek = new File("tweetsThisWeek.txt");
            if (!tweetsThisWeek.exists()) {
                boolean fileCreated = tweetsThisWeek.createNewFile();
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(tweetsThisWeek));
            bw.write(tweet.getId());
            bw.newLine();
            bw.flush();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
