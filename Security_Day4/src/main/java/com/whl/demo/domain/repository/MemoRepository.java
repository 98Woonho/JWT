package com.whl.demo.domain.repository;


import com.whl.demo.domain.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoRepository extends JpaRepository<Memo,Integer> {
}
