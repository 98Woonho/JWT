package com.whl.demo.domain.mapper;

import com.whl.demo.domain.dto.MemoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemoMapperTest {

    @Autowired
    private MemoMapper memoMapper;

    @Test
    public void t1() {
        MemoDto dto = new MemoDto(4, "memo");
        memoMapper.insert(dto);
        // @SelectKey에서 select max(id) + 1 이므로 +1이 된 id 값을 출력 함.
        System.out.println(dto);
    }

    @Test
    public void t2() {
        MemoDto dto = memoMapper.FindByIdXML(3);
        System.out.println(dto);
    }
}