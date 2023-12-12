package com.whl.demo.domain.repository;

import com.whl.demo.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemoRepositoryTest {
    @Autowired
    private UserRepository memoRepository;

    @Test
    public void t1() {
        memoRepository.save(new User("user1", "1234", "ROLE_USER")); // User 테이블에 생성됨.
    }
}