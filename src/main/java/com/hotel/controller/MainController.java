package com.hotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    @GetMapping("/")
    public String home() {
        return "/home/first";
    }

    @GetMapping(value = "/home/roomsInfo")
    public String roomPhotos() {
        return "/home/roomsInfo";
    }

    @GetMapping("/login")
    public String login() {
        return "/account/login";
    }

}