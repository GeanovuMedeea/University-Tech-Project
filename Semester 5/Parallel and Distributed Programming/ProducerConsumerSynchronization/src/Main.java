import java.security.KeyPair;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class Main {

    public static List<Pair> generateNumberList(int nrOfPairs)
    {
        Random random = new Random();
        List<Pair> listOfPairs = new ArrayList<>();
        for(int index=0;index<nrOfPairs;index++)
        {
            int firstElement = random.nextInt(10)+1;
            int secondElement = random.nextInt(10)+1;
            Pair newPair = new Pair(firstElement,secondElement);
            listOfPairs.add(newPair);
        }
        return listOfPairs;
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        System.out.println("Input the nr of pairs:");
        int nrOfPairs = in.nextInt();
        List<Pair> listOfNumbers = generateNumberList(nrOfPairs);
        Queue<Integer> queue = new LinkedList<>();
        ReentrantLock mutex = new ReentrantLock();
        Condition sharedCondition = mutex.newCondition();
        ProducerThread producer =new ProducerThread(listOfNumbers,nrOfPairs,queue, mutex, sharedCondition);
        ConsumerThread consumer = new ConsumerThread(queue,nrOfPairs, mutex, sharedCondition);
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);
        producerThread.start();
        consumerThread.start();
        producerThread.join();
        consumerThread.join();

        int checkSum = 0;
        for(int index=0;index<nrOfPairs;index++)
        {
            Pair pairItem = listOfNumbers.get(index);
            checkSum+=pairItem.getFirst()*pairItem.getSecond();
        }

        System.out.println("Correct sum: " + checkSum);
    }
}