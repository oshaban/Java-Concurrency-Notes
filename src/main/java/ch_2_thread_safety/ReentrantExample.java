package ch_2_thread_safety;

/*
This code is thread safe.

This is an example of thread re-entrancy in Java.

A thread is allowed to "acquire a lock it already has"
 */
public class ReentrantExample {

    public void a() {
        synchronized (this) {
            b();
            System.out.println(Thread.currentThread().getName() + "- here I am, in a()");
        }
    }

    public void b() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + "- here I am, in b()");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ReentrantExample runner = new ReentrantExample();

        Thread threadA = new Thread(() -> {
            runner.a();
        });

        Thread threadB = new Thread(() -> {
            runner.a();
        });

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();

    }

}
