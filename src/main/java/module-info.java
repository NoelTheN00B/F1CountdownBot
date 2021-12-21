module de.leon.f1twitterbot {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    requires org.twitter4j.core;
    requires static lombok;

    opens de.leon.f1twitterbot to javafx.fxml;
    exports de.leon.f1twitterbot;
    exports de.leon.f1twitterbot.config;
    opens de.leon.f1twitterbot.config to javafx.fxml;
}