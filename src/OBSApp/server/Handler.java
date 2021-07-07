package OBSApp.server;

import OBSApp.core.Account;
import OBSApp.core.Bill;
import OBSApp.core.Loan;
import OBSApp.core.User;
import OBSApp.core.exceptions.BillAlreadyPaidException;
import OBSApp.core.exceptions.BillNotFoundException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.Map;
import java.util.jar.JarException;

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

                        case "6" -> sendAllAccIDs();

                        case "7" -> setAccAlias();

                        case "9" -> transactionResponse();

                        case "10" -> sendCurrentBills();

                        case "11" -> loanResponse();

                        case "13" -> closeAccResponse();

                        case "14" -> sendUserInfo();

                        case "15" -> sendProfilePic();

                        case "16" -> sendCurrentLoans();

                        case "17" -> payBillResponse();

                        case "18" -> sendPayedBills();

                        case "100" -> imAlive();
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
        JSONObject loginJo = new JSONObject(receive());
        // TODO: validation of received data
        User u = ServerCli.db.readUser((String) loginJo.get("username"));
        if (u != null) {
            if (u.auth((String) (loginJo.get("passhash")))) {
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
        JSONObject signUpJo = new JSONObject(receive());
        // TODO: validation of received data
        if (!ServerCli.db.userExist((String) signUpJo.get("nationalCode"))) {
            ServerCli.db.writeUser(new User(
                    (String) signUpJo.get("firstname"),
                    (String) signUpJo.get("lastname"),
                    (String) signUpJo.get("nationalCode"),
                    (String) signUpJo.get("phoneNumber"),
                    (String) signUpJo.get("email"),
                    (String) signUpJo.get("password")
            ));
            send("0");
        } else {
            rejectionResponse("UserAlreadyExist");
        }
    }

    private void createAccResponse() throws IOException {
        send("0");
        JSONObject jo = new JSONObject(receive());
        // TODO: validation of received data

        if (jo.get("alias") == null || user.getAccByAlias((String) (jo.get("alias"))) == null) {
            // TODO: use the returned id and send it to client
            user.createAcc(
                    Account.Type.valueOf((String) jo.get("type")),
                    (String) jo.get("alias"),
                    (String) jo.get("accPass")
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
        for (String id : user.getAllAccIds()) {
            Account ac = user.getAccById(id);
            JSONObject jo = new JSONObject();
            jo.put("id", ac.getId());
            jo.put("type", ac.getType());
            jo.put("alias", ac.getAlias());
            jo.put("balance", ac.getBalance().toString());
            jo.put("creationDate", ac.getCreationDate());

            ja.put(jo);
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

    private void sendAllAccIDs() {
        send("0");

        JSONArray ja = new JSONArray();
        for (String id : user.getAllAccIds()) {
            ja.put(id);
        }

        send(ja.toString());
    }

    private void setAccAlias() throws IOException {
        send("0");

        JSONObject jo = new JSONObject(receive());
        Account ac = user.getAccById((String) jo.get("accId"));
        if (ac != null) {
            // TODO: validation of received data
            ac.setAlias((String) jo.get("alias"));

            // update the db
            ServerCli.db.writeUser(user);

            send("0");
        } else
            rejectionResponse("InvalidAccId");
    }

    private void transactionResponse() throws IOException {
        send("0");

        JSONObject jo = new JSONObject(receive());
        Account srcAcc = user.getAccById((String) jo.get("srcId"));
        BigDecimal amount = new BigDecimal((String) jo.get("amount"));
        Account destAcc = User.getAccByIdInAll((String) jo.get("destId"));
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

        if (srcAcc.auth((String) jo.get("accPass"))) {
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

        JSONObject jo = new JSONObject(receive());
        Account acc = user.getAccById((String) jo.get("id"));
        if (acc == null) {
            rejectionResponse("InvalidSrcAccId");
            return;
        }

        try {
            if (acc.auth((String) jo.get("accPass"))) {
                if (acc.getBalance().compareTo(new BigDecimal(0)) == 0) {
                    user.removeAcc(acc.getId());

                    // update the db
                    ServerCli.db.writeUser(user);

                    send("0");
                } else
                    rejectionResponse("BalanceIsNotZero");
            } else {
                rejectionResponse("WrongPassword");
            }

        } catch (JSONException e) {
            rejectionResponse("WrongPassword");
        }
    }

    private void sendUserInfo() {
        send("0");

        JSONObject jo = new JSONObject();
        jo.put("firstName", user.getFirstname());
        jo.put("lastName", user.getLastName());
        jo.put("nationalCode", user.getNationalCode());
        jo.put("phoneNumber", user.getPhoneNumber());
        jo.put("email", user.getEmail());

        send(jo.toString());
    }

    private void sendProfilePic() {
        send("0");

        String imgStr = null;
        try {
            imgStr = ServerCli.db.getProfilePicInStr(user.getNationalCode());
            send("0");
        } catch (IOException e) {
            rejectionResponse("PictureNotFound");
        }
        send(imgStr);
    }

    private void sendCurrentBills() {
        send("0");

        JSONArray ja = new JSONArray();
        for (Map.Entry<String, Bill> entry : user.currentBills.entrySet()) {
            JSONObject jo = new JSONObject(entry.getValue());
            ja.put(jo);
        }

        send(ja.toString());
    }

    private void sendPayedBills() {
        send("0");

        JSONArray ja = new JSONArray();
        for (Map.Entry<String, Bill> entry : user.currentBills.entrySet()) {
            JSONObject jo = new JSONObject(entry.getValue());
            ja.put(jo);
        }

        send(ja.toString());
    }

    private void payBillResponse() throws IOException {
        send("0");

        JSONObject jo = new JSONObject(receive());
        Account acc = user.getAccById((String) jo.get("accId"));
        String pass = (String) jo.get("accPass");
        if (acc != null)
            if (acc.auth(pass))
                try {
                    BillManagment.payBill(user, acc, (String) jo.get("key"));
                } catch (BillAlreadyPaidException e) {
                    rejectionResponse("BillAlreadyPaid");
                } catch (BillNotFoundException e) {
                    rejectionResponse("BillNotFound");
                }
            else
                rejectionResponse("WrongPassword");
        else
            rejectionResponse("InvalidSrcAccId");
    }

    private void sendCurrentLoans() {
        send("0");

        JSONArray ja = new JSONArray();
        for (Map.Entry<String, Loan> entry : user.currentLoans.entrySet()) {
            JSONObject jo = new JSONObject(entry.getValue());
            ja.put(jo);
        }

        send(ja.toString());
    }

    // TODO : incompleted section
    private void loanResponse() throws IOException {
        send("0");

        JSONObject jo = new JSONObject(receive());


    }

    // DEBUGGING PURPOSES
    private void imAlive() {
        rejectionResponse("ImAliveIdiot");
    }
}
