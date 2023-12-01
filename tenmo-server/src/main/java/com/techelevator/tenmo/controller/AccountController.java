package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {
    @Autowired
    private AccountDao accountDao;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/account/create/{userId}", method = RequestMethod.POST)
    public Account createAccount(@PathVariable int userId) {
        return accountDao.createAccount(userId);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/account/delete", method = RequestMethod.DELETE)
    public void deleteAccount(@RequestParam(value = "user_id") int userId,
                              @RequestParam(value = "account_id") int accountId) {
        accountDao.deleteAccount(userId, accountId);
    }

    @RequestMapping(path = "/account/balance/{accountId}", method = RequestMethod.GET)
    public double getBalance(@PathVariable int accountId) {
        return accountDao.getBalanceById(accountId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/account", method = RequestMethod.GET)
    public Account listTransfers(@RequestParam int userId) {
        return null;
    }

    //
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/send", method = RequestMethod.POST)
    public void sendMoney(@RequestParam(value = "sender_account_id") int senderAccountId,
                          @RequestParam(value = "recipient_account_id") int recipientAccountId,
                          @RequestParam(value = "transfer_amount") double amount) {
        accountDao.sendMoney(senderAccountId, recipientAccountId, amount);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/account/5", method = RequestMethod.POST)
    public Account getTransferById(@RequestParam int userId) {
        return null;
    }
}
