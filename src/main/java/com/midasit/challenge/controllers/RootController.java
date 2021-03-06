package com.midasit.challenge.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin
@Controller
public class RootController {
    @RequestMapping("^(?!/api).*$")
    public String forward() {
        return "index";
    }
}
