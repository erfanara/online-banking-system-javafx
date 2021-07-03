package obsjApp.server;

import obsjApp.core.Account;
import obsjApp.core.User;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

public class ServerCli {
    private final static String HELP_MENU = "COMMANDS >> \n"
            + "deposit <Account Id> <amount>\n"
            + "lsAllAcc\n"
            + "exit\n";

    public static final UserStorage db = new UserStorage("Users");

    // server has a mother account for depositing money to user accounts,
    // the password is just for decoration and some protection.
    private static MotherAccount motherAcc = new MotherAccount("MyServer!@#", null, new BigDecimal("0"));

    private static Scanner in = new Scanner(System.in);

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
                default -> System.out.println(HELP_MENU);
            }
            cmd = in.nextLine();
        }

        System.exit(0);
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
