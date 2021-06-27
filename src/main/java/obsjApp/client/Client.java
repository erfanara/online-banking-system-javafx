package obsjApp.client;

import obsjApp.server.Server;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

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

    public void blockingSend(String msg) {
        out.println(msg);
    }

    public String blockingReceive() throws IOException {
        return in.readLine();
    }

    public boolean loginRequest(String username, String passHash) throws IOException {
        blockingSend("1");

        JSONArray ja = new JSONArray();
        ja.put(username);
        ja.put(passHash);

        blockingSend(ja.toString());

        return blockingReceive().equals("0");
    }

    public boolean signupRequest(String firstName,
                                 String lastName,
                                 String nationalCode,
                                 String phoneNumber,
                                 String email,
                                 String passHash
    ) throws IOException {
        blockingSend("2");

        JSONArray ja = new JSONArray();
        ja.put(firstName);
        ja.put(lastName);
        ja.put(nationalCode);
        ja.put(phoneNumber);
        ja.put(email);
        ja.put(passHash);

        blockingSend(ja.toString());

        return blockingReceive().equals("0");
    }

//    public boolean createAcc() throws IOException {
//
//    }

    public JSONArray getAllAccInfo() throws IOException {
        blockingSend("4");

        return new JSONArray(blockingReceive());
    }

    public JSONObject getAccById(int id) throws IOException {
        blockingSend("5");

        blockingSend("" + id);

        return new JSONObject(blockingReceive());
    }


    public boolean setAccinfo(int accid, int newId) throws IOException {
        // TODO: new kind of exception needed if something is not valid
        blockingSend("6");

        blockingSend("" + accid);
        blockingSend("" + newId);

        return blockingReceive().equals("0");
    }

    public boolean setAccInfo(int accId, String alias) throws IOException {
        // TODO: new kind of exception needed if something is not valid
        blockingSend("7");

        blockingSend("" + accId);
        blockingSend(alias);

        return blockingReceive().equals("0");
    }

//    public boolean transaction()
//    public JSONArray getTheBills()
//    public JSONArray loanRequest()

    public boolean authAcc() throws IOException {
        String passHash = null;
        int cvv2 = 0;

        //TODO : now we should get the password and cvv2 from the user

        blockingSend(passHash);
        blockingSend("" + cvv2);

        return blockingReceive().equals("0");
    }

    public boolean closeAcc(int id) throws IOException {
        blockingSend("13");

        blockingSend("" + id);

        if (blockingReceive().equals("12")) return authAcc();
        // TODO: new kind of exception needed if something is not valid
        return false;
    }

    public JSONArray getUserInfo() throws IOException {
        blockingSend("14");

        return new JSONArray(blockingReceive());
    }


    public static void main(String[] args)
            throws IOException {
    }
}
