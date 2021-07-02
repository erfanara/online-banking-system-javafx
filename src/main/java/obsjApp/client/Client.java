package obsjApp.client;

import obsjApp.core.Account;
import obsjApp.server.Server;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

class LogInFirstException extends RuntimeException {
    public LogInFirstException() {
        super("you have to LogIn first");
    }
}

class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException() {
        super("Username is invalid");
    }
}

class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super("This user with this national code already Exist");
    }
}

class WrongPasswordException extends RuntimeException {
    public WrongPasswordException() {
        super("Password is wrong");
    }
}

class DuplicateAliasExeception extends RuntimeException {
    public DuplicateAliasExeception() {
        super("this alias is already submited for another account");
    }
}

public class Client {
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
            case "LogInFirst":
                throw new LogInFirstException();
            case "InvalidUsername":
                throw new InvalidUsernameException();
            case "UserAlreadyExist":
                throw new UserAlreadyExistException();
            case "WrongPassword":
                throw new WrongPasswordException();
            case "DuplicateAlias":
                throw new DuplicateAliasExeception();
        }
    }

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

        JSONArray ja = new JSONArray();
        ja.put(username);
        ja.put(passHash);

        send(ja.toString());

        return checkServerResponse();
    }

    public boolean signupRequest(String firstName,
                                 String lastName,
                                 String nationalCode,
                                 String phoneNumber,
                                 String email,
                                 String passHash
    ) throws Exception {
        send("2");
        checkServerResponse();

        JSONArray ja = new JSONArray();
        ja.put(firstName);
        ja.put(lastName);
        ja.put(nationalCode);
        ja.put(phoneNumber);
        ja.put(email);
        ja.put(passHash);

        send(ja.toString());

        return checkServerResponse();
    }

    public boolean createAcc(Account.Type type, String alias, String accPassword) throws Exception {
        send("3");
        checkServerResponse();

        JSONArray ja = new JSONArray();
        ja.put(type.toString());
        ja.put((alias != null) ? alias : "");
        ja.put(accPassword);
        send(ja.toString());

        // TODO: receive the id of created Acc and then fetch the accInfo
        return checkServerResponse();
    }

    public void getAllAccInfo() throws Exception {
        send("4");
        checkServerResponse();

        JSONArray idsJa = new JSONArray(receive());
        JSONArray accJa = new JSONArray();
        for (String id : idsJa.toList().toArray(new String[0])) {
            accJa.put(getAccById(id));
        }

        System.out.println(accJa);
    }

    public JSONObject getAccById(String id) throws Exception {
        send("5");
        checkServerResponse();

        send(id);

        return new JSONObject(receive());
    }

    public boolean setAccAlias(String accId, String alias) throws Exception {
        send("7");
        checkServerResponse();

        send(accId);
        send(alias);

        return checkServerResponse();
    }

//    public boolean transaction()
//    public JSONArray getTheBills()
//    public JSONArray loanRequest()

    public boolean authAcc() throws Exception {
        String passHash = null;
        int cvv2 = 0;

        //TODO : now we should get the password and cvv2 from the user

        send(passHash);
        send("" + cvv2);

        return receive().equals("0");
    }

    public boolean closeAcc(int id) throws Exception {
        send("13");
        checkServerResponse();

        send("" + id);

        if (receive().equals("12")) return authAcc();
        // TODO: new kind of exception needed if something is not valid
        return false;
    }

    public JSONArray getUserInfo() throws Exception {
        send("14");
        checkServerResponse();

        return new JSONArray(receive());
    }

    public void closeSocket() throws Exception {
        socket.close();
    }

    // just for test
    public static void main(String[] args)
            throws Exception {
        Client test = new Client();
        test.signupRequest("test", "test2", "123456789", "123123", "alo@gmail.com", "testtest321");
        System.out.println(test.loginRequest("123456789", "testtest321"));
        System.out.println(test.createAcc(Account.Type.CHECKING, "lol", "ajab"));
        test.getAllAccInfo();
    }
}
