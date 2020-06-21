package ch_3_thread_safety_and_sharing_objects;

import java.math.BigInteger;
/*
One approach to ensure thread safety is to use Thread confinement.
<p>
The local variables are confined to the respective threads. Each thread has its own call-stack.
 */
public class Confinement {

    public static class Factorial {
        public void computeFact(final int n) {
            BigInteger result = BigInteger.valueOf(1L);
            for (int i = 1; i <= n; i++) {
                System.out.println("working on fact: " + i);
                result = result.multiply(new BigInteger(String.valueOf(i)));
            }
            System.out.println("fact(" + n + ") = " + result);
        }
    }

    public static void main(String[] args) {
        Factorial factorial = new Factorial();
        new Thread(()-> {
            factorial.computeFact(99);
        }).start();
        factorial.computeFact(100);
    }

}
