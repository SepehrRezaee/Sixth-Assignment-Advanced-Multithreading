package sbu.cs.CalculatePi;

import java.math.BigDecimal;
import java.util.concurrent.*;

public class PiCalculator {

    public String calculate(int digits) {
        BigDecimal pi = BigDecimal.ZERO;
        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CompletionService<BigDecimal> completionService = new ExecutorCompletionService<>(executor);

        for (int i = 0; i < digits; i++) {
            final int k = i;
            completionService.submit(() -> calculatePiTerm(k, digits));
        }
        executor.shutdown();

        for (int i = 0; i < digits; i++) {
            try {
                pi = pi.add(completionService.take().get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return pi.setScale(digits, BigDecimal.ROUND_HALF_UP).toString();
    }

    private BigDecimal calculatePiTerm(int k, int digits) {
        BigDecimal a = BigDecimal.valueOf(16).pow(k).multiply(BigDecimal.valueOf(4).divide(BigDecimal.valueOf(8 * k + 1), digits, BigDecimal.ROUND_HALF_UP));
        BigDecimal b = BigDecimal.valueOf(16).pow(k).multiply(BigDecimal.valueOf(2).divide(BigDecimal.valueOf(8 * k + 4), digits, BigDecimal.ROUND_HALF_UP));
        BigDecimal c = BigDecimal.valueOf(16).pow(k).multiply(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(8 * k + 5), digits, BigDecimal.ROUND_HALF_UP));
        BigDecimal d = BigDecimal.valueOf(16).pow(k).multiply(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(8 * k + 6), digits, BigDecimal.ROUND_HALF_UP));
        return a.subtract(b).subtract(c).subtract(d).multiply(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(16).pow(k), digits, BigDecimal.ROUND_HALF_UP));
    }

    public static void main(String[] args) {
        PiCalculator piCalculator = new PiCalculator();
        System.out.println("Pi: " + piCalculator.calculate(1000));
    }
}
