package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {
    double getBalanceById(int accountId);

    Account createAccount(int userId);

    boolean deleteAccount(int userID, int accountId);

    boolean sendMoney(int senderAccountId, int recipientAccountId, double amount);
}
