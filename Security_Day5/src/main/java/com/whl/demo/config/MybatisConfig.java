package com.whl.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.whl.demo.domain.mapper")
public class MybatisConfig {

    @Autowired
    private HikariDataSource dataSource;

    @Autowired
    private ApplicationContext applicationContext; // 빈이 저장된 곳

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        // SqlSessionFactoryBean 인스턴스 생성
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

        // setDataSource 메서드를 통해 데이터베이스와의 연결에 사용할 DataSource를 설정
        sessionFactory.setDataSource(dataSource);

        // setMapperLocations 메서드를 통해 MyBatis 매퍼 파일의 위치를 설정
        // 여기서는 "classpath:mappers/*.xml" 경로에 있는 XML 매퍼 파일들을 사용하도록 설정
        sessionFactory.setMapperLocations(applicationContext.getResources("classpath:mappers/*.xml"));

        // SqlSessionFactoryBean에서 getObject() 메서드를 호출하여 SqlSessionFactory를 생성하고 반환
        return sessionFactory.getObject();
    }
}
