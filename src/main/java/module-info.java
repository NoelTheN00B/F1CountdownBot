module de.leon.f1twitterbot {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    requires static lombok;
    requires quartz;
    requires org.apache.commons.lang3;
    requires twittered;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens de.leon.f1twitterbot to javafx.fxml;
    exports de.leon.f1twitterbot;
    exports de.leon.f1twitterbot.config;
    exports de.leon.f1twitterbot.jobs;
    opens de.leon.f1twitterbot.jobs;
    exports de.leon.f1twitterbot.model;
    opens de.leon.f1twitterbot.model;
    opens de.leon.f1twitterbot.config to javafx.fxml;
}