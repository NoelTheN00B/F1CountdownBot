package de.leon.f1twitterbot.jobs;

import java.util.TimerTask;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestTask extends TimerTask {

    @Override
    public void run() {
        System.out.println("TestJob executed!");
    }
}
