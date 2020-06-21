package ch_2_thread_safety;

/*
This is an example of code that is not thread-safe

In the bank account, the cash machine deposits and withdraws money; the total balance SHOULD be 0 at the end.

When two threads do this at the same time, they are both accessing the "balance" variable.
The value of this now depends on how the two threads are interleaved with each other.

 */
public class UnsafeBankAccount {

    // Shared mutable state
    private static int balance = 0;

    private static int TRANSACTIONS_PER_MACHINE = 10000;

    private static void deposit() {
        balance = balance + 1;
    }

    private static void withdraw() {
        balance = balance - 1;
    }

    /*
        Each ATM does a bunch of transactions, but leaves the balance unmodified
     */
    private static void cashMachine() {
        for (int i = 0; i < TRANSACTIONS_PER_MACHINE; i++) {
            // Note: This does not happen as one "atomic" piece
            deposit(); // put a dollar in
            withdraw(); // take a dollar out
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // Cash machine A that access the bank account
        Thread threadA = new Thread(()-> {
           cashMachine();
        });

        // Cash machine B that access the bank account
        Thread threadB = new Thread(()-> {
            cashMachine();
        });

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();

        System.out.println("The balance is: " + balance);
    }
}
