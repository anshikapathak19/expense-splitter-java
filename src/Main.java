import models.*;
import services.ExpenseService;
import utils.InputHelper;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Group group = new Group();
        ExpenseService expenseService = new ExpenseService();

        System.out.println("===== EXPENSE SPLITTER (SPLITWISE CLONE) =====");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add User");
            System.out.println("2. Add Expense");
            System.out.println("3. Show Balances");
            System.out.println("4. Show Settlement");
            System.out.println("5. Exit");

            int choice = InputHelper.getInt("Choose an option: ");

            switch (choice) {
                case 1:
                    addUser(group);
                    break;

                case 2:
                    addExpense(group, expenseService);
                    break;

                case 3:
                    showBalances(group);
                    break;

                case 4:
                    showSettlement(group);
                    break;

                case 5:
                    System.out.println("\nThank you for using Expense Splitter!");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ---------------- ADD USER ----------------
    private static void addUser(Group group) {
        String name = InputHelper.getString("Enter user name: ");
        group.addUser(new User(name));
        System.out.println("User added!");
    }

    // ---------------- ADD EXPENSE ----------------
    private static void addExpense(Group group, ExpenseService expenseService) {

        if (group.getUsers().isEmpty()) {
            System.out.println("Add users first!");
            return;
        }

        System.out.println("\nWho paid?");
        List<User> users = group.getUsers();
        for (int i = 0; i < users.size(); i++) {
            System.out.println((i + 1) + ". " + users.get(i).getName());
        }

        int paidByIndex = InputHelper.getInt("Choose payer number: ") - 1;
        User paidBy = users.get(paidByIndex);

        double amount = InputHelper.getDouble("Enter amount: ");

        System.out.println("\nEnter Split Type: ");
        System.out.println("1. Equal");
        System.out.println("2. Exact");
        System.out.println("3. Percentage");

        int type = InputHelper.getInt("Choose type: ");

        Expense expense = null;

        switch (type) {

            // ------------------------------------------------ EQUAL SPLIT
            case 1:
                expense = expenseService.createEqualExpense(paidBy, amount, users);
                group.addExpense(expense);
                System.out.println("Equal expense added!");
                break;

            // ------------------------------------------------ EXACT SPLIT
            case 2:
                List<Double> exact = new ArrayList<>();
                System.out.println("Enter exact amount for each user:");
                for (User u : users) {
                    exact.add(InputHelper.getDouble(u.getName() + ": "));
                }
                expense = expenseService.createExactExpense(paidBy, amount, exact, users);
                group.addExpense(expense);
                System.out.println("Exact expense added!");
                break;

            // ------------------------------------------------ PERCENT SPLIT
            case 3:
                List<Double> percent = new ArrayList<>();
                System.out.println("Enter percentage for each user:");
                for (User u : users) {
                    percent.add(InputHelper.getDouble(u.getName() + "% : "));
                }
                expense = expenseService.createPercentageExpense(paidBy, amount, percent, users);
                group.addExpense(expense);
                System.out.println("Percentage expense added!");
                break;

            default:
                System.out.println("Invalid split type!");
        }
    }

    // ---------------- SHOW BALANCES ----------------
    private static void showBalances(Group group) {
        System.out.println("\n--- BALANCES ---");
        Map<String, Double> balances = group.getBalances();

        for (Map.Entry<String, Double> entry : balances.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    // ---------------- SETTLEMENT ----------------
    private static void showSettlement(Group group) {

        Map<String, Double> balances = group.getBalances();

        List<Map.Entry<String, Double>> positive = new ArrayList<>();
        List<Map.Entry<String, Double>> negative = new ArrayList<>();

        for (Map.Entry<String, Double> entry : balances.entrySet()) {
            if (entry.getValue() > 0)
                positive.add(entry);
            else if (entry.getValue() < 0)
                negative.add(entry);
        }

        int i = 0, j = 0;

        System.out.println("\n--- SETTLEMENT ---");

        while (i < positive.size() && j < negative.size()) {

            String creditor = positive.get(i).getKey();
            String debtor = negative.get(j).getKey();

            double credit = positive.get(i).getValue();
            double debit = -negative.get(j).getValue();

            double settle = Math.min(credit, debit);

            System.out.println(debtor + " → " + creditor + ": " + settle);

            positive.get(i).setValue(credit - settle);
            negative.get(j).setValue(-(debit - settle));

            if (positive.get(i).getValue() == 0) i++;
            if (negative.get(j).getValue() == 0) j++;
        }
    }
}
