package com.whl.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataSourceConfigTest {

    @Autowired
    private HikariDataSource dataSource;

    @Test
    void dataSource() {
        System.out.println(dataSource.getJdbcUrl());
    }
}