import Model.Product;
import Model.Bill;

import java.net.NoRouteToHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static List<Product> generateProducts(int nrOfProducts){
        List<Product> products = new ArrayList<>();
        for(int index=1; index <= nrOfProducts; index++){
            String name = "Product_"+index;
            float price = Math.round(index*0.5+15);
            int quantity = index * 100 + 500;
            products.add(new Product(name, price, quantity));
        }
        return products;
    }

    public static void main(String[] args) throws InterruptedException {

        //List<Product> products = readProductsFromFile();
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the number of products: ");
        int nrOfProducts = in.nextInt();
        System.out.println("Enter the number of threads: ");
        int threadNumber = in.nextInt();
        List<Product> products = generateProducts(nrOfProducts);
        List<Thread> threads = new ArrayList<>();
        Transactions transactions = new Transactions(products);

        /*List<Bill> bills =transactions.generateBills(products, nrOfProducts/5);
        int batches = (int) Math.ceil((double) (nrOfProducts/5)/ threadNumber);
        int checkIfInventoryIsValidThreadNumber = threadNumber + 1;
        int firstBill = 0;
        int lastBill = batches;
        for (int i = 0; i < threadNumber; i++) {
            lastBill = (lastBill > nrOfProducts/5) ? nrOfProducts/5 : lastBill;
            //lastBill = ((i + 1) * batches > nrOfProducts/5) ? nrOfProducts : (i + 1) * batches;
            threads.add(new Thread(new ProcessThread(transactions, bills.subList(firstBill, lastBill), i+1, firstBill, lastBill)));
            firstBill += batches;
            lastBill += batches;
        }*/

        List<Bill> bills = transactions.generatefixedBills(products,3);
        int batches = (int) Math.ceil((double) (nrOfProducts/5)/ threadNumber);
        int checkIfInventoryIsValidThreadNumber = threadNumber + 1;

        threads.add(new Thread(new ProcessThread(transactions, bills.subList(0, 1), 1, 0, 1)));
        threads.add(new Thread(new ProcessThread(transactions, bills.subList(1, 2), 2, 1, 2)));
        threads.add(new Thread(new ProcessThread(transactions, bills.subList(2, 3), 3, 2, 3)));

        long startTime = System.nanoTime();

        //List<Integer> temp = new ArrayList<>();
        for (Thread thread : threads)
        {
            thread.start();

            if ((transactions.getTransactionsDone()) % 2==0) {
                //temp.add(transactions.getTransactionsDone());
                Thread checkThead= new Thread(new CheckIfInventoryIsValidThread(transactions, checkIfInventoryIsValidThreadNumber));
                checkIfInventoryIsValidThreadNumber++;
                checkThead.start();
                checkThead.join();
            }
        }
        for (Thread thread : threads)
            thread.join();

        Thread finalThread = new Thread(new CheckIfInventoryIsValidThread(transactions, checkIfInventoryIsValidThreadNumber));
        finalThread.join();

        long endTime = System.nanoTime();

        System.out.println("Total money: " + transactions.getTotalMoney());
        /*for(Integer integer: temp)
        {
            System.out.println(integer);
        }*/

        double seconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("That took "+ seconds + " seconds");

    }
}