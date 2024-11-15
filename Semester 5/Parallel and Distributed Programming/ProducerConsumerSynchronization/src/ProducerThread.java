import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerThread implements Runnable{
    private final int queueSize;
    private final int numberOfPairs;
    private final List<Pair> listOfNumbers;
    private final Condition notFull;

    private final Queue<Integer> queue;
    private final ReentrantLock mutex;

    ProducerThread(List<Pair> listOfNumbers, int numberOfPairs, Queue<Integer> queue, ReentrantLock mutex, Condition condition){
        this.listOfNumbers = listOfNumbers;
        this.numberOfPairs = numberOfPairs;
        this.queue = queue;
        this.mutex = mutex;
        this.queueSize = numberOfPairs/2+1;
        this.notFull = condition;
    }

    @Override
    public void run() {
        for(int index=0;index<numberOfPairs;index++) {
            Pair pairItem = listOfNumbers.get(index);
            try {
                computeProduct(pairItem.getFirst(), pairItem.getSecond());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void computeProduct(int first, int second) throws InterruptedException {
        this.mutex.lock();
        try {
            while (this.queue.size() >= this.queueSize) {
                System.out.println("The queue is full. Producer is waiting. Queue size: " + this.queueSize);
                this.notFull.await();
            }
            int product = first * second;
            this.queue.add(product);
            System.out.println("Producer computed product of " + first + " and " + second + " equal to " + product);
            this.notFull.signal();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        finally {
            this.mutex.unlock();

        }
    }
}
