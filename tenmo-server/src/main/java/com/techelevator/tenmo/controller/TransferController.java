package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/transfer")
public class TransferController implements BaseController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private TransferDao transferDao;

    @Override
    public String whoami() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public Account currentAccount() {
        String username = whoami();
        int userId = userDao.findByUsername(username).getId();
        return accountDao.findByUserId(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/find", method = RequestMethod.GET)
    public Transfer findByTransferId(@RequestParam(value = "transfer_id") int transferId) {
        return transferDao.findByTransferId(transferId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/init", method = RequestMethod.POST)
    public Transfer init(
            @RequestParam(value = "username") String username,
            @RequestParam double amount) {
        Account sender = currentAccount();
        Account receiver = accountDao.findByUsername(username);
        return transferDao.init(sender, receiver, amount);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/approve", method = RequestMethod.PUT)
    public Transfer approve(@RequestBody Transfer transfer) {
        return transferDao.approve(transfer);
//        return transfer;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/reject", method = RequestMethod.PUT)
    public Transfer reject(@RequestBody Transfer transfer) {
        boolean isNotReceiver = currentAccount().getUserId() != transfer.getReceiverAccountId();
        if (isNotReceiver)
            return transfer;
        return transferDao.reject(transfer);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/cancel", method = RequestMethod.PUT)
    public Transfer cancel(@RequestBody Transfer transfer) {
        boolean isNotSender = currentAccount().getUserId() != transfer.getSenderAccountId();
        if (isNotSender)
            return transfer;
        return transferDao.reject(transfer);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/send", method = RequestMethod.POST)
    public Transfer send(
            @RequestParam(value = "username") String username,
            @RequestParam double amount) {
        Account sender = currentAccount();
        Account receiver = accountDao.findByUsername(username);
        return transferDao.send(sender, receiver, amount);
    }
}
