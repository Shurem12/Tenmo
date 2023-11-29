package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {
    Account createAccount(int userId);

    Account deleteAccount(int accountId);

    List<Account> listTransfers(int userId);

    Account sendMoney(int userId, double amount);

    Account retrieveDetails(int userId);
}
