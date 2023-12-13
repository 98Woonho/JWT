package com.whl.demo.domain.repository;

import com.whl.demo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> { // <Entity 이름, 해당 Entity의 PK의 자료형>

}
