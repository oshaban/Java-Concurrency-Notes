package ch_1_into_to_threading;

/*
This is an example of sequence generator that is thread safe
 */
public class SafeSequence {

    /**
     * Shared mutable state; multiple threads can access this,
     * so we need to provide proper synchronization
     */
    private int value;

    /*
    Only one thread can be inside this block at a time
     */
    public synchronized int getNext() {
        return value++;
    }

    public static void main(String[] args) throws InterruptedException {

        SafeSequence sequence = new SafeSequence();

        Thread threadA = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                sequence.getNext();
            }
        });

        Thread threadB = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                sequence.getNext();
            }
        });

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();

        System.out.println("value: " + sequence.value);

    }

}
