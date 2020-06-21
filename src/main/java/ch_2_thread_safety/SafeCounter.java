package ch_2_thread_safety;

import java.util.concurrent.atomic.AtomicLong;

/*
This is an example of a counter that is thread-safe

Now that count is "atomic", the increment operation happens as one indivisible chunk

 */
public class SafeCounter {

    private AtomicLong count = new AtomicLong(0);

    public AtomicLong getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        SafeCounter counter = new SafeCounter();

        Thread threadA = new Thread(()-> {
            for (int i = 0; i < 10000; i++) {
                counter.count.getAndIncrement();
            }
        });

        Thread threadB = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.count.getAndIncrement();
            }
        });

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();

        System.out.println(counter.getCount());

    }

}