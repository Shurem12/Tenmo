package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/account")
@PreAuthorize("isAuthenticated()")
public class AccountController implements BaseController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private AccountDao accountDao;

    @Override
    public String whoami() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    //    @ResponseStatus(HttpStatus.CREATED)
//    @RequestMapping(path = "/find_all_users", method = RequestMethod.GET)
//    public List<String> findAllUsers() {
//        List<String> listOfUsers = userDao.findAll();
//        listOfUsers.remove(whoami());
//        return listOfUsers;
//    }

    @Override
    public Account currentAccount() {
        String username = whoami();
        int userId = userDao.findByUsername(username).getId();
        return accountDao.findByUserId(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public double getBalance() {
        return currentAccount().getBalance();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/deposit", method = RequestMethod.PUT)
    public Account deposit(@RequestParam double amount) {
        accountDao.deposit(currentAccount(), amount);
        return currentAccount();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/withdraw", method = RequestMethod.PUT)
    public Account withdraw(@RequestParam double amount) {
        accountDao.withdraw(currentAccount(), amount);
        return currentAccount();
    }
}
