package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.service.TransferService;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping(path="/transfer")

public class TransferController {
    private TransferDao transferDao;
    private TransferService transferService;
    private AccountDao accountDao;
    private UserDao userDao;


    public TransferController(TransferService transferService, AccountDao accountDao, UserDao userDao, TransferDao transferDao) {
        this.transferService = transferService;
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.transferDao = transferDao;
    }
    @GetMapping
    public List<Transfer> listTransfers() {
        return transferService.getAllTransfers();
    }
    @GetMapping("/{id}")
    public Transfer getUserById(@PathVariable int id) {
        Transfer transfer = transferService.getUserId(id);
        return transfer;
    }

    @PostMapping
    public void createTransfer(@RequestBody Transfer newTransfer) {
        transferService.createTransfer(newTransfer);
        transferDao.updateToBalance(newTransfer);
        transferDao.updateFromBalance(newTransfer);
    }


}
