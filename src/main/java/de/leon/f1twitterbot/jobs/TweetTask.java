package de.leon.f1twitterbot.jobs;

import static java.time.temporal.ChronoUnit.DAYS;

import de.leon.f1twitterbot.config.ConfiguredTwitter;
import de.leon.f1twitterbot.config.Texts;
import de.leon.f1twitterbot.model.RaceInfo;
import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.dto.tweet.Tweet;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class TweetTask extends TimerTask {

    TwitterClient twitterClient;
    List<RaceInfo> racesThisYear = null;
    RaceInfo nextRace;
    LocalDate testDate = LocalDate.of(2021, 6, 1);

    public TweetTask(TwitterClient twitterClient) {
        this.twitterClient = twitterClient;
    }

    @Override
    public void run() {
        loadRaces();

        try {
            nextRace = racesThisYear.stream()
                //.filter(e -> e.getRaceDateTime().toLocalDate().isAfter(LocalDate.now())).findFirst()
                .filter(e -> e.getRaceDateTime().toLocalDate().isAfter(testDate)).findFirst()
                .orElseThrow();
        } catch (Exception e) {
            racesThisYear = readFile(LocalDate.now().getYear() + 1);
            nextRace = racesThisYear.get(0);
        }

        Tweet lastTweet = twitterClient.postTweet(
            Texts.daysUntil(
                (int) DAYS.between(testDate, nextRace.getRaceDateTime().toLocalDate()),
                nextRace.getGrandPrixName()));

        try {
            File tweetsThisWeek = new File("tweetsThisWeek.txt");
            if (!tweetsThisWeek.exists()) {
                tweetsThisWeek.createNewFile();
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(tweetsThisWeek));
            bw.write(lastTweet.getId());
            bw.newLine();
            bw.flush();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Bot tweeted!");
    }

    private void loadRaces() {

        if (racesThisYear == null) {

            racesThisYear = readFile(LocalDate.now().getYear());

        } else {

            if (testDate.isAfter(
                racesThisYear.get(racesThisYear.size() - 1).getRaceDateTime().toLocalDate())) {
                racesThisYear = readFile(LocalDate.now().getYear() + 1);
                nextRace = racesThisYear.get(0);
            }
        }
    }

    private List<RaceInfo> readFile(int year) {
        List<RaceInfo> raceInfoList = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(
            new FileReader("races-" + year))) {

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("--")) {
                    RaceInfo raceInfo = new RaceInfo(line);
                    raceInfoList.add(raceInfo.getRaceNo() - 1, raceInfo);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return raceInfoList;
    }
}
