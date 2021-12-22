package de.leon.f1twitterbot;

import de.leon.f1twitterbot.config.ConfiguredTwitter;
import de.leon.f1twitterbot.config.Texts;
import de.leon.f1twitterbot.jobs.TestJob;
import de.leon.f1twitterbot.jobs.TestTask;
import de.leon.f1twitterbot.jobs.TweetJob;
import de.leon.f1twitterbot.jobs.TweetTask;
import io.github.redouane59.twitter.TwitterClient;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class MainController {

    @FXML
    public PasswordField apiKeyField;
    @FXML
    public PasswordField apiSecretKeyField;
    @FXML
    public PasswordField oauthTokenField;
    @FXML
    public PasswordField oauthTokenSecretField;
    @FXML
    public Label statusLabel;

    TwitterClient twitterClient;
    Scheduler scheduler;
    Timer timer;

    @FXML
    protected void startButtonClick() throws SchedulerException {

        twitterClient = ConfiguredTwitter.get();

        /*
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        scheduler = schedulerFactory.getScheduler();

        JobDetail tweetJobDetail = JobBuilder.newJob(TweetJob.class)
            .withIdentity("tweetJob", "tweets")
            .build();

        JobDetail testJobDetail = JobBuilder.newJob(TestJob.class)
            .withIdentity("testJob", "testJobs")
            .build();

        Trigger dailyTrigger = TriggerBuilder.newTrigger()
            .withIdentity("dailyTrigger", "trigger")
            .startAt(Date.from(
                LocalDate.now().plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault())
                    .toInstant()))
            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInHours(24)
                .withMisfireHandlingInstructionFireNow()
                .repeatForever())
            .forJob("tweetJob", "tweets")
            .build();

        Trigger testTrigger = TriggerBuilder.newTrigger()
            .withIdentity("testTrigger", "trigger")
            .startNow()
            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(30)
                .withMisfireHandlingInstructionFireNow()
                .repeatForever())
            //.forJob("testJob", "testJobs")
            .forJob("tweetJob", "tweets")
            .build();

        CronTrigger dailyCronTrigger = TriggerBuilder.newTrigger()
            .withIdentity("dailyCronTrigger", "trigger")
            .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?")
                .withMisfireHandlingInstructionFireAndProceed())
            .forJob("tweetJob", "tweets")
            .build();

        //scheduler.scheduleJob(tweetJobDetail, dailyCronTrigger);
        //scheduler.scheduleJob(testJobDetail, testTrigger);
        scheduler.scheduleJob(tweetJobDetail, testTrigger);
        scheduler.start();
         */

        assert twitterClient != null;
        twitterClient.postTweet(Texts.onlineMsg());
        statusLabel.setText("Bot running!");

        timer = new Timer();
        //timer.schedule(new TestTask(), Date.from(LocalDate.now().plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()), ((1000 * 60) * 60) * 24);
        timer.schedule(new TestTask(), Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()), 30000);
        timer.schedule(new TweetTask(twitterClient), Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()), 1000 * 60);

    }

    public void stopButtonClick(ActionEvent actionEvent) throws SchedulerException {

        try {
            timer.cancel();
            timer.purge();
            scheduler.shutdown();
        } catch (NullPointerException ignore) {
        }

        ConfiguredTwitter.get().postTweet(Texts.offlineMsg());
        statusLabel.setText("Bot has been stopped!");
        System.out.println("Bot has been stopped!");
    }
}
