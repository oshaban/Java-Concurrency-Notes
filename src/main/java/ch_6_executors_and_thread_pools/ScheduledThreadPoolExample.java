package ch_6_executors_and_thread_pools;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This is an example of using a Scheduled Thread Pool
 */
public class ScheduledThreadPoolExample {

    public static void main(String[] args) {

        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

        // Task to run after 10 second delay
        service.schedule(new Task(), 10, TimeUnit.SECONDS);

        // Task to run repeatedly every 10 seconds
        service.scheduleAtFixedRate(new Task(), 15, 10, TimeUnit.SECONDS);

        // Task to run repeatedly 10 seconds after previous task completes
        service.scheduleWithFixedDelay(new Task(), 15, 10, TimeUnit.SECONDS);

    }

    public static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println("Running a task");
        }
    }

}
