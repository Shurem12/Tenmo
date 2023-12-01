package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    Transfer getTransferById(int transferId);
    Transfer addTransfer();
    Transfer removeTransfer(int transferId);
    List<Transfer> listTransfersByUserId(int userId);
    List<Transfer> listTransfersBySenderUserId(int senderUserId);
    List<Transfer> listTransfersBySenderAccountId(int senderAccountId);
    List<Transfer> listTransfersByAccountId(int accountId);
    List<Transfer> listTransfersByRecipientUserId(int recipientUserId);
    List<Transfer> listTransfersByRecipientAccountId(int recipientAccountId);
}
