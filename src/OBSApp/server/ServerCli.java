package OBSApp.server;

import OBSApp.core.Account;
import OBSApp.core.Bill;
import OBSApp.core.User;

import java.math.BigDecimal;
import java.util.Scanner;

public class ServerCli {
    private final static String HELP_MENU = """
            \n\nCOMMANDS >>\s
            deposit <Account Id> <amount>
            issueBill <User national code> <type=power,gas,water,phone> <amount> <subsidiaryCompanyId>
            lsAllUsers
            lsAllAcc
            numberOfUsers
            exit\n\n
            """;

    public static final UserStorage db = new UserStorage("Users");

    // server has a mother account for depositing money to user accounts,
    // the password is just for decoration and some protection.
    public static final MotherAccount motherAcc = new MotherAccount("MyServer!@#", null, new BigDecimal("0"));

    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        Server server = new Server();

        // If process received SIGTERM, shutdown the server
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nTerminating the Server...");
            server.shutdownAndAwaitTermination();
            Runtime.getRuntime().halt(0);
        }));

        String cmd = in.nextLine();
        while (!cmd.equals("exit")) {
            switch (cmd.split(" ")[0]) {
                case "deposit" -> deposit(cmd);
                case "lsAllAcc" -> listAllAcc();
                case "countUsers" -> numberOfUsers();
                case "issueBill" -> issueBill(cmd);
                case "lsAllUsers" -> lsAllUsers();
                default -> System.out.println(HELP_MENU);
            }
            cmd = in.nextLine();
        }

        System.exit(0);
    }

    private static void lsAllUsers() {
        for (User u : UserStorage.getUsers()) {
            System.out.println(u);
        }
    }

    private static void issueBill(String cmd) {
        String[] cmdSplit = cmd.split(" ");
        User u = db.readUser(cmdSplit[1]);
        if (u != null) {
            System.out.println("issue bill to "
                    + u.getFirstname()
                    + " " + u.getLastName()
                    + ".\nAre you sure? Y/n");
            if (in.nextLine().equals("Y")) {
                BillManagment.createBill(u, Bill.Type.valueOf(cmdSplit[2].toUpperCase()), new BigDecimal(cmdSplit[3]), cmdSplit[4]);
                System.out.println("<successful>");
            } else {
                System.out.println("<you cancelled operation>");
            }
        } else {
            System.out.println("wrong user id");
        }
    }

    private static void numberOfUsers() {
        System.out.println(User.getNumberOfUsers());
    }

    private static void deposit(String cmd) {
        String[] cmdSplit = cmd.split(" ");
        Account acc = User.getAccByIdInAll(cmdSplit[1]);
        if (acc != null) {
            System.out.println("deposit to "
                    + acc.getOwner().getFirstname()
                    + " " + acc.getOwner().getLastName()
                    + ".\nAre you sure? Y/n");
            if (in.nextLine().equals("Y")) {
                motherAcc.withdraw(new BigDecimal(cmdSplit[2]), acc);
                System.out.println("<deposit successful>");
            } else {
                System.out.println("<you cancelled deposit>");
            }
        } else {
            System.out.println("wrong Account id");
        }
    }

    private static void listAllAcc() {
        for (Account acc : User.getAllAccountsMap().values()) {
            System.out.println(acc);
        }
    }
}
