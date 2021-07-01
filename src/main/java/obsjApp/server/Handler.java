package obsjApp.server;

import obsjApp.core.User;
import org.json.JSONArray;

import java.io.*;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class Handler implements Runnable {
    private static final ObjectStorage db = new ObjectStorage("Users");
    private static final Map<String, User> users = new LinkedHashMap<String, User>();
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
            String str = in.readLine();
            while (!str.equals("null")) {
                if (!userLoggedIn) {
                    switch (str) {
                        // LogIn request
                        case "1" -> {
                            send("0");
                            JSONArray loginJa = new JSONArray(receive());
                            // TODO: validation of received data
                            try {
                                User u = db.readUser((String) (loginJa.get(0)));
                                if (u.auth((String) (loginJa.get(1)))) {
                                    userLoggedIn = true;
                                    send("0");
                                }
                            } catch (ClassNotFoundException | FileNotFoundException e) {
                                rejectionResponse("InvalidUsername");
                            }
                        }

                        // SignUp request
                        case "2" -> {
                            send("0");
                            JSONArray signUpJa = new JSONArray(receive());
                            // TODO: validation of received data
                            db.writeUser(new User(
                                    (String) (signUpJa.get(0)),
                                    (String) (signUpJa.get(1)),
                                    (String) (signUpJa.get(2)),
                                    (String) (signUpJa.get(3)),
                                    (String) (signUpJa.get(4)),
                                    (String) (signUpJa.get(5))
                            ));
                            send("0");
                        }

                        default -> rejectionResponse("LogInFirst");
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

                        // setAccInfo
                        case "6":
                            send("0");
                            break;

                    }
                }
                str = in.readLine();
            }
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
