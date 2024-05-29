package sbu.cs.Semaphore;

public class Operator extends Thread {

    public Operator(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Resource.accessResource(this.getName());
            try {
                Thread.sleep(500); // simulate doing some work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Interrupted: " + getName());
            }
        }
    }
}
