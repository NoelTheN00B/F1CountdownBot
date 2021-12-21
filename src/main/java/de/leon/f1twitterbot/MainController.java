package de.leon.f1twitterbot;

import de.leon.f1twitterbot.config.Setup;
import de.leon.f1twitterbot.config.Texts;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Getter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class MainController {

    @FXML
    public PasswordField apiKeyField;
    @FXML
    public PasswordField apiSecretKeyField;
    @FXML
    public PasswordField oauthTokenField;
    @FXML
    public PasswordField oauthTokenSecretField;

    @Getter
    private TwitterFactory twitterFactory;
    List<RaceInfo> racesThisYear = new LinkedList<>();

    @FXML
    protected void startButtonClick() throws TwitterException {

        twitterFactory.getInstance().updateStatus(Texts.onlineMsg());

    }

    public void setupButtonClick(ActionEvent actionEvent) {

        twitterFactory = Setup.setupConfig(new AuthData(apiKeyField.getText(),
            apiSecretKeyField.getText(), oauthTokenField.getText(), oauthTokenSecretField.getText()));
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
