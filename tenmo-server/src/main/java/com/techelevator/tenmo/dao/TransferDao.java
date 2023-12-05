package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;

import java.util.List;

public interface TransferDao {
    Transfer findByTransferId(int transferId);

    Transfer init(Account sender, Account receiver, double amount);

    Transfer send(Account sender, Account receiver, double amount);

    Transfer update(String sql, Transfer transfer);

    Transfer approve(Transfer transfer);

    Transfer reject(Transfer transfer);

    Transfer cancel(Transfer transfer);

//    List<Transfer> listByAccountId(int accountId);
//    List<Transfer> listBySenderAccountId(int senderAccountId);
//    List<Transfer> listByRecipientAccountId(int recipientAccountId);
}
