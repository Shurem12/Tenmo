package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {
//    @Autowired
//    private TransferDao transferDao;


}
