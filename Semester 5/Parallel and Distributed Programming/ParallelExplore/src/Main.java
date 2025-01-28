import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    public static void printGraph(Graph graph){
        List<Integer> nodes = graph.getNodes();
        List<List<Integer>> edges = graph.getEdges();
        for (int node : nodes) {
            System.out.print("\nNode " + node + "\nEdges:");
            for (int j = 0; j < edges.get(node).size(); j++) {
                System.out.print("[" + node + " -> " + edges.get(node).get(j) + "] ");
            }
        }
    }

    private static Graph generateRandomHamiltonian(int nrOfNodes) {
        Graph graph = new Graph(nrOfNodes);

        List<Integer> nodes = graph.getNodes();

        for (int i = 1; i < nodes.size(); i++){
            graph.addEdge(nodes.get(i - 1),  nodes.get(i));
        }

        graph.addEdge(nodes.get(nodes.size() -1), nodes.get(0));

        Random random = new Random();

        for(int j = 0; j <= 2; j++)
            for (int i = 0; i < nrOfNodes/2; i++){
                int firstNode = random.nextInt(nrOfNodes - 1);
                int secondNode = random.nextInt(nrOfNodes - 1);
                if(!graph.hasEdge(firstNode, secondNode))
                    graph.addEdge(firstNode, secondNode);
            }

        return graph;
    }

    public static void findHamiltonianParallel(Graph graph) {
        int startNode = 0;
        AtomicBoolean found = new AtomicBoolean(false);
        //List<Integer> resultPath = Collections.synchronizedList(new ArrayList<>());
        List<Integer> resultPath = new ArrayList<>();

        //int maxDepth = graph.getNodes().size()/250+1;
        int maxDepth = 3;

        ReentrantReadWriteLock.WriteLock lock = new ReentrantReadWriteLock().writeLock();
        try (ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())) {
            Future<?> future = executor.submit(new GraphTask(graph, startNode, new ArrayList<>(), found, resultPath, executor, maxDepth, lock));
            future.get();

            if (found.get()) {
                System.out.println("Found Hamiltonian cycle: " + resultPath);
            } else {
                System.out.println("No Hamiltonian cycle found.");
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        List<Graph> graphs = new ArrayList<>();

        graphs.add(generateRandomHamiltonian(10));
        graphs.add(generateRandomHamiltonian(500));
        graphs.add(generateRandomHamiltonian(1000));

        System.out.println("Parallel");
        for (int i = 0; i < graphs.size(); i++) {
            //printGraph(graphs.get(i));
            long startTime = System.currentTimeMillis();
            findHamiltonianParallel(graphs.get(i));
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);
            //System.out.println("Nr of nodes " + graphs.get(i).getNodes().size() + ": " + duration + " ms");
        }
    }
}