package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
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


    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }
    @GetMapping
    public List<Transfer> listTransfers() {
        return transferService.getAllTransfers();
    }
    @GetMapping("/{id}")
    public Transfer getTransferById(@PathVariable int id) {
        Transfer transfer = transferDao.getTransferById(id);
        return transfer;
    }

    @PostMapping
    public Transfer createTransfer(@RequestBody Transfer newTransfer) {
        return transferService.createTransfer(newTransfer);
    }


}
