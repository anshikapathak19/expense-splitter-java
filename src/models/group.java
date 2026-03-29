package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Group {
    private List<User> users;
    private List<Expense> expenses;

    public Group() {
        this.users = new ArrayList<>();
        this.expenses = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);

        // update balances for each split
        for (Split split : expense.getSplits()) {
            split.getUser().updateBalance(-split.getAmount());
        }

        // payer gets positive amount (people owe them)
        expense.getPaidBy().updateBalance(expense.getAmount());
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    // returns final balances for all users
    public Map<String, Double> getBalances() {
        Map<String, Double> balances = new HashMap<>();

        for (User user : users) {
            balances.put(user.getName(), user.getBalance());
        }

        return balances;
    }
}
