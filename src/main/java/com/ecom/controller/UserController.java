package com.ecom.controller;

import com.ecom.dto.request.LoginForm;
import com.ecom.dto.request.RoleToUser;
import com.ecom.entity.User;
import com.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class UserController extends BaseController {
    private final UserService userService;

    @PostMapping("/signup")
    public String signUp(@RequestBody User user, HttpServletRequest request) {
        return userService.signUp(user, getStringUrl(request));
    }

    private String getStringUrl(HttpServletRequest request) {
        String siteUrl = request.getRequestURL().toString();
        return siteUrl.replace(request.getServletPath(), "");
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam("code") String code) {
        return userService.verifyUser(code);
    }

    @PostMapping("/signin")
    public String signIn(@RequestBody LoginForm form) {
        return userService.signIn(form);
    }

    @GetMapping("/logout_system")
    public void logOut() {
        SecurityContextHolder.clearContext();
    }

    @PostMapping("/user/change_avatar")
    public String setAvatar(@RequestPart MultipartFile file) {
        return userService.changeAvatar(file, getUser());
    }

    @PostMapping("/role/add")
    public User addRoleToUser(@RequestBody RoleToUser roleToUser) {
        return userService.addRoleForUser(roleToUser);
    }

}
