package com.hotel.controller;

import com.hotel.service.UserService;
import com.hotel.service.*;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import org.slf4j.Logger;


@Controller
public class MainController {


    @GetMapping(value = "/roomsInfo")
    public String roomPhotos() {
        return "/greeting/roomsInfo";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "first";
    }




//    @GetMapping("/admin")
//    public String admin() {
//        return "/admin/adminPage";
//    }


//For messages-lacal.
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }


//    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
//
//    @ResponseBody
//    @RequestMapping(path = "/")
//    public String home() {
//
//        LOGGER.trace("This is TRACE");
//        LOGGER.debug("This is DEBUG");
//        LOGGER.info("This is INFO");
//        LOGGER.warn("This is WARN");
//        LOGGER.error("This is ERROR");
//
//        return "Hi, show loggings in the console or file!";
//    }





}