package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {
    Account createAccount(int userId);

    boolean deleteAccount(int userID, int  accountId);

    List<Account> listTransfers(int userId);

    Account sendMoney( int senderAccountId, int recipientAccountId, double amount);

    Account getBalance(int userId);

    Account grabDetails(int userId);
}
