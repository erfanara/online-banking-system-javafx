package obsjApp.server;

import obsjApp.core.Account;
import obsjApp.core.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.math.BigDecimal;
import java.net.Socket;

public class Handler implements Runnable {
    private User user = null;
    private final Socket sock;
    private PrintWriter out;
    private BufferedReader in;

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
                        case "3" -> createAccResponse();

                        case "4" -> sendAllAccInfo();

                        case "5" -> sendAccById();

                        case "7" -> setAccAlias();

                        case "9" -> transactionResponse();

                        case "13" -> closeAccResponse();
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
        User u = ServerCli.db.readUser((String) loginJa.get(0));
        if (u != null) {
            if (u.auth((String) (loginJa.get(1)))) {
                user = u;
                send("0");
            } else {
                rejectionResponse("WrongPassword");
            }
        } else {
            rejectionResponse("InvalidUsername");
        }
    }

    private void signUpResponse() throws IOException {
        send("0");
        JSONArray signUpJa = new JSONArray(receive());
        // TODO: validation of received data
        if (!ServerCli.db.userExist((String) (signUpJa.get(2)))) {
            ServerCli.db.writeUser(new User(
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

    private void createAccResponse() throws IOException {
        send("0");
        JSONArray ja = new JSONArray(receive());
        // TODO: validation of received data

        if (ja.get(1) == null || user.getAccByAlias((String) (ja.get(1))) == null) {
            // TODO: use the returned id and send it to client
            user.createAcc(
                    Account.Type.valueOf((String) (ja.get(0))),
                    (String) (ja.get(1)),
                    (String) (ja.get(2))
            );

            // update the db
            ServerCli.db.writeUser(user);

            send("0");
        } else
            rejectionResponse("DuplicateAlias");
    }

    private void sendAllAccInfo() throws IOException {
        send("0");

        JSONArray ja = new JSONArray();
        String[] ids = user.getAllAccIds();
        for (String id : ids) {
            ja.put(id);
        }
        send(ja.toString());
    }

    private void sendAccById() throws IOException {
        send("0");

        String id = receive();
        Account ac = user.getAccById(id);

        JSONObject jo = new JSONObject();
        jo.put("type", ac.getType());
        jo.put("alias", ac.getAlias());
        jo.put("balance", ac.getBalance().toString());
        jo.put("creationDate", ac.getCreationDate());

        jo.accumulate("transactions", ac.getTransactions());

        send(jo.toString());
    }

    private void setAccAlias() throws IOException {
        send("0");

        Account ac = user.getAccById(receive());
        if (ac != null) {
            // TODO: validation of received data
            ac.setAlias(receive());

            // update the db
            ServerCli.db.writeUser(user);

            send("0");
        } else
            rejectionResponse("InvalidAccId");
    }

    private void transactionResponse() throws IOException {
        send("0");

        JSONArray ja = new JSONArray(receive());
        Account srcAcc = user.getAccById((String) ja.get(0));
        BigDecimal amount = new BigDecimal((String) ja.get(1));
        Account destAcc = User.getAccByIdInAll((String) ja.get(3));
        if (srcAcc == null) {
            rejectionResponse("InvalidSrcAccId");
            return;
        }
        if (destAcc == null) {
            rejectionResponse("InvalidDestAccId");
            return;
        }
        if (amount.compareTo(new BigDecimal(0)) < 0) {
            rejectionResponse("InvalidAmount");
            return;
        }

        if (srcAcc.auth((String) ja.get(2))) {
            if (srcAcc.withdraw(amount, destAcc)) {
                send("0");
            } else
                rejectionResponse("InsufficientFunds");

            // update the db
            ServerCli.db.writeUser(user);
            ServerCli.db.writeUser(destAcc.getOwner());
        } else
            rejectionResponse("WrongPassword");

    }

    private void closeAccResponse() throws IOException {
        send("0");

        Account acc = user.getAccById(receive());
        if (acc == null) {
            rejectionResponse("InvalidSrcAccId");
            return;
        }

        if (acc.auth(receive())) {
            if (acc.getBalance().compareTo(new BigDecimal(0)) == 0) {
                user.removeAcc(acc.getId());

                // update the db
                ServerCli.db.writeUser(user);

                send("0");
            } else
                rejectionResponse("BalanceIsNotZero");
        }
    }
}
