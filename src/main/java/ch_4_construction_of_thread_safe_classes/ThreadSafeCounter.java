package ch_4_construction_of_thread_safe_classes;

/*
This is an example of a Thread-Safe counter that uses the monitor pattern.
 */
public class ThreadSafeCounter {

    private long value = 0;

    public synchronized long getValue() {
        return this.value;
    }

    public synchronized long increment() {
        if (value == Long.MAX_VALUE) {
            throw new IllegalStateException("counter overflow");
        }
        return ++value;
    }

    public static void main(String[] args) throws InterruptedException {

        ThreadSafeCounter counter = new ThreadSafeCounter();

        Thread threadA = new Thread(()-> {
            for (int i = 0; i < 10000; i++) {
                counter.increment();
            }
        });

        Thread threadB = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.increment();
            }
        });

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();

        System.out.println("Value: " + counter.getValue());
    }

}
