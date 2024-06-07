package sbu.cs.CalculatePi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.*;

public class PiCalculator {

    public String calculate(int digits) {
        BigDecimal pi = BigDecimal.ZERO;
        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CompletionService<BigDecimal> completionService = new ExecutorCompletionService<>(executor);
        MathContext mc = new MathContext(digits + 2, RoundingMode.HALF_UP);

        int terms = Math.max(digits * 10, 1000);
        for (int i = 0; i < terms; i++) {
            final int k = i;
            completionService.submit(() -> calculatePiTerm(k, mc));
        }
        executor.shutdown();

        for (int i = 0; i < terms; i++) {
            try {
                pi = pi.add(completionService.take().get(), mc);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return formatResult(pi, digits);
    }

    private BigDecimal calculatePiTerm(int k, MathContext mc) {
        BigDecimal sixteen = new BigDecimal("16", mc);
        BigDecimal eightK = new BigDecimal(8 * k, mc);

        BigDecimal term = BigDecimal.ONE.divide(sixteen.pow(k, mc), mc);
        BigDecimal sum = BigDecimal.ZERO;

        sum = sum.add(BigDecimal.valueOf(4).divide(eightK.add(BigDecimal.ONE, mc), mc));
        sum = sum.subtract(BigDecimal.valueOf(2).divide(eightK.add(BigDecimal.valueOf(4), mc), mc));
        sum = sum.subtract(BigDecimal.ONE.divide(eightK.add(BigDecimal.valueOf(5), mc), mc));
        sum = sum.subtract(BigDecimal.ONE.divide(eightK.add(BigDecimal.valueOf(6), mc), mc));

        return term.multiply(sum, mc);
    }

    private String formatResult(BigDecimal pi, int digits) {
        return pi.round(new MathContext(digits + 1, RoundingMode.HALF_UP)).toString();
    }

    public static void main(String[] args) {
        PiCalculator piCalculator = new PiCalculator();
        System.out.println("Pi: " + piCalculator.calculate(1000));
    }
}