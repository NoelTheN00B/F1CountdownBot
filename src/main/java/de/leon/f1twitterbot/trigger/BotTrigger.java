package de.leon.f1twitterbot.trigger;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class BotTrigger {

    public static CronTrigger getDailyCounterTrigger(String name, String group, String jobName, String jobGroup) {
        return TriggerBuilder.newTrigger()
            .withIdentity(name, group)
            .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?")
                .withMisfireHandlingInstructionFireAndProceed())
            .forJob(jobName, jobGroup)
            .startNow()
            .build();
    }

    public static CronTrigger getWeeklyStatusTrigger(String name, String group, String jobName, String jobGroup, CronTriggerWeekdayCode cronTriggerWeekdayCode) {
        return TriggerBuilder.newTrigger()
            .withIdentity(name, group)
            .withSchedule(CronScheduleBuilder.cronSchedule("0 0 12 ? * " + cronTriggerWeekdayCode.getCode())
                .withMisfireHandlingInstructionFireAndProceed())
            .forJob(jobName, jobGroup)
            .startNow()
            .build();
    }

    public static Trigger getTestTrigger(String name, String group, String jobName, String jobGroup, Integer intervalInSec) {
        return TriggerBuilder.newTrigger()
            .withIdentity(name, group)
            .startNow()
            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(intervalInSec)
                .withMisfireHandlingInstructionFireNow()
                .repeatForever())
            .forJob(jobName, jobGroup)
            .build();
    }

    public static Trigger getDailySimpleTrigger(String name, String group, String jobName, String jobGroup) {
        return TriggerBuilder.newTrigger()
            .withIdentity(name, group)
            .startAt(Date.from(
                LocalDate.now().plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault())
                    .toInstant()))
            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInHours(24)
                .withMisfireHandlingInstructionFireNow()
                .repeatForever())
            .forJob(jobName, jobGroup)
            .build();
    }

    public enum CronTriggerWeekdayCode {
        MON, TUE, WED, THU, FRI, SAT, SUN;

        public String getCode() {
            switch (this) {
                case MON:
                default:
                    return "2";
                case TUE:
                    return "3";
                case WED:
                    return "4";
                case THU:
                    return "5";
                case FRI:
                    return "6";
                case SAT:
                    return "7";
                case SUN:
                    return "1";
            }
        }
    }
}
