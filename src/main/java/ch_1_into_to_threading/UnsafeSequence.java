package ch_1_into_to_threading;

/*
This is an example of sequence generator that is not thread safe

It is possible for both threads to access "value" at the same time, see the same value,
then add one to it, resulting in a missed operation.

Whether or not getNext() returns a unique number depends on the order in which the threads get to it
 */
public class UnsafeSequence {

    private int value;

    public int getNext() {
        return value++;
    }

    public static void main(String[] args) throws InterruptedException {

        UnsafeSequence sequence = new UnsafeSequence();

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
