package com.ahmed.monitor.scheduler;

import com.ahmed.monitor.config.Config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    private final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();

    public void start(Runnable task) {

        scheduler.scheduleAtFixedRate(
                task,
                0,
                Config.CHECK_INTERVAL,
                TimeUnit.SECONDS
        );

    }

}