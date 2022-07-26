package com.voicestore.voicestore.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {


    @RequestMapping(value="/")
    public String index(){

        return "/view/index";
    }

    @RequestMapping(value = "/login/googleLogin")
    public String login(){
        return "/view/index";
    }
}
