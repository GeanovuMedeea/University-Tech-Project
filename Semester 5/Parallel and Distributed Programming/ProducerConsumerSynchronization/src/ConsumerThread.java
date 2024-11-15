import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConsumerThread implements Runnable{

    private int sumOfProducts;
    private final int numberOfPairs;
    private final ReentrantLock mutex;
    private final Condition notEmpty;

    private final Queue<Integer> queue;

    ConsumerThread(Queue<Integer> queue, int numberOfPairs, ReentrantLock mutex, Condition condition)
    {
        sumOfProducts = 0;
        this.queue =queue;
        this.numberOfPairs = numberOfPairs;
        this.mutex=mutex;
        notEmpty = condition;
    }

    public void addToSum() throws InterruptedException {
        this.mutex.lock();
        try {
            while (this.queue.isEmpty()) {
                System.out.println("The queue is empty. Consumer is waiting");
                notEmpty.await();
            }
            int product = this.queue.remove();
            this.sumOfProducts += product;
            System.out.println("Consumer updated the final sum to:" + sumOfProducts);
            this.notEmpty.signal();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        finally {
            this.mutex.unlock();
        }

    }

    @Override
    public void run(){
        for(int index=0;index<numberOfPairs;index++)
        {
            try {
                addToSum();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Thread computed sum: " + getSumOfProducts());
    }

    public int getSumOfProducts() {
        return sumOfProducts;
    }
}
