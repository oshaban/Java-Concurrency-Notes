package ch_5_concurrent_collections;

public class ThreadInterruptExample {

    public static void main(String[] args) {

        // Start thread
        Thread newThread = new Thread(new Task());
        newThread.start();

        // some time later

        // oops, I changed my mind
        newThread.interrupt();
        System.out.println("Asking politely to stop");

    }

    public static class Task implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                System.out.println("Processing number " + i);

                // Poll for interrupts
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Stopping the task");
                    return;
                }
            }
        }
    }

}
