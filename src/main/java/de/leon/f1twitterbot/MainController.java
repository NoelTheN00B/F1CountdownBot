package de.leon.f1twitterbot;

import de.leon.f1twitterbot.config.Setup;
import de.leon.f1twitterbot.model.AuthData;
import de.leon.f1twitterbot.model.RaceInfo;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Getter;
import twitter4j.TwitterFactory;

public class MainController {

    @Getter
    private TwitterFactory twitterFactory;

    @FXML
    protected void startButtonClick() {

        twitterFactory = Setup.setupConfig(new AuthData());

        List<RaceInfo> racesThisYear = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("races-" + LocalDate.now().getYear()))) {

            String line;
            while ((line = br.readLine()) != null) {
                RaceInfo raceInfo = new RaceInfo(line);
                racesThisYear.add(raceInfo.getRaceNo(), raceInfo);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
