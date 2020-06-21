package ch_6_executors_and_thread_pools;

import java.util.Random;
import java.util.concurrent.*;

/**
 * This is an example of using the ExecutorService to run some tasks,
 * then getting the tasks return value using a Callable with a Future
 */
public class ThreadPoolWithCallableExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // Create pool
        ExecutorService service = Executors.newFixedThreadPool(10);

        // Submit the task for execution
        Future<Integer> future = service.submit(new Task());

        // Perform other un-related operations
        Integer result = future.get(); // This will block until the value is future has the result
        System.out.println("The result is: " + result);

        System.out.println("Thread Name: " + Thread.currentThread().getName());

    }

    public static class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            Thread.sleep(3000);
            return new Random().nextInt();
        }
    }

}
