package ch_6_executors_and_thread_pools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is an example of using a Fixed Thread Pool
 * <p>
 * Internally the producer-consumer pattern is used
 */
public class FixedThreadPoolExample {

    public static void main(String[] args) {

        System.out.println("Number of cores: " + Runtime.getRuntime().availableProcessors());

        // Create the pool
        ExecutorService service = Executors.newFixedThreadPool(10);

        // Submit tasks for execution
        for (int i = 0; i < 10; i++) {
            service.execute(new Task());
        }
        System.out.println("Thread Name: " + Thread.currentThread().getName());

        service.shutdown();
    }

    public static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println("Thread Name: " + Thread.currentThread().getName());
        }
    }

}
