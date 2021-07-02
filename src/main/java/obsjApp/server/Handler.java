package obsjApp.server;

import obsjApp.core.Account;
import obsjApp.core.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class Handler implements Runnable {
    private static final ObjectStorage db = new ObjectStorage("Users");
    // Map for loading users into the ram for faster operations
//    private static final Map<String, User> usersMap = new LinkedHashMap<String, User>();
    private User user = null;
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

    @Override
    public void run() {
        try {
            String str = in.readLine();
            while (str != null) {
                // check user log in
                if (user == null) {
                    switch (str) {
                        case "1" -> logInResponse();

                        case "2" -> signUpResponse();

                        default -> rejectionResponse("LogInFirst");
                    }
                } else {
                    switch (str) {
//                        case "4" -> sendAllAccInfo();

                        case "5" -> sendAccById();

                        case "6" -> setAccInfo();
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

    public void rejectionResponse(String reason) {
        send("-1");
        send(reason);
    }

    private void logInResponse() throws IOException {
        send("0");
        JSONArray loginJa = new JSONArray(receive());
        // TODO: validation of received data
        try {
            User u = db.readUser((String) (loginJa.get(0)));
            if (u.auth((String) (loginJa.get(1)))) {
                user = u;
                send("0");
            } else {
                rejectionResponse("wrongPassword");
            }
        } catch (ClassNotFoundException | FileNotFoundException e) {
            rejectionResponse("InvalidUsername");
        }
    }

    private void signUpResponse() throws IOException {
        send("0");
        JSONArray signUpJa = new JSONArray(receive());
        // TODO: validation of received data
        if (!db.userExist((String) (signUpJa.get(2)))) {
            db.writeUser(new User(
                    (String) (signUpJa.get(0)),
                    (String) (signUpJa.get(1)),
                    (String) (signUpJa.get(2)),
                    (String) (signUpJa.get(3)),
                    (String) (signUpJa.get(4)),
                    (String) (signUpJa.get(5))
            ));
            send("0");
        } else {
            rejectionResponse("UserAlreadyExist");
        }
    }

//    private void sendAllAccInfo() throws IOException {
//        send("0");
//
//        send(new JSONArray());
//    }

    private void sendAccById() throws IOException {
        send("0");

        String id = receive();
        Account ac = user.getAccById(id);

        JSONObject jo = new JSONObject();
        jo.put("alias", ac.getAlias());
        jo.put("balance", ac.getBalance().toString());
        jo.put("creationDate", ac.getCreationDate());

        JSONArray ja = new JSONArray();
        ja.put(ac.getTransactions());
        jo.accumulate("transactions", ja);

        send(jo.toString());
    }

    private void setAccInfo() throws IOException {

    }
}
