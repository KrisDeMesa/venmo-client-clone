package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping(path="/transfer")

public class TransferController {
    private TransferDao transferDao;
    public TransferController(TransferDao transferDao) {
        this.transferDao=transferDao;
    }
    @GetMapping
    public List<Transfer> listTransfers() {
        return transferDao.getAllTransfers();
    }
    @GetMapping("/{id}")
    public Transfer getTransferById(@PathVariable int id) {
        Transfer transfer = transferDao.getTransferById(id);
        return transfer;
    }


}
