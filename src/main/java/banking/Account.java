package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

abstract class Account {
    public List<String> transactionHistory;
    protected double balance;
    protected String id;
    protected int age;
    protected boolean allowWithdraw;
    protected double calcApr;
    private double apr;
    private String type;

    public Account(String type, String id, double apr) {
        this.id = id;
        this.apr = apr;
        this.balance = 0;
        this.age = 0;
        this.type = type;
        this.calcApr = (apr / 100) / 12;
        this.allowWithdraw = true;
        this.transactionHistory = new ArrayList<>();
    }

    static String truncate(String amt) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        String amtX = decimalFormat.format(Double.parseDouble(String.valueOf(amt)));
        return amtX;
    }

    public void deposit(double value) {
        this.balance += value;
    }

    public double getBalance() {
        return balance;
    }

    public String getID() {
        return id;
    }

    public double getAPR() {
        return apr;
    }

    public double getCalcApr() {
        return calcApr;
    }

    public List<String> getTransactionHistory() {
        return this.transactionHistory;
    }

    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }

    public String getStateofAcc() {
        String apr = truncate(String.valueOf(getAPR()));
        String balance = truncate(String.valueOf(getBalance()));
        String type_front = getType().substring(0, 1).toUpperCase();
        String type_back = getType().substring(1);
        String type_capital = type_front + type_back;
        return type_capital + " " + getID() + " " + balance + " " + apr;
    }

    public String getType() {
        return type;
    }

    public void incAge(int month) {
        age += month;
        calculateAPR();
    }

    public int getAge() {
        return age;
    }

    protected void calculateAPR() {
        double interestRate = getBalance() * calcApr;
        this.balance += interestRate;
    }

    public abstract boolean validDeposit(double value);


    public abstract boolean validWithdraw(double value);

    public abstract void withdraw(double value);

}
