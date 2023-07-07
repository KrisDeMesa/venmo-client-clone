package com.techelevator.tenmo.model;

public class Account {

    private int accountId;
    private double balance;
    private int userId;
    private int sendMoney;


    public Account() {

    }

    public Account(int accountId, double balance, int userId, int sendMoney) {
        this.accountId = accountId;
        this.balance = balance;
        this.userId = userId;
        this.sendMoney = sendMoney;
    }

    public Account(int userId) {
        this.userId = userId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setSendMoney(int sendMoney) {
        this.sendMoney = sendMoney;
    }

    public int getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "AccountService{" +
                "balance=" + balance +
                '}';
    }
}
