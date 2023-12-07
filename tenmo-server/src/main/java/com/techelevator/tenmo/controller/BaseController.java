package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@PreAuthorize("isAuthenticated()")
public interface BaseController {
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/whoami", method = RequestMethod.GET)
    public String whoami();

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/info", method = RequestMethod.GET)
    public Account currentAccount();

//    @ResponseStatus(HttpStatus.OK)
//    @RequestMapping(path = "/find_all_users", method = RequestMethod.GET)
//    public List<String> findAllUsers();
}
