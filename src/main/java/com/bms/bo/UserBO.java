package com.bms.bo;

import com.bms.model.User;
import com.bms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserBO {
    @Autowired
    private UserService userService;

    public User add(User user) {
        return this.userService.add(user);
    }

    public User findById(Long userId) {
        return this.userService.findById(userId);
    }
}
