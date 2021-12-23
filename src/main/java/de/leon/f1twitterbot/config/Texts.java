package de.leon.f1twitterbot.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Texts {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd - HH:mm:ss";

    public static String onlineMsg() {
        return "Beep boop.\nI'm online now!\n" + LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }

    public static String offlineMsg() {
        return "Beep boop.\nIt's getting dark, I'm going to sleep now...\n" + LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }

    public static String raceWeek(String city, String grandPrixName) {
        return "IT'S RACE WEEK IN {city}!\n6 days until {grandPrixName}.\n\n#F1 #Formula1".replace("{city}", city).replace("{grandPrixName}", grandPrixName);
    }

    public static String raceDay(String circuitName) {
        return "IT'S RACE DAY AT {circuitName}!\n\n#F1 #Formula1".replace("{circuitName}", circuitName.toUpperCase(Locale.ROOT));
    }

    public static String raweCeek(String city, String grandPrixName) {
        return "IT'S RAWE CEEK IN {city}!\n6 days until {grandPrixName}.\n\n#F1 #Formula1".replace("{city}", city).replace("{grandPrixName}", grandPrixName);
    }

    public static String daysUntil(Integer days, String grandPrixName) {
        if (days == 1) {
            return "It's 1 day until {grandPrixName}!\n\n#F1 #Formula1".replace("{grandPrixName}", grandPrixName);
        }
        return "It's {days} days until {grandPrixName}!\n\n#F1 #Formula1".replace("{days}", days.toString()).replace("{grandPrixName}", grandPrixName);
    }

}
