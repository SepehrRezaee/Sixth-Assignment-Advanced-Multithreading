package sbu.cs.Semaphore;

public class Controller {

    public static void main(String[] args) {
        Operator operator1 = new Operator("Operator1");
        Operator operator2 = new Operator("Operator2");
        Operator operator3 = new Operator("Operator3");
        Operator operator4 = new Operator("Operator4");
        Operator operator5 = new Operator("Operator5");

        operator1.start();
        operator2.start();
        operator3.start();
        operator4.start();
        operator5.start();
    }
}
