package com.techelevator.tenmo.service;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferService {

    private final AccountDao accountDao;
    private final UserDao userDao;
    private final TransferDao transferDao;




    public TransferService(AccountDao accountDao, UserDao userDao, TransferDao transferDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.transferDao = transferDao;
    }

    public Transfer createTransfer(Transfer newTransfer) {
        Transfer transfer = transferDao.createTransfer(newTransfer);
        return transfer;
    }
    public List<User> getUserList() {
        return userDao.findAll();
    }

    public Account getAccountById(int accountId) {
        Account account = accountDao.getAccountById(accountId);
        return account;
    }

    public Account getAccountBalance(int accountId) {
        Account accountBalance = accountDao.getBalance(accountId);
        return accountBalance;
    }

    public List<Transfer> getAllTransfers() {
        return transferDao.getAllTransfers();
    }
    public Transfer getUserId(int userId) {
        Transfer transfer = transferDao.getTransferById(userId);
        return transfer;
    }

    public Transfer getTransferById(int transferId) {
        return transferDao.getTransferById(transferId);
    }

}
