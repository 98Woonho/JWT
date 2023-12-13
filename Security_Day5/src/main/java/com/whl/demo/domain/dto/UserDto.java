package com.whl.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto  {
    private String username;
    private String password;
    private String role;

    //OAuth2
    private String provider;
    private String providerId;
}
