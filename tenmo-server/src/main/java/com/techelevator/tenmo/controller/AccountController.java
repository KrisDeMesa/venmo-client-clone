package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.service.TransferService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/account")
public class AccountController {

    private final String API_URL = "http://localhost:8080/";
    private AccountDao accountDao;
    private TransferService transferService;

    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @GetMapping("/{id}/balance")
    public Account getBalance(@PathVariable int id) {
        Account accountBalance = accountDao.getBalance(id);
        return accountBalance;
    }
    // ID is User Id
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable int id) {
        Account account = accountDao.getAccountById(id);
        return account;
    }

    @PutMapping("/{id}/")
    public void updateAccountBalance(@RequestBody Account accountToUpdate, @PathVariable int id) {
       transferService.updateBalances(accountToUpdate, id);
    }

    @GetMapping
    public List<Account> listAccounts() {
        return accountDao.getAccounts();
    }
//    @GetMapping("/{id}/")
//    public Account getUserById(@PathVariable int id) {
//        Account account = accountDao.getUserId(id);
//            return account;
//    }

//    @GetMapping("/{whoami}")
//    public String getCurrentUser(Principal curUser) {
//        return curUser.getName();
//    }

}
