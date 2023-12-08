package com.whl.demo.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor // 모든 인자를 받는 생성자. 단 파라미터 전달 순서는 선언한 순서로 정해짐.
@NoArgsConstructor // default 생성자
@Data // getter, setter, equals, hashcode
@Builder // 생성자의 파라미터 순서가 바껴도 알아서 조정해줌.
@Entity // DB table에 User가 없으면 JPA가 알아서 만들어 줌
@Table(name = "user") // table 이름 지정
public class User {
    @Id // username을 PK로 지정
    private String username;
    private String password;
    private String role;
}


// @Column // 해당 열의 name, length 등 설정 가능