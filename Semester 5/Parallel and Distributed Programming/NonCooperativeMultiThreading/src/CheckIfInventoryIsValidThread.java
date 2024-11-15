import java.util.concurrent.TimeUnit;

public class CheckIfInventoryIsValidThread implements Runnable {
    private final Transactions transactions;
    private final int threadNumber;

    public CheckIfInventoryIsValidThread(Transactions transactions, int threadNumber){
        this.transactions=transactions;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String message = transactions.checkEverything();
        System.out.println("Thread " + threadNumber + " concluded that " + message);
    }
}