package sbu.cs.Semaphore;

import java.util.concurrent.Semaphore;

public class Resource {
    private static final Semaphore semaphore = new Semaphore(2, true); // Fair semaphore to ensure FIFO

    public static void accessResource(String threadName) {
        try {
            semaphore.acquire();
            System.out.println(threadName + " entered the critical section at " + System.currentTimeMillis());
            Thread.sleep(100); // Simulating resource access
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // set the interrupt flag
            System.out.println("Interrupted: " + threadName);
        } finally {
            semaphore.release();
            System.out.println(threadName + " exiting the critical section at " + System.currentTimeMillis());
        }
    }
}
