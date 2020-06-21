package ch_3_thread_safety_and_sharing_objects;


import java.util.concurrent.atomic.AtomicLong;

/*
Thread-safe

This is an example that shows how changes to volatile variables are visible to other threads
*/
public class ViolatileExample {

    public volatile static boolean asleep = false;

    public static AtomicLong count = new AtomicLong(0);

    public static class Job implements Runnable {

        @Override
        public void run() {
            while (!asleep) {
                countSomeSheep();
            }
        }

        public void countSomeSheep() {
            count.getAndIncrement();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Job job = new Job();

        Thread threadA = new Thread(job);
        Thread threadB = new Thread(job);

        /*
        This thread will update the volatile variable "asleep"

        Since this variable is volatile, changes to it will be made visible to other threads
         */
        new Thread(() -> {
            try {
                Thread.sleep(10);
                asleep = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();

        System.out.println("Count is: " + count);

    }

}
