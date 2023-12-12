package com.whl.demo.domain.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TxTestServiceTest {
    @Autowired
    private TxTestService txTestService;

    @Test
    public void t1() {
        txTestService.TxTest1();
    }
}