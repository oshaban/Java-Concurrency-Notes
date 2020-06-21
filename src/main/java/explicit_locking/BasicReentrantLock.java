package explicit_locking;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This is an example of using a ReentrantLock to protect access to a shared resource
 *
 * This is an alternative to using the intrinsic lock in synchronized blocks
 */
public class BasicReentrantLock {

    public static void main(String[] args) {

        SharedResource resource = new SharedResource();

        Thread t1 = new Thread(()-> resource.accessResource());
        Thread t2 = new Thread(() -> resource.accessResource());
        Thread t3 = new Thread(() -> resource.accessResource());
        Thread t4 = new Thread(() -> resource.accessResource());

        t1.start();
        t2.start();
        t3.start();
        t4.start();

    }

    public static class SharedResource {

        Lock lock = new ReentrantLock();

        // Shared-resource
        int counter = 0;

        public void accessResource() {
            lock.lock();

            System.out.println("The thread has acquired the lock " + Thread.currentThread().getName());
            try {
                counter++;
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                // Always release the lock
                System.out.println("The thread has released the lock " + Thread.currentThread().getName());
                lock.unlock();
            }
        }
    }

}