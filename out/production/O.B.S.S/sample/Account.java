package sample;

import java.util.ArrayList;

public class Account {
    private String alias;
    private int id;
    private long Balance;
    private final ArrayList<Transaction> transactions;


    public Account(int id){
        this.id = id;
        transactions = new ArrayList<>();
    }

    public Account(String alias, int id) {
        this.alias = alias;
        this.id = id;
        transactions = new ArrayList<>();
    }

    public Account(String alias, int id, long balance) {
        this.alias = alias;
        this.id = id;
        Balance = balance;
        transactions = new ArrayList<>();
    }

    public void withdraw(long amount) {
        if (getBalance() - amount < 0) {
            transactions.add(new Transaction('W', amount, getBalance(), false));
            return;
        }
        setBalance(getBalance() - amount);
        transactions.add(new Transaction('W', amount, getBalance(), true));
    }

    public void Deposit(long amount){
        setBalance(getBalance() + amount);
        transactions.add(new Transaction('D', amount, getBalance(), true));
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getId() {
        return id;
    }

    public long getBalance() {
        return Balance;
    }

    public void setBalance(long balance) {
        Balance = balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}
