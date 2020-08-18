package com.commerceiq.bms.bo;

import com.commerceiq.bms.model.User;
import com.commerceiq.bms.service.UserService;
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
