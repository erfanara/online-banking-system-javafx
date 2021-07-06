package OBSApp.client;

import OBSApp.core.exceptions.*;
import javafx.scene.image.Image;
import OBSApp.core.Account;
import OBSApp.server.Server;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.Socket;

public class Client{
    public final InetAddress addr = InetAddress.getByName(null);
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;


    public Client() throws IOException {
        System.out.println("addr = " + addr);
        socket = new Socket(addr, Server.DEFAULT_PORT);
        System.out.println("socket = " + socket);

        in = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
        // Output is automatically flushed
        // by PrintWriter:
        out = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream())), true);
    }

    public void send(String msg) {
        out.println(msg);
    }

    public String receive() throws IOException {
        return in.readLine();
    }

    public void throwException(String reason) throws Exception {
        switch (reason) {
            case "LogInFirst" -> throw new LogInFirstException();

            case "InvalidUsername" -> throw new InvalidUsernameException();

            case "UserAlreadyExist" -> throw new UserAlreadyExistException();

            case "WrongPassword" -> throw new WrongPasswordException();

            case "DuplicateAlias" -> throw new DuplicateAliasException();

            case "InvalidSrcAccId" -> throw new InvalidSrcAccIdException();

            case "InvalidDestAccId" -> throw new InvalidDestAccIdException();

            case "InvalidAmount" -> throw new InvalidAmountException();

            case "InsufficientFunds" -> throw new InsufficientFundsException();

            case "BalanceIsNotZero" -> throw new BalanceIsNotZeroException();

            case "PictureNotFound" -> throw new PictureNotFoundException();

            case "BillAlreadyPaid" -> throw new BillAlreadyPaidException();

            case "BillNotFound" -> throw new BillNotFoundException();
        }
    }

    // TODO: throwing Exception is so imprecise
    // TODO: try to change return type from JSONArray to Array
    public boolean checkServerResponse() throws Exception {
        switch (receive()) {
            case "0":
                return true;
            case "-1":
                throwException(receive());
        }
        return false;
    }

    public boolean loginRequest(String username, String passHash) throws Exception {
        send("1");
        checkServerResponse();

        JSONObject ja = new JSONObject();
        ja.put("username", username);
        ja.put("passhash", passHash);

        send(ja.toString());

        return checkServerResponse();
    }

    public boolean signupRequest(String firstName,
                                 String lastName,
                                 String nationalCode,
                                 String phoneNumber,
                                 String email,
                                 String password
    ) throws Exception {
        send("2");
        checkServerResponse();

        JSONObject jo = new JSONObject();
        jo.put("firstname", firstName);
        jo.put("lastname", lastName);
        jo.put("nationalCode", nationalCode);
        jo.put("phoneNumber", phoneNumber);
        jo.put("email", email);
        jo.put("password", password);

        send(jo.toString());

        return checkServerResponse();
    }

    public boolean createAcc(Account.Type type, String alias, String accPassword) throws Exception {
        send("3");
        checkServerResponse();

        JSONObject jo = new JSONObject();
        jo.put("type", type.toString());
        jo.put("alias", (alias != null) ? alias : "");
        jo.put("accPass", accPassword);
        send(jo.toString());

        // TODO: receive the id of created Acc and then fetch the accInfo
        return checkServerResponse();
    }

    public JSONArray getAllAccInfo() throws Exception {
        send("4");
        checkServerResponse();

        String str = receive();
        System.out.println(str);
        JSONArray accJa = new JSONArray(str);

        //debug purposes:
        System.out.println(accJa);
        return accJa;
    }

    public JSONObject getAccById(String id) throws Exception {
        send("5");
        checkServerResponse();

        send(id);

        return new JSONObject(receive());
    }

    public JSONArray getAllAccIDs() throws Exception {
        send("6");
        checkServerResponse();

        return new JSONArray(receive());
    }

    public boolean setAccAlias(String accId, String alias) throws Exception {
        send("7");
        checkServerResponse();

        JSONObject jo = new JSONObject();
        jo.put("accId", accId);
        jo.put("alias", alias);

        send(jo.toString());
        return checkServerResponse();
    }

    public boolean transaction(String srcId, String srcAccPassword, BigDecimal amount, String destId) throws Exception {
        send("9");
        checkServerResponse();

        JSONObject jo = new JSONObject();
        jo.put("srcId", srcId);
        jo.put("amount", amount.toString());
        jo.put("accPass", srcAccPassword);
        jo.put("destId", destId);

        send(jo.toString());
        return checkServerResponse();
    }

    public JSONArray getCurrentBills() throws Exception {
        send("10");
        checkServerResponse();

        return new JSONArray(receive());
    }

    public JSONArray getPayedBills() throws Exception {
        send("18");
        checkServerResponse();

        return new JSONArray(receive());
    }

    public boolean payBill(String billId, String paymentId, String fromAcc, String accPass) throws Exception {
        send("17");
        checkServerResponse();

        JSONObject jo = new JSONObject();
        jo.put("key", billId + paymentId);
        jo.put("accId", fromAcc);
        jo.put("accPass", accPass);

        send(jo.toString());
        return checkServerResponse();
    }

    public boolean loanRequest(BigDecimal amount, int paymentPeriodInDays) throws Exception {
        send("11");
        checkServerResponse();

        JSONObject jo = new JSONObject();
        jo.put("amount", amount);
        jo.put("ppDays", paymentPeriodInDays);

        send(jo.toString());
        return checkServerResponse();
    }

    public JSONArray getLoans() throws Exception {
        send("16");
        checkServerResponse();

        return new JSONArray(receive());
    }

    public boolean closeAcc(String id, String accPassword) throws Exception {
        send("13");
        checkServerResponse();

        JSONObject jo = new JSONObject();
        jo.put("id", id);
        jo.put("accPass", accPassword);

        send(jo.toString());
        return checkServerResponse();
    }

    public JSONObject getUserInfo() throws Exception {
        send("14");
        checkServerResponse();

        return new JSONObject(receive());
    }

    public Image getProfilePic() throws Exception {
        send("15");
        checkServerResponse();

        checkServerResponse();
        ByteArrayInputStream stream = new ByteArrayInputStream(receive().getBytes());
        return new Image(stream);
    }

    public void closeSocket() throws Exception {
        socket.close();
    }

    // just for test
    public static void main(String[] args)
            throws Exception {
        Client test = new Client();
//        test.signupRequest("test", "test2", "123456789", "123123", "alo@gmail.com", "testtest321");
//        System.out.println(test.loginRequest("123456789", "testtest321"));
//        System.out.println(test.createAcc(Account.Type.CHECKING, "lol", "ajab"));
//        test.getAllAccInfo();
//        Client test2 = new Client();
//        test2.signupRequest("ali", "irv", "12312345", "0915551233", "ali@gmail.com", "aliali");
//        System.out.println(test2.loginRequest("12312345","aliali"));
//        System.out.println(test2.createAcc(Account.Type.CHECKING,null,"ahsant"));
//        test2.getAllAccInfo();

        System.out.println(test.loginRequest("099999998", null));
        System.out.println(test.getUserInfo());
        System.out.println(test.getAllAccInfo());
    }
}
