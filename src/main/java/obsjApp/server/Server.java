package obsjApp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public final class Server {
    public static final int DEFAULT_PORT = 48080;

    /*
     *  http://dev.bizo.com/2014/06/cached-thread-pool-considered-harmlful.html ,
     *  https://mucahit.io/2020/01/27/finding-ideal-jvm-thread-pool-size-with-kubernetes-and-docker/
     */
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors() * 20);

    private static boolean finished = false;

    private static void setFinished() {
        finished = true;
    }

    /*
     * This method is from java api doc (ExecutorService Interface)
     */
    private static void shutdownAndAwaitTermination() {
        threadPool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!threadPool.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            threadPool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws IOException {

        try (ServerSocket ss = new ServerSocket(DEFAULT_PORT)) {
            System.out.println("Server Started , Port: " + DEFAULT_PORT);

            // If process received SIGTERM, shutdown the server
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\nTerminating the Server...");
                setFinished();
                shutdownAndAwaitTermination();
                Runtime.getRuntime().halt(0);
            }));

            while (!finished) {
                // Blocks until a connection occurs:
                Socket cs = ss.accept();
                Logger.getGlobal().info("socket accepted : " + cs);
                try {
                    threadPool.execute(new Handler(cs));
                } catch (IOException e) {
                    // If it fails, close the socket,
                    // otherwise the thread will close it
                    cs.close();
                }
            }
        }
    }
}
