import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class GraphTask implements Callable<Void> {
    private final Graph graph;
    private final int currentNode;
    private final List<Integer> path;
    private final AtomicBoolean found;
    private final List<Integer> resultPath;
    private final ExecutorService executor;
    private final int maxDepth;
    ReentrantReadWriteLock.WriteLock lock;

    public GraphTask(Graph graph, int currentNode, List<Integer> path, AtomicBoolean found,
                     List<Integer> resultPath, ExecutorService executor, int maxDepth, ReentrantReadWriteLock.WriteLock lock) {
        this.graph = graph;
        this.currentNode = currentNode;
        this.path = new ArrayList<>(path);
        this.found = found;
        this.resultPath = resultPath;
        this.executor = executor;
        this.maxDepth = maxDepth;
        this.lock = lock;
        //System.out.println(Thread.currentThread().threadId());
    }

    @Override
    public Void call() {
        if (found.get()) return null;

        path.add(currentNode);
        System.out.println("Path: "+ path + " thread: " + Thread.currentThread().toString());

        if (path.size() == graph.getNodes().size() && graph.hasEdge(currentNode, path.get(0))) {
            synchronized (found) {
                if (!found.get()) {
                    lock.lock();
                    found.set(true);
                    path.add(path.get(0));
                    resultPath.addAll(path);
                    lock.unlock();
                }
            }
            return null;
        }

        List<Future<?>> futures = new ArrayList<>();

        for (int neighbor : graph.neighboursOf(currentNode)) {
            if (!path.contains(neighbor) && !found.get()) {
                if (path.size() < maxDepth) {
                    futures.add(executor.submit(new GraphTask(graph, neighbor, path, found, resultPath, executor, maxDepth, lock)));
                } else {
                    new GraphTask(graph, neighbor, path, found, resultPath, executor, maxDepth, lock).call();
                }
            }
        }

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }
}
