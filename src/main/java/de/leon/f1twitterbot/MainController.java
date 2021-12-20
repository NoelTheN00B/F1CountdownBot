package de.leon.f1twitterbot;

import de.leon.f1twitterbot.config.Setup;
import de.leon.f1twitterbot.model.AuthData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Getter;
import twitter4j.TwitterFactory;

public class MainController {

    @FXML
    public Label apiKeyLabel;
    @FXML
    private TextField apiKeyTextfield;
    @FXML
    public Label apiSecretKeyLabel;
    @FXML
    public TextField apiSecretKeyTextfield;
    @FXML
    public Label accessTokenLabel;
    @FXML
    public TextField accessTokenTextfield;
    @FXML
    public Label accessTokenSecretLabel;
    @FXML
    public TextField accessTokenSecretTextfield;

    @Getter
    private TwitterFactory twitterFactory;

    @FXML
    protected void setConfigButtonClick() {

        twitterFactory = Setup.setupConfig(new AuthData(apiKeyTextfield.getText(),
            apiSecretKeyTextfield.getText(),
            accessTokenTextfield.getText(),
            accessTokenSecretTextfield.getText()));
    }
}
