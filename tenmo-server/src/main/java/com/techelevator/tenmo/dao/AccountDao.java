package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {

    Account getBalance(int id);
    Account getUserId(int id);
    Account getAccountById(int id);

    Account updateBalances(Account accountTo, Account accountFrom);

    Account updateToBalance(Account accountTo, Account accountFrom);

    void updateToBalance(Account accountTo);

   void updateFromBalance(Account accountFrom);

    List<Account> getAccounts();
}
