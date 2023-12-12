package com.whl.demo.domain.service;

import com.whl.demo.domain.entity.User;
import com.whl.demo.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TxTestService {
    @Autowired
    private UserRepository userRepository;

    public void TxTest1() {
        log.info("[TxTestService] TxTest()");
        userRepository.save(new User("user1", "1234", "ROLE_USER"));
        userRepository.save(new User("user1", "5678", "ROLE_USER"));
    }
}
