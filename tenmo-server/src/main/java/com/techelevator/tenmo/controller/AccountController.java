package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserDao userDao;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/account/whoami", method = RequestMethod.GET)
    public String whoAmi() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/account/balance", method = RequestMethod.GET)
    public double getBalance() {
        String username = whoAmi();
        int userId = userDao.findByUsername(username).getId();
        return accountDao.getBalanceById(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/account/deposit", method = RequestMethod.POST)
    public void deposit(@RequestParam double amount) {
        String username = whoAmi();
        int userId = userDao.findByUsername(username).getId();
        Account account = accountDao.findById(userId);

        if (account != null)
            accountDao.deposit(account, amount);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/account/withdraw", method = RequestMethod.POST)
    public void withdraw(@RequestParam double amount) {
        String username = whoAmi();
        int userId = userDao.findByUsername(username).getId();
        Account account = accountDao.findById(userId);

        if (account != null)
            accountDao.withdraw(account, amount);
    }

//    @ResponseStatus(HttpStatus.CREATED)
//    @RequestMapping(path = "/account/create", method = RequestMethod.POST)
//    public int create() {
//        String username = whoAmi();
//        int userId = userDao.findByUsername(username).getId();
//        return accountDao.create(userId);
//    }
//
//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @RequestMapping(path = "/account/delete", method = RequestMethod.DELETE)
//    public void delete() {
//        String username = whoAmi();
//        int userId = userDao.findByUsername(username).getId();
//        accountDao.delete(userId);
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @RequestMapping(path = "/account/hasAccount", method = RequestMethod.GET)
//    public String hasAccount() {
//        String username = whoAmi();
//        int userId = userDao.findByUsername(username).getId();
//        if( userDao.hasAccount(userId))
//            return "true";
//        return "false";
//    }

//    @RequestMapping(path = "/account/balance/{accountId}", method = RequestMethod.GET)
//    public double getBalance(@PathVariable int accountId) {
//        return accountDao.getBalanceById(accountId);
//    }
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @RequestMapping(path = "/account", method = RequestMethod.GET)
//    public Account listTransfers(@RequestParam int userId) {
//        return null;
//    }
//
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @RequestMapping(path = "/send", method = RequestMethod.POST)
//    public void sendMoney(@RequestParam(value = "sender_account_id") int senderAccountId,
//                          @RequestParam(value = "recipient_account_id") int recipientAccountId,
//                          @RequestParam(value = "transfer_amount") double amount) {
//        accountDao.sendMoney(senderAccountId, recipientAccountId, amount);
//    }
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @RequestMapping(path = "/account/5", method = RequestMethod.POST)
//    public Account getTransferById(@RequestParam int userId) {
//        return null;
//    }
}
