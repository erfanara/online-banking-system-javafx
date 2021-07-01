package obsjApp.server;

import org.json.JSONArray;

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

    public void send(String msg) {
        out.println(msg);
    }

    public String receive() throws IOException {
        return in.readLine();
    }

    public void rejectionResponse(String reason) {
        send("-1");
        send(reason);
    }

    boolean userLoggedIn = false;


    @Override
    public void run() {
        try {
            String str = null;
            do {
                str = in.readLine();
                if (!userLoggedIn) {
                    switch (str) {
                        // LogIn request
                        case "1":
                            send("0");
                            JSONArray loginJa = new JSONArray(receive());
                            // TODO: ...
                            break;
                        // SignUp request
                        case "2":
                            send("0");
                            JSONArray signUpJa = new JSONArray(receive());
                            // TODO: ...
                            break;
                        default:
                            rejectionResponse("LogInFirst");
                    }
                } else {
                    switch (str) {
                        // sendAllAccInfo
                        case "4":
                            send("0");
                            // TODO: ...
                            break;
                        // sendAccById
                        case "5":
                            send("0");
                            // TODO:...
                            break;
                        case "6":
                            send("0");

                    }
                }
            } while (!str.equals("null"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (!sock.isClosed())
                    sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
