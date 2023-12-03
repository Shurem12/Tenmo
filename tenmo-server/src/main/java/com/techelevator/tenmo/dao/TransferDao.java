package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    Transfer findById(int transferId);
    int request();
//    int approve(int transferId);
//    int reject(int transferId);
//    List<Transfer> listByAccountId(int accountId);
//    List<Transfer> listBySenderAccountId(int senderAccountId);
//    List<Transfer> listByRecipientAccountId(int recipientAccountId);
}
