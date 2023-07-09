package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {




    Transfer getTransferById(int transferId);

    boolean updateFromBalance(Transfer transfer);

    boolean updateToBalance(Transfer transfer);

    Transfer createTransfer(Transfer transfer);

    Transfer getTransferTypeId(int transferTypeId);


    Transfer getTransferStatusId(int transferStatusId);

    Transfer getFromTransfer(int accountFromId);

    Transfer getAccountToId(int accountToId);

    Transfer getTransferAmount(double transferAmount);
    List<Transfer> getAllTransfers();
}
