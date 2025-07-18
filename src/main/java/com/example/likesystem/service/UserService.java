package com.example.likesystem.service;

import com.example.likesystem.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author alanz
*/
public interface UserService extends IService<User> {

    User getLoginUser(HttpServletRequest request);
}
