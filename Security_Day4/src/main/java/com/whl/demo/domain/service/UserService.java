package com.whl.demo.domain.service;

import com.whl.demo.domain.dto.UserDto;
import com.whl.demo.domain.entity.User;
import com.whl.demo.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean memberJoin(UserDto dto) {

        // 비지니스 Validation Check

        // Dto -> Entity
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole("ROLE_USER");

        // Db Saved...
        userRepository.save(user);

        return userRepository.existsById(user.getUsername());
    }
}
