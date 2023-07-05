package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {

    Account getBalance(int id);
    Account getUserId(int id);
    Account getAccountById(int id);
    List<Account> getAccounts();
}
