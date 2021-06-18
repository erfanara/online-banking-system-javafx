package obsjApp.client;

import obsjApp.server.Server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args)
            throws IOException {
        // Passing null to getByName() produces the
        // special "Local Loopback" IP address, for
        // testing on one machine w/o a network:
        InetAddress addr =
                InetAddress.getByName(null);
        // Alternatively, you can use
        // the address or name:
        // InetAddress addr =
        //    InetAddress.getByName("127.0.0.1");
        // InetAddress addr =
        //    InetAddress.getByName("localhost");
        System.out.println("addr = " + addr);
        // Guard everything in a try-finally to make
        // sure that the socket is closed:
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

        }
    }
}
