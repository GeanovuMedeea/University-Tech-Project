import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static void displayMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int elem : row) {
                System.out.print(elem + " ");
            }
            System.out.println();
        }
    }

    private static int[][] initializeMatrix(int rowSize, int columnSize) {
        int[][] matrix = new int[rowSize][columnSize];
        Random random = new Random();
        for (int rowIndex = 0; rowIndex < rowSize; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnSize; columnIndex++) {
                matrix[rowIndex][columnIndex] = random.nextInt(10)+1;
            }
        }
        return matrix;
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input the row nr of the first matrix: ");
        int firstRowNr = scanner.nextInt();

        System.out.println("Input the column nr of the first matrix: ");
        int firstColumnNr = scanner.nextInt();

        System.out.println("Input the row nr of the second matrix: ");
        int secondRowNr = scanner.nextInt();

        System.out.println("Input the column nr of the second matrix: ");
        int secondColumnNr = scanner.nextInt();

        System.out.println("Input the number of tasks: ");
        int taskCount = scanner.nextInt();

        int[][] firstMatrix = initializeMatrix(firstRowNr, firstColumnNr);
        int[][] secondMatrix = initializeMatrix(secondRowNr, secondColumnNr);

        //int[][] firstMatrix = {{2,1,4},{0,1,1}};
        //int[][] secondMatrix = {{6,3,-1,0},{1,1,0,4},{-2,5,0,2}};

        System.out.println("Using individual threads:");
        MatrixMultiplier firstMultiplier = new MatrixMultiplier(firstMatrix, secondMatrix, firstRowNr, secondColumnNr, firstColumnNr);
        long individualThreadsStartTime = System.nanoTime();

        //firstMultiplier.multiplyWithIndividualThreads(taskCount);
        try {
            Thread[] threads = new Thread[taskCount];
            for (int taskIndex = 0; taskIndex < taskCount; taskIndex++) {
                threads[taskIndex] = new Thread(new MatrixMultiplierTask(firstMultiplier, taskIndex, taskCount));
                threads[taskIndex].start();
            }
            for (Thread thread : threads) {
                thread.join();
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        long individualThreadsEndTime = System.nanoTime();
        displayMatrix(firstMultiplier.getResultMatrix());
        System.out.println("Time taken with individual threads: " + (individualThreadsEndTime - individualThreadsStartTime) / 1_000_000.0 + " ms");

        System.out.println("Using a thread pool:");
        MatrixMultiplier secondMultiplier = new MatrixMultiplier(firstMatrix, secondMatrix, firstRowNr, secondColumnNr, firstColumnNr);
        long threadPoolStartTime = System.nanoTime();
        //secondMultiplier.multiplyWithThreadPool(taskCount);

        // try works to avoid using finally, like streams read/write to files or shutdowns
        try (ExecutorService pool = Executors.newFixedThreadPool(taskCount)) {
            for (int taskIndex = 0; taskIndex < taskCount; taskIndex++) {
                pool.submit(new MatrixMultiplierTask(secondMultiplier, taskIndex, taskCount));
            }
            //pool.shutdown();
            //pool.awaitTermination(1, TimeUnit.MINUTES);
            //while(pool.isTerminated(){}
        }
        long threadPoolEndTime = System.nanoTime();
        displayMatrix(secondMultiplier.getResultMatrix());
        System.out.println("Time taken with thread pool: " + (threadPoolEndTime - threadPoolStartTime) / 1_000_000.0 + " ms");
        scanner.close();
    }
}
