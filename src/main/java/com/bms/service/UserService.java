package com.bms.service;

import com.bms.dao.UserRepository;
import com.bms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User add(User user) {
        return this.userRepository.save(user);
    }

    public User findById(Long userId) {
        return this.userRepository.findById(userId).orElse(null);
    }
}
