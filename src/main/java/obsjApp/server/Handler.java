package obsjApp.server;

import java.io.IOException;
import java.net.Socket;

public class Handler extends Thread {
    Socket sock;
    public Handler(Socket acceptedSocket) throws IOException {
        this.sock = acceptedSocket;
    }

    @Override
    public void run() {

    }
}
