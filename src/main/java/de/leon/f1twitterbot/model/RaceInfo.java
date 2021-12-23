package de.leon.f1twitterbot.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NonNull;

@Getter
@NonNull
public class RaceInfo {

    private Integer raceNo;
    private String country;
    private String city;
    private String circuitName;
    private String grandPrixName;
    private LocalDateTime raceDateTime;

    public RaceInfo(Integer raceNo, String country, String city, String circuitName, String grandPrixName,
        LocalDateTime raceDateTime) {
        this.raceNo = raceNo;
        this.country = country;
        this.city = city;
        this.circuitName = circuitName;
        this.grandPrixName = grandPrixName;
        this.raceDateTime = raceDateTime;
    }

    /**
     * Parses line from text file with structure "raceNo//country//city//circuitName//grandPrixName//year//month//day//startHour//startMinute"
     *
     * @param line
     */
    public RaceInfo(String line) {
        String[] infos = line.split("//");
        raceNo = Integer.parseInt(infos[0]);
        country = infos[1];
        city = infos[2];
        circuitName = infos[3];
        circuitName = infos[4];
        raceDateTime = LocalDateTime.of(Integer.parseInt(infos[5]), Integer.parseInt(infos[6]), Integer.parseInt(infos[7]),
            Integer.parseInt(infos[8]), Integer.parseInt(infos[9]));
    }
}
