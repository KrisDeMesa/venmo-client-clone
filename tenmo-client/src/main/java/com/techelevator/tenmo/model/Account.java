package com.techelevator.tenmo.model;

public class Account {

    private int accountId;
    private double balance;
    private int userId;


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
