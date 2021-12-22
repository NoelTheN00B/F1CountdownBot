package de.leon.f1twitterbot.config;

public class Texts {

    public static String onlineMsg() {
        return "Beep boop.\nI'm online now!";
    }

    public static String offlineMsg() {
        return "Beep boop.\nIt's getting dark, I'm going to sleep now...";
    }

    public static String raceWeek() {
        return "IT'S RACE WEEK!";
    }

    public static String raceDay() {
        return "IT'S RACE DAY!";
    }

    public static String raweCeek() {
        return "IT'S RAWE CEEK!";
    }

    public static String daysUntil(Integer days) {
        if (days == 1) {
            return "It's 1 day until race day!";
        }
        return "It's {days} days until race day!".replace("{days}", days.toString());
    }

}
