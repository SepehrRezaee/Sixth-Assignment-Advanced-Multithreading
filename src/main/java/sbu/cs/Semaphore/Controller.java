package sbu.cs.Semaphore;

public class Controller {

    public static void main(String[] args) {
        String[] operatorNames = {"Operator1", "Operator2", "Operator3", "Operator4", "Operator5"};

        for (String name : operatorNames) {
            Operator operator = new Operator(name);
            operator.start();
        }
    }
}
