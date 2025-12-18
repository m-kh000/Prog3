package utils;

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import core.ProductLine;

public class ThreadManager {
    private static Thread[] threadPool = new Thread[6];
    private static Queue<ProductLine> waiting = new PriorityBlockingQueue<>(1,
                             (pl1, pl2) -> {return pl2.getPriority() - pl1.getPriority();});

    /**
     * 
     * @param pl
     */
    public static void employ(ProductLine pl) {
        if (pl == null) {
            return;
        }

        waiting.add(pl);
    }

    /**
     * 
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
