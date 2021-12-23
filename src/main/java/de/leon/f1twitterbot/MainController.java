package de.leon.f1twitterbot;

import de.leon.f1twitterbot.config.ConfiguredTwitter;
import de.leon.f1twitterbot.config.Texts;
import de.leon.f1twitterbot.jobs.TestJob;
import de.leon.f1twitterbot.jobs.TweetJob;
import de.leon.f1twitterbot.jobs.WeeklyStatusPmJob;
import de.leon.f1twitterbot.trigger.BotTrigger;
import de.leon.f1twitterbot.trigger.BotTrigger.CronTriggerWeekdayCode;
import io.github.redouane59.twitter.TwitterClient;
import java.util.Timer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
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

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        scheduler = schedulerFactory.getScheduler();

        JobDetail countdownTweets = JobBuilder.newJob(TweetJob.class)
            .withIdentity("countdownTweet", "tweets")
            .build();

        JobDetail weeklyStatusPM = JobBuilder.newJob(WeeklyStatusPmJob.class)
            .withIdentity("weeklyStatusPM", "pms")
            .build();

        JobDetail testJobDetail = JobBuilder.newJob(TestJob.class)
            .withIdentity("testJob", "testJobs")
            .build();

        Trigger testTrigger = BotTrigger.getTestTrigger("testTrigger1", "testTrigger", "testJob", "testJobs", 30);
        Trigger testTrigger2 = BotTrigger.getTestTrigger("testTrigger2", "testTrigger", "weeklyStatusPM", "pms", 60);
        CronTrigger countdownTweetsTrigger = BotTrigger.getDailyCounterTrigger("countdownTweetsTrigger", "tweetTrigger", "countdownTweet", "tweets");
        CronTrigger weeklyStatusTrigger = BotTrigger.getWeeklyStatusTrigger("weeklyStatusTrigger", "pmTrigger", "weeklyStatusPM", "pms", CronTriggerWeekdayCode.MON);

        assert twitterClient != null;
        twitterClient.postTweet(Texts.onlineMsg());
        statusLabel.setText("Bot running!");

        scheduler.scheduleJob(testJobDetail, testTrigger);
        scheduler.scheduleJob(weeklyStatusPM, testTrigger2);
        //scheduler.scheduleJob(countdownTweets, countdownTweetsTrigger);
        //scheduler.scheduleJob(weeklyStatusPM, weeklyStatusTrigger);
        scheduler.start();

        /*timer = new Timer();
        //timer.schedule(new TestTask(), Date.from(LocalDate.now().plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()), ((1000 * 60) * 60) * 24);
        timer.schedule(new TestTask(twitterClient), Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()), 60000);*/

    }

    public void stopButtonClick(ActionEvent actionEvent) throws SchedulerException {

        try {
            scheduler.clear();
            scheduler.shutdown();
        } catch (NullPointerException ignore) {
        }

        try {
            timer.cancel();
            timer.purge();
        } catch (NullPointerException ignore) {
        }

        ConfiguredTwitter.get().postTweet(Texts.offlineMsg());
        statusLabel.setText("Bot has been stopped!");
        System.out.println("Bot has been stopped!");
    }
}
