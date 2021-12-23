package de.leon.f1twitterbot.jobs;

import de.leon.f1twitterbot.config.ConfiguredTwitter;
import java.util.UUID;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob implements Job {

    public TestJob() {
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("TestJob executed!");
        ConfiguredTwitter.get().postTweet("Test-Tweet - " + UUID.randomUUID());
    }
}
