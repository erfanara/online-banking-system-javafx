package obsjApp.server;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;

import java.io.*;
import java.net.Socket;

public class Handler extends Thread {
    private Socket sock;
    PrintWriter out;
    BufferedReader in;

    public Handler(Socket acceptedSocket) throws IOException {
        this.sock = acceptedSocket;
        in = new BufferedReader(
                new InputStreamReader(
                        sock.getInputStream()));
        // autoFlush enabled
        out = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                sock.getOutputStream())), true);

        start();
    }

    @Override
    public void run() {
        try{

        }finally {
            try {
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
