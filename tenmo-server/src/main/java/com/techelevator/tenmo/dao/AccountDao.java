package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {
    int create(int userId);

    Account findById(int accountId);

    double getBalanceById(int accountId);

    void deposit(Account account, double amount);
    void withdraw(Account account, double amount);

//    void delete(int userId);
//    Account findByUserId(int userId);

//    boolean delete(int userID, int accountId);

//    boolean sendMoney(int senderAccountId, int recipientAccountId, double amount);
}
