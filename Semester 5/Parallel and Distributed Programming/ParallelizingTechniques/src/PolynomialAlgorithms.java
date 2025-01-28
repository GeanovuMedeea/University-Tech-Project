import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PolynomialAlgorithms {
    private static final int MAX_PARALLEL_DEPTH = 4;

    public static Polynomial usualMultiplication(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        ArrayList<Integer> coefficients = IntStream.generate(() -> 0).limit(firstPolynomial.getDegree() + secondPolynomial.getDegree() + 1).boxed().collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0; i < firstPolynomial.getCoefficients().size(); i++) {
            for (int j = 0; j < secondPolynomial.getCoefficients().size(); j++) {
                int index = i + j;
                int value = coefficients.get(index) + firstPolynomial.getCoefficients().get(i) * secondPolynomial.getCoefficients().get(j);
                coefficients.set(index, value);
            }
        }
        return new Polynomial(coefficients);
    }

    public static Polynomial parallelUsualMultiplication(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        ArrayList<Integer> coefficients = IntStream.generate(() -> 0).limit(firstPolynomial.getDegree() + secondPolynomial.getDegree() + 1).boxed().collect(Collectors.toCollection(ArrayList::new));
        Polynomial result = new Polynomial(coefficients);

        try (ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5)) {
            int step = (result.getLength()/5 == 0 ) ? 1 : result.getLength()/5;
            for (int i = 0; i < result.getLength(); i += step) {
                PolynomialTask task = new PolynomialTask(i,i+step, firstPolynomial, secondPolynomial, result);
                executor.execute(task);
            }
        }
        return result;
    }

    public static Polynomial karatsubaMultiplication(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        if (firstPolynomial.getDegree() < 2 || secondPolynomial.getDegree() < 2) {
            return usualMultiplication(firstPolynomial, secondPolynomial);
        }

        int middle = Math.max(firstPolynomial.getDegree(), secondPolynomial.getDegree()) / 2;

        Polynomial[] splitFirst = splitPolynomial(firstPolynomial, middle);
        Polynomial[] splitSecond = splitPolynomial(secondPolynomial, middle);

        Polynomial lowerPart = karatsubaMultiplication(splitFirst[0], splitSecond[0]);
        Polynomial multiPart = karatsubaMultiplication(add(splitFirst[0], splitFirst[1]), add(splitSecond[0], splitSecond[1]));
        Polynomial higherPart = karatsubaMultiplication(splitFirst[1], splitSecond[1]);

        return combineResults(lowerPart, multiPart, higherPart, middle);
    }

    public static Polynomial parallelKaratsubaMultiplication(Polynomial firstPolynomial, Polynomial secondPolynomial, int currentDepth)
            throws ExecutionException, InterruptedException {

        if (currentDepth > MAX_PARALLEL_DEPTH || firstPolynomial.getDegree() < 2 || secondPolynomial.getDegree() < 2) {
            return karatsubaMultiplication(firstPolynomial, secondPolynomial);
        }

        try(ExecutorService executor = Executors.newFixedThreadPool(5)) {
            int middle = Math.max(firstPolynomial.getDegree(), secondPolynomial.getDegree()) / 2;

            Polynomial[] firstSplit = splitPolynomial(firstPolynomial, middle);
            Polynomial[] secondSplit = splitPolynomial(secondPolynomial, middle);

            Future<Polynomial> lowerPartFuture = executor.submit(() -> parallelKaratsubaMultiplication(firstSplit[0], secondSplit[0], currentDepth + 1));
            Future<Polynomial> multiPartFuture = executor.submit(() -> parallelKaratsubaMultiplication(
                    add(firstSplit[0], firstSplit[1]), add(secondSplit[0], secondSplit[1]), currentDepth + 1));
            Future<Polynomial> higherPartFuture = executor.submit(() -> parallelKaratsubaMultiplication(firstSplit[1], secondSplit[1], currentDepth + 1));

            Polynomial lowerPart = lowerPartFuture.get();
            Polynomial multiPart = multiPartFuture.get();
            Polynomial higherPart = higherPartFuture.get();
            return combineResults(lowerPart, multiPart, higherPart, middle);
        }
    }

    private static Polynomial[] splitPolynomial(Polynomial polynomial, int middle) {
        Polynomial lowPart = new Polynomial(polynomial.getCoefficients().subList(0, Math.min(middle, polynomial.getLength())));
        Polynomial highPart = new Polynomial(polynomial.getCoefficients().subList(Math.min(middle, polynomial.getLength()), polynomial.getLength()));
        return new Polynomial[]{lowPart, highPart};
    }

    private static Polynomial combineResults(Polynomial lowerPart, Polynomial multiPart, Polynomial higherPart, int middle) {
        Polynomial shiftedHighPart = shift(higherPart, 2 * middle);
        Polynomial shiftedMiddlePart = shift(subtract(subtract(multiPart, higherPart), lowerPart), middle);
        return add(add(shiftedHighPart, shiftedMiddlePart), lowerPart);
    }

    public static Polynomial shift(Polynomial polynomial, int offset) {
        List<Integer> coefficients = new ArrayList<>(offset + polynomial.getLength());
        for (int i = 0; i < offset; i++) coefficients.add(0);
        coefficients.addAll(polynomial.getCoefficients());
        return new Polynomial(coefficients);
    }

    public static Polynomial add(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        int maxDegree = Math.max(firstPolynomial.getDegree(), secondPolynomial.getDegree());
        List<Integer> coefficients = new ArrayList<>(maxDegree + 1);

        for (int i = 0; i <= maxDegree; i++) {
            int coeff1 = i <= firstPolynomial.getDegree() ? firstPolynomial.getCoefficients().get(i) : 0;
            int coeff2 = i <= secondPolynomial.getDegree() ? secondPolynomial.getCoefficients().get(i) : 0;
            coefficients.add(coeff1 + coeff2);
        }

        return new Polynomial(coefficients);
    }


    public static Polynomial subtract(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        int maxDegree = Math.max(firstPolynomial.getDegree(), secondPolynomial.getDegree());
        List<Integer> coefficients = new ArrayList<>(maxDegree + 1);

        for (int i = 0; i <= maxDegree; i++) {
            int coeff1 = i <= firstPolynomial.getDegree() ? firstPolynomial.getCoefficients().get(i) : 0;
            int coeff2 = i <= secondPolynomial.getDegree() ? secondPolynomial.getCoefficients().get(i) : 0;
            coefficients.add(coeff1 - coeff2);
        }

        while (coefficients.size() > 1 && coefficients.get(coefficients.size() - 1) == 0) {
            coefficients.remove(coefficients.size() - 1);
        }

        return new Polynomial(coefficients);
    }
}