package ch_5_concurrent_collections;

import java.util.concurrent.CountDownLatch;

/**
 * This is an example of making a thread wait for resources to be set-up
 * from multiple threads
 */
public class CountdownLatchExample {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(3);

        // Create and start two new threads
        new Thread(new DependentService(latch)).start();
        new Thread(new DependentService(latch)).start();
        new Thread(new DependentService(latch)).start();

        // Main thread will block until "gate" is open (count = 0)
        latch.await();

        System.out.println("All dependent service are initialized");
        // program initialized, perform some other operations

    }

    public static class DependentService implements Runnable{

        private CountDownLatch latch;

        public DependentService(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            // start up task
            System.out.println("Counting down");
            latch.countDown();
            // continue with other operations
        }
    }

}
