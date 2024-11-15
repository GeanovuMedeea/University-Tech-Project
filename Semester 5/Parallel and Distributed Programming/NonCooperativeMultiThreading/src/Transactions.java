import Model.Product;
import Model.Bill;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Transactions {
    private float totalMoney;
    private final List<Product> allProducts;
    private final ReentrantReadWriteLock readWriteLock;
    private final ReentrantReadWriteLock onlyWritingLock;

    private int transactionsDone;
    //private final ReentrantLock onlyWritingLock;
    //private final ReentrantReadWriteLock readLock;
    //private final ReentrantReadWriteLock writeLock; //works

    private final List<Bill> processedBills;

    public Transactions(List<Product> allProducts) {
        this.allProducts = allProducts;
        this.totalMoney = 0;
        this.readWriteLock = new ReentrantReadWriteLock(true);
        this.processedBills = new ArrayList<>();
        this.transactionsDone = 0;
        this.onlyWritingLock = new ReentrantReadWriteLock(true);
        //this.onlyWritingLock = new ReentrantLock(true);
        //this.readLock = new ReentrantReadWriteLock(true);
        //this.writeLock = new ReentrantReadWriteLock(true);//works
    }

    public List<Bill> generateBills(List<Product> products, int numberOfBills) {
        List<Bill> generatedBills = new ArrayList<>();
        Random random = new Random();

        for (int billNumber = 1; billNumber <= numberOfBills; billNumber++) {
            List<Product> productsInBill = new ArrayList<>();
            int numberOfProductsInBill = random.nextInt(5) + 1;

            for (int productNumber = 0; productNumber < numberOfProductsInBill; productNumber++) {
                int productIndex = random.nextInt(products.size());
                Product selectedProduct = products.get(productIndex);

                String name = selectedProduct.getName();
                float price = selectedProduct.getUnitPrice();
                int maxQuantity = selectedProduct.getQuantity();
                int quantity = random.nextInt(maxQuantity%10+1) + 1;

                productsInBill.add(new Product(name, price, quantity));
            }

            generatedBills.add(new Bill(productsInBill, billNumber));
        }

        return generatedBills;
    }

    public List<Bill> generatefixedBills(List<Product> products, int numberOfBills) {
        List<Bill> generatedBills = new ArrayList<>();

        List<Product> productsInBill = new ArrayList<>();

        Product product = new Product("Product_1", 10.5F, 2);
        Product product2 = new Product("Product_2", 2.5F, 5);
        Product product3 = new Product("Product_3", 6.5F, 5);

        Product product4 = new Product("Product_4", 13.6F, 12);
        Product product5 = new Product("Product_5", 112.4F, 3);
        Product product6 = new Product("Product_6", 11F, 23);

        Product product7 = new Product("Product_7", 2.5F, 5);
        Product product8 = new Product("Product_8", 6.2F, 6);
        Product product9 = new Product("Product_9", 11.2F, 23);

        productsInBill.add(product);
        productsInBill.add(product2);
        productsInBill.add(product3);

        generatedBills.add(new Bill(productsInBill, 1));

        List<Product> productsInBill2 = new ArrayList<>();

        productsInBill2.add(product4);
        productsInBill2.add(product5);
        productsInBill2.add(product6);
        generatedBills.add(new Bill(productsInBill2, 2));


        List<Product> productsInBill3 = new ArrayList<>();

        productsInBill3.add(product7);
        productsInBill3.add(product8);
        productsInBill3.add(product9);

        generatedBills.add(new Bill(productsInBill3, 3));


        return generatedBills;
    }

    public void processBill(Bill billToProcess) throws Exception {
        readWriteLock.readLock().lock(); //good to have, but slows down the program
        //readWriteLock.writeLock().lock();
        //readLock.readLock().lock();
        for (Product billProduct : billToProcess.getProducts())
            for (Product product : allProducts)
                if (billProduct.getName().equals(product.getName())) {
                    //onlyWritingLock.writeLock().lock();
                    product.getMutex().lock();
                    product.setSoldQuantity(billProduct.getQuantity());
                    //onlyWritingLock.writeLock().unlock();
                    product.getMutex().unlock();
                    break;
                }

        //billToProcess.getStrictWritingMutex().lock();
        onlyWritingLock.writeLock().lock();
        //readWriteLock.writeLock().lock();
        //writeLock.writeLock().lock(); //works
        totalMoney += billToProcess.getBillMoney();
        processedBills.add(billToProcess);
        transactionsDone += 1;

        //billToProcess.getStrictWritingMutex().unlock();
        onlyWritingLock.writeLock().unlock();
        //readWriteLock.writeLock().unlock();
        //writeLock.writeLock().unlock(); //works too
        readWriteLock.readLock().unlock();
       if(transactionsDone%10==0)
            checkEverything();
        //readLock.readLock().unlock();
    }

    public String checkEverything() {
        readWriteLock.writeLock().lock();
        HashMap<String, Integer> processedBillProducts = new HashMap<>();
        float allSalesMoney = 0;

        allProducts.forEach(product -> processedBillProducts.put(product.getName(), 0));

        for (Bill bill : processedBills)
            for (Product product : bill.getProducts()) {
                processedBillProducts.merge(product.getName(), product.getQuantity(), Integer::sum);
                allSalesMoney += product.getQuantity() * product.getUnitPrice();
            }

        if (!String.format("%.2f", allSalesMoney).equals(String.format("%.2f",totalMoney))) {
            readWriteLock.writeLock().unlock();
            //onlyWritingLock.unlock();
            return "All sales money: " + String.format("%.2f", allSalesMoney) + " don't equal total Money: " + String.format("%.2f",totalMoney);

        }

        for (Product product : allProducts) {
            int processedQuantity = processedBillProducts.get(product.getName());
            if (processedQuantity != product.getSoldQuantity()) {
                readWriteLock.writeLock().unlock();
                //onlyWritingLock.unlock();
                return product.getName() + " quantity sold: " + processedQuantity +
                        " does not equal recorded sales: " + product.getSoldQuantity();
            }
        }


        readWriteLock.writeLock().unlock();
        //onlyWritingLock.unlock();
        System.out.println("Verified that " + processedBills.size() + " bills are correct!");
        return "Everything is ok in checking. Total sales: " + String.format("%.2f", allSalesMoney);
    }

    float getTotalMoney(){
        return this.totalMoney;
    }

    int getTransactionsDone(){
        return this.transactionsDone;
    }
}