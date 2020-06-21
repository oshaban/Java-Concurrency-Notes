package ch_2_thread_safety;

/*
Not thread safe

ThreadA and ThreadB run getInstance at the same time

ThreadA checks to see if ExpensiveObject and it was not, so it attempts to create it, but it takes some time.
While ThreadA is creating ExpensiveObject, ThreadB checks to see if ExpensiveObject was made yet, and it was not,
so ThreadB creates ExpensiveObject.

Now getInstance returns two different objects, instead of returning the same object.

Known as check-then-act race condition

 */
public class LazyInitRaceCondition {

    private ExpensiveObject instance = null;

    public ExpensiveObject getInstance() {
        if (instance == null) {
            return new ExpensiveObject();
        }
        return instance;
    }

    public static void main(String[] args) throws InterruptedException {
        LazyInitRaceCondition driver = new LazyInitRaceCondition();

        Thread threadA = new Thread(()-> {
            driver.getInstance();
        });

        Thread threadB = new Thread(() -> {
            driver.getInstance();
        });

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();
    }

    public static class ExpensiveObject {
        public ExpensiveObject() {
            // Simulate expensive operation to create thread
            try {
                Thread.sleep(100);
                System.out.println("Creating Expensive Object");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
