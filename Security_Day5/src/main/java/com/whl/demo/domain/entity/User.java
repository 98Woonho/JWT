package com.whl.demo.domain.entity;

import com.whl.demo.domain.dto.UserDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor //  @AllArgsConstructor : 모든 인자를 받는 생성자. 단 파라미터 전달 순서는 선언한 순서로 정해짐.
@NoArgsConstructor //  @NoArgsConstructor : default 생성자
@Data //  @Data : getter, setter, equals, hashcode
@Builder //  @Builder : 생성자의 파라미터 순서가 바껴도 알아서 조정해줌.
@Entity //  @Entity : DB table에 User가 없으면 JPA가 알아서 만들어 줌
@Table(name = "user") //  @Table : table 이름 지정
public class User {
    @Id //  @Id : username을 PK로 지정
    private String username;
    private String password;
    private String role;

    //OAuth2
    private String provider;
    private String providerId;

    // UserEntity를 UserDto로 바꾸는 메서드
    public static UserDto entityToDto(User user) {
        UserDto dto = UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .provider(user.getProvider())
                .providerId(user.getProviderId())
                .build();

        return dto;
    }
}


//  @Column : 해당 열의 name, length 등 설정 가능