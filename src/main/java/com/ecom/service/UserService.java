package com.ecom.service;

import com.ecom.dto.request.LoginForm;
import com.ecom.dto.request.RoleToUser;
import com.ecom.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User getUserByUsername(String username);
    String signUp(User user, String siteUrl);
    String signIn(LoginForm form);
    User addRoleForUser(RoleToUser roleToUser);
    String verifyUser(String code);
    String changeAvatar(MultipartFile file, User user);
}
