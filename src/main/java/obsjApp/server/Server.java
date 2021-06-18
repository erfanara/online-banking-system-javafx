package obsjApp.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class Server {
    public static final int PORT = 48080;
//    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private static boolean finished = false;

    private static void setFinished() {
        finished = true;
    }

    public static void main(String[] args) throws IOException {

        try (ServerSocket ss = new ServerSocket(PORT)) {
            System.out.println("Server Started , Port: " + PORT);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\nTerminating the Server...");
                setFinished();
                threadPool.shutdown();
                try {
                    threadPool.awaitTermination(1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Runtime.getRuntime().halt(0);
            }));

            while (!finished) {
                // Blocks until a connection occurs:
                Socket cs = ss.accept();
                System.out.println("socket accepted= " + cs);
                try {
                    threadPool.execute(new Handler(cs));
//                    new Handler(cs);
                } catch (IOException e) {
                    // If it fails, close the socket,
                    // otherwise the thread will close it
                    cs.close();
                }
            }
        }
    }
}
