package models;

public class User {
    private String name;
    private double balance;

    public User(String name) {
        this.name = name;
        this.balance = 0.0;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }

    @Override
    public String toString() {
        return name + " (Balance: " + balance + ")";
    }
}
