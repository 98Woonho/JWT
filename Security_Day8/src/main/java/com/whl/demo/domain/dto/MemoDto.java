package com.whl.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data // getter, setter 및 equals 등을 알아서 만들어 줌.
@AllArgsConstructor // 모든 인자를 받는 생성자를 만들어 줌.
public class MemoDto {
    private int id;
    private String text;
}
