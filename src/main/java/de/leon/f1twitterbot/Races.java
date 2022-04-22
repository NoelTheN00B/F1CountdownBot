package de.leon.f1twitterbot;

import de.leon.f1twitterbot.model.RaceInfo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Races {

    private List<RaceInfo> raceInfoList;
    private LocalDate date;
    private RaceInfo nextRace;

    public Races(List<RaceInfo> raceInfoList, LocalDate date) {
        this.raceInfoList = raceInfoList;
        this.date = date;
    }

    public Races(LocalDate date) {
        this.raceInfoList = readFile(date.getYear());
        this.date = date;
    }

    public List<RaceInfo> readFile(int year) {
        raceInfoList = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(
            new FileReader("races-" + year + ".txt"))) {

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

    public void loadData() {

        if (raceInfoList == null) {

            raceInfoList = readFile(date.getYear());

        }

        if (date.isAfter(
            raceInfoList.get(raceInfoList.size() - 1).getRaceDateTime().toLocalDate())) {

            raceInfoList = readFile(LocalDate.now().getYear() + 1);
            nextRace = raceInfoList.get(0);

        }

        nextRace = raceInfoList.stream()
            .filter(e -> e.getRaceDateTime().toLocalDate().isAfter(date)).findFirst()
            .get();

    }

    public List<RaceInfo> getRaceInfoList() {

        loadData();

        return raceInfoList;
    }

    public RaceInfo getNextRace() {

        loadData();

        return nextRace;
    }
}
