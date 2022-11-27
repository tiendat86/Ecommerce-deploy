package com.ecom.controller;

import com.ecom.entity.User;
import com.ecom.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
//@Scope(value = "prototype")
public class BaseController {
    @Autowired
    private UserService userService;

    public final User getUser() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                String userCd = ((UserDetails) auth.getPrincipal()).getUsername();
                return userService.getUserByUsername(userCd);
            }
            return null;
        } catch (Exception e) {
            log.error("Error get info User");
            return null;
        }
    }
}
