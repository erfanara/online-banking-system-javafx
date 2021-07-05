package OBSApp.server;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public Scheduler(Runnable cmd, LocalDateTime firstRun, int periodInDays) {
        Duration duration = Duration.between(LocalDateTime.now(), firstRun);
        scheduler.scheduleAtFixedRate(
                cmd,
                duration.getSeconds(),
                TimeUnit.DAYS.toSeconds(periodInDays),
                TimeUnit.SECONDS
        );

    }
}
