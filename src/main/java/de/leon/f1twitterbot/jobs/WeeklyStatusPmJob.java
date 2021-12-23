package de.leon.f1twitterbot.jobs;

import de.leon.f1twitterbot.config.ConfiguredTwitter;
import de.leon.f1twitterbot.model.RaceInfo;
import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.dto.tweet.Tweet;
import io.github.redouane59.twitter.dto.user.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class WeeklyStatusPmJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        TwitterClient twitterClient = ConfiguredTwitter.get();
        assert twitterClient != null;
        User bot = twitterClient.getUserFromUserId(twitterClient.getUserIdFromAccessToken());
        List<Tweet> tweetsThisWeek = getTweetsThisWeek(twitterClient);

        String msg = "Ich habe aktuell " + bot.getFollowersCount() + " Follower. \n"
            + "Ich habe insgesamt " + bot.getTweetCount() + " Tweets verfasst. \n"
            + "Meine Tweets diese Woche haben bis jetzt " + calcLikesThisWeek(tweetsThisWeek) + " Likes, \n"
            + calcRetweetsThisWeek(tweetsThisWeek) + " Retweets und \n"
            + calcRepliesThisWeek(tweetsThisWeek) + " Antworten erhalten, \n"
            + "außerdem wurden sie " + calcQuotesThisWeek(tweetsThisWeek) + " Mal zitiert.";

        twitterClient.postDm(msg, "2884603469");
        System.out.println("Status PM send!");

    }

    private List<Tweet> getTweetsThisWeek(TwitterClient twitterClient) {
        List<Tweet> tweetsThisWeek = new LinkedList<>();


        try {
            File tweetsThisWeekFile = new File("tweetsThisWeek.txt");
            if (!tweetsThisWeekFile.exists()) {
                tweetsThisWeekFile.createNewFile();
            }

            BufferedReader br = new BufferedReader(new FileReader(tweetsThisWeekFile));

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals("") && !line.equals(" ")) {
                    tweetsThisWeek.add(twitterClient.getTweet(line));
                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tweetsThisWeek;
    }

    private Integer calcLikesThisWeek(List<Tweet> tweets) {
        int likes = 0;
        for (Tweet tweet : tweets) {
            likes += tweet.getLikeCount();
        }
        return likes;
    }

    private Integer calcRetweetsThisWeek(List<Tweet> tweets) {
        int likes = 0;
        for (Tweet tweet : tweets) {
            likes += tweet.getRetweetCount();
        }
        return likes;
    }

    private Integer calcQuotesThisWeek(List<Tweet> tweets) {
        int likes = 0;
        for (Tweet tweet : tweets) {
            likes += tweet.getQuoteCount();
        }
        return likes;
    }

    private Integer calcRepliesThisWeek(List<Tweet> tweets) {
        int likes = 0;
        for (Tweet tweet : tweets) {
            likes += tweet.getReplyCount();
        }
        return likes;
    }
}
