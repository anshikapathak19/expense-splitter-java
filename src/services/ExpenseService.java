package models;

import java.util.List;

public class Expense {
    private User paidBy;
    private double amount;
    private String splitType;  // EQUAL, EXACT, PERCENTAGE
    private List<Split> splits;

    public Expense(User paidBy, double amount, String splitType, List<Split> splits) {
        this.paidBy = paidBy;
        this.amount = amount;
        this.splitType = splitType;
        this.splits = splits;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public double getAmount() {
        return amount;
    }

    public String getSplitType() {
        return splitType;
    }

    public List<Split> getSplits() {
        return splits;
    }
}
