package utils;

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import core.ProductLine;

public class ThreadManager {
    private static Thread[] threadPool = new Thread[6];
    private static Queue<ProductLine> waiting = new PriorityBlockingQueue<>(1,
                             (pl1, pl2) -> {return pl2.getPriority() - pl1.getPriority();});

    /**
     * Adds a product line to the waiting queue.
     * 
     * @param pl the productline to add
     */
    public static void employ(ProductLine pl) {
        if (pl == null) {
            return;
        }

        waiting.add(pl);
    }

    /**
     * Searchs for a dead thread in the thread pool, if found, it assigns the top 
     * {@code ProductLine} in the {@code PriorityQueue} to it, or else, 
     * the method does nothing.
     */
    public static void assign() {
        for (Thread t : threadPool) {
            if (!t.isAlive()) {
                t = new Thread(waiting.remove());
                t.start();
            }
        }
    }
}
