public class Main {
    public static void main(String[] args) {

        Polynomial firstPolynomial = new Polynomial(20000);
        Polynomial secondPolynomial = new Polynomial(20000);

       // System.out.println("first polynomial:" + firstPolynomial);
       // System.out.println("second polynomial:" + secondPolynomial);
        System.out.println("\n");

        try {
            //Simple
            long startTime = System.currentTimeMillis();
            Polynomial firstResult = PolynomialAlgorithms.usualMultiplication(firstPolynomial, secondPolynomial);
            long endTime = System.currentTimeMillis();
            System.out.println("Usual multiplication: ");
            System.out.println("Duration : " + (endTime - startTime) + " ms");
            //System.out.println(firstResult.toString() + "\n");

            startTime = System.currentTimeMillis();
            Polynomial secondResult = PolynomialAlgorithms.parallelUsualMultiplication(firstPolynomial, secondPolynomial);
            endTime = System.currentTimeMillis();
            System.out.println("Parallel usual multiplication: ");
            System.out.println("Time : " + (endTime - startTime) + " ms");
            //System.out.println(secondResult.toString() + "\n");

            //Karatsuba
            startTime = System.currentTimeMillis();
            Polynomial thirdResult = PolynomialAlgorithms.karatsubaMultiplication(firstPolynomial, secondPolynomial);
            endTime = System.currentTimeMillis();
            System.out.println("Karatsuba multiplication: ");
            System.out.println("Time : " + (endTime - startTime) + " ms");
            //System.out.println(thirdResult.toString() + "\n");

            startTime = System.currentTimeMillis();
            Polynomial fourthResult = PolynomialAlgorithms.parallelKaratsubaMultiplication(firstPolynomial, secondPolynomial, 4);
            endTime = System.currentTimeMillis();
            System.out.println("Parallel Karatsuba multiplication: ");
            System.out.println("time : " + (endTime - startTime) + " ms");
            //System.out.println(fourthResult.toString() + "\n");
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
}