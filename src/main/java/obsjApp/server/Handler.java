package obsjApp.server;

import java.io.*;
import java.net.Socket;

public class Handler implements Runnable {
    private Socket sock;
    PrintWriter out;
    BufferedReader in;

    public Handler(Socket acceptedSocket) throws IOException {
        this.sock = acceptedSocket;
        this.in = new BufferedReader(
                new InputStreamReader(
                        sock.getInputStream()));
        // autoFlush enabled
        this.out = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                sock.getOutputStream())), true);

    }

    @Override
    public void run() {
        try {
//            for (int i = 0; i < 10; i++) {
//                System.out.println("received: " + in.readLine());
//                System.out.println("sending: pong" + i);
//                out.println("pong" + i);
//            }
            while(true){
                System.out.println("received : " + in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
