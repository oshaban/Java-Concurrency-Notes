package ch_2_thread_safety;

/*
This is an example of a counter that is not thread-safe

Even though count++ looks like a single indivisible operation (aka atomic)
It happens in 3 parts: Read the value, modify, write

 */
public class UnsafeCounter {

    // Shared mutable state
    private long count = 0;

    public long getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        UnsafeCounter counter = new UnsafeCounter();

        Thread threadA = new Thread(()-> {
            for (int i = 0; i < 10000; i++) {
                counter.count++;
            }
        });

        Thread threadB = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.count++;
            }
        });

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();

        System.out.println(counter.getCount());

    }

}
