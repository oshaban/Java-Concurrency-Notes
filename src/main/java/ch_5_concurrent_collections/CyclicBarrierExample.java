package ch_5_concurrent_collections;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * This is an example using a CyclicBarrier to wait for threads to
 * come together at barrier point
 */
public class CyclicBarrierExample {


    public static void main(String[] args) {

        CyclicBarrier barrier = new CyclicBarrier(3);
        new Thread(new Task(barrier)).start();
        new Thread(new Task(barrier)).start();
        new Thread(new Task(barrier)).start();

    }

    public static class Task implements Runnable {

        private CyclicBarrier barrier;

        public Task(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {

            while (true) {

                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                // Send message to system
                System.out.println("Sending a message");

            }

        }
    }

}
