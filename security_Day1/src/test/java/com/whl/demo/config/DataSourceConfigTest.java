package com.whl.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class DataSourceConfigTest {

    DataSourceConfig dataSource = new DataSourceConfig();

    HikariDataSource hikariDataSource = dataSource.dataSource();

    @Test
    void dataSource() {
        System.out.println(hikariDataSource.getJdbcUrl());
    }
}