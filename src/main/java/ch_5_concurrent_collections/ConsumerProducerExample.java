package ch_5_concurrent_collections;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Producer threads generate a random number.
 * Consumer threads divide that number by 2.
 */
public class ConsumerProducerExample {

    private static final BlockingQueue<Item> queue = new LinkedBlockingQueue<>(5);

    public static void main(String[] args) {

        // Producers
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    int number = new Random().nextInt() % 10;
                    try {
                        Thread.sleep(1);
                        // Thread blocks if full
                        queue.put(new Item(number));
                        System.out.println("Producer " + Thread.currentThread().getName() + " generated : " + number);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }).start();
        }

        // Consumers
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        // Thread blocks if empty
                        Item item = queue.take();
                        System.out.println("Consumer Thread " + Thread.currentThread().getName() + " is dividing : " + item.getNumber() / 2);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }).start();
        }

    }

    public static class Item {
        private int number;

        public Item(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }
    }

}
