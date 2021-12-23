package de.leon.f1twitterbot.jobs;

import io.github.redouane59.twitter.TwitterClient;
import java.util.TimerTask;
import java.util.UUID;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestTask extends TimerTask {

    TwitterClient twitterClient;

    public TestTask(TwitterClient twitterClient) {
        this.twitterClient = twitterClient;
    }

    @Override
    public void run() {
        System.out.println("TestJob executed!");
        twitterClient.postTweet("Test-Tweet - " + UUID.randomUUID());
    }
}
