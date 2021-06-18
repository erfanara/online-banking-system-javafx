package obsjApp.client;

import obsjApp.server.Server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args)
            throws IOException {
        InetAddress addr = InetAddress.getByName(null);
        System.out.println("addr = " + addr);

        try (Socket socket = new Socket(addr, Server.PORT)) {
            System.out.println("socket = " + socket);
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()));
            // Output is automatically flushed
            // by PrintWriter:
            PrintWriter out =
                    new PrintWriter(
                            new BufferedWriter(
                                    new OutputStreamWriter(
                                            socket.getOutputStream())), true);


            for (int i = 0; i < 10; i++) {
                System.out.println("sending: ping" + i);
                out.println("ping" + i);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println("received: " + in.readLine());
            }
            out.println("END");

        }
    }
}
