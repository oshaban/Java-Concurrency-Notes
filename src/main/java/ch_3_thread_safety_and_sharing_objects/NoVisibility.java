package ch_3_thread_safety_and_sharing_objects;

/**
 * Example of Thread visibility issue.
 */
public class NoVisibility {

    // Shared mutable state
    private static boolean ready;
    private static int number;

    private static class ReaderThread implements Runnable {
        @Override
        public void run() {
            while (!ready) {
                System.out.println(Thread.currentThread().getName());
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new Thread(new ReaderThread()).start();
        number = 42;
        ready = true;
    }

}
