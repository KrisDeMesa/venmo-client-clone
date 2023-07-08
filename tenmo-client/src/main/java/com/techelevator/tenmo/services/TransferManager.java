package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;

public interface TransferManager {
    Transfer[] viewTransfers();

    Transfer getTransfersById(int transferId);

    Transfer createTransfer(Transfer newTransfer);
}
