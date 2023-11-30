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

    @RequestMapping(path = "/account/balance/{userId}",method = RequestMethod.GET)
    public Account getBalance(@PathVariable int userId){
        return accountDao.retrieveDetails(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/account", method = RequestMethod.DELETE)
    public Account deleteAccount(@RequestParam int accountId) {
        return null;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/account", method = RequestMethod.GET)
    public Account listTransfers(@RequestParam int userId) {
        return null;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/account/4", method = RequestMethod.POST)
    public Account sendMoney(@RequestParam int userId,  int senderAccountId, int recipientAccountId, double amount) {
        return null;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/account/5", method = RequestMethod.POST)
    public Account retrieveDetails(@RequestParam int userId) {
        return null;
    }
}
