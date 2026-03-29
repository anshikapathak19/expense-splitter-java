package services;

import models.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseService {

    public Expense createEqualExpense(User paidBy, double amount, List<User> users) {
        double splitAmount = amount / users.size();
        List<Split> splits = new ArrayList<>();

        for (User user : users) {
            splits.add(new Split(user, splitAmount));
        }

        return new Expense(paidBy, amount, "EQUAL", splits);
    }

    public Expense createExactExpense(User paidBy, double amount, List<Double> exactAmounts, List<User> users) {

        double total = 0;
        for (double val : exactAmounts) total += val;

        if (total != amount) {
            throw new IllegalArgumentException("Exact split amounts do not match total amount!");
        }

        List<Split> splits = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            splits.add(new Split(users.get(i), exactAmounts.get(i)));
        }

        return new Expense(paidBy, amount, "EXACT", splits);
    }

    public Expense createPercentageExpense(User paidBy, double amount, List<Double> percentages, List<User> users) {

        double total = 0;
        for (double p : percentages) total += p;

        if (total != 100) {
            throw new IllegalArgumentException("Percentages must add up to 100!");
        }

        List<Split> splits = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            double splitAmount = (amount * percentages.get(i)) / 100.0;
            splits.add(new Split(users.get(i), splitAmount));
        }

        return new Expense(paidBy, amount, "PERCENTAGE", splits);
    }
}
