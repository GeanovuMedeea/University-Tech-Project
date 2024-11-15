import Model.Bill;

import java.util.List;

public class ProcessThread implements Runnable {
    private final Transactions transactions;
    private final List<Bill> bills;
    private final int threadNumber;
    private final int start;
    private final int end;
    public ProcessThread(Transactions transactions, List<Bill> bills, int threadNumber, int start, int end)
    {
        this.transactions=transactions;
        this.bills = bills;
        this.threadNumber=threadNumber;
        this.start = start;
        this.end = end-1;
    }

    @Override
    public void run() {
        System.out.println("Thread " + threadNumber+ " having bills from index " + start + " to " +end);
        for (Bill bill : bills) {
            System.out.println("Thread " + threadNumber + " is processing Bill " + bill.getBillNumber());
            try {
                transactions.processBill(bill);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}