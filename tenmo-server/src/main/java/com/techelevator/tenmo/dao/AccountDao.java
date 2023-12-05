package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {
    int create(int userId);

    Account findByAccountId(int accountId);

    Account findByUserId(int userId);

    Account findByUsername(String username);

    double getBalanceById(int accountId);

    void deposit(Account account, double amount);

    void withdraw(Account account, double amount);
}
