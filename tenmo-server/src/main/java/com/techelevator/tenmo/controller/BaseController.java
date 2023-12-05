package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//@RestController
//@PreAuthorize("isAuthenticated()")
public interface BaseController {
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/whoami", method = RequestMethod.GET)
    public String whoami();

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/account", method = RequestMethod.GET)
    public Account currentAccount();
}
