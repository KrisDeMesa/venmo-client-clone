package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {




    Transfer getTransferById(int transferId);

    Transfer getTransferTypeId(int transferTypeId);


    Transfer getTransferStatusId(int transferStatusId);

    Transfer getFromTransfer(int accountFromId);

    Transfer getAccountToId(int accountToId);

    Transfer getTransferAmount(double transferAmount);
    List<Transfer> getAllTransfers();
}
