package com.whl.demo.domain.mapper;

import com.whl.demo.domain.dto.MemoDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface MemoMapper {
    // @SelectKey : 데이터베이스에서 생성된 키를 자바 객체에 매핑하기 위해 사용
    // statement : 데이터베이스에서 실행할 SQL 쿼리 지정. `tbl_memo` 테이블의 `id` 컬럼에서 현재 최대값을 가져와서 1을 더한 값을 사용
    // keyProperty : 데이터베이스에서 가져온 값을 매핑할 자바 객체의 속성을 지정. 여기서는 `id`라는 이름의 속성을 `MemoDto` 객체와 연결
    // before : false면 @Insert 쿼리가 실행 된 후에 @SelectKey 쿼리가 실행
    // resultType : 결과 자료형
    @SelectKey(statement="select max(id)+1 from tbl_memo",keyProperty = "id",before = false, resultType = int.class)
    @Insert("insert into tbl_memo values(#{id},#{text})")
    public int insert(MemoDto dto);

    @Update("update tbl_memo set text=#{text} where id=#{id}")
    public int update(MemoDto dto);

    @Delete("delete from tbl_memo where id=#{id}")
    public int delete(int id);

    @Select("select * from tbl_memo where id=#{id}")
    public MemoDto selectAt(int id);

    @Select("select * from tbl_memo")
    public List<MemoDto> selectAll();

    @Results(id="MemoResultMap", value= {
            @Result(property = "id",column="id"),
            @Result(property = "text", column="text")
    })
    @Select("select * from tbl_memo")
    public List<Map<String,Object>> selectAllResultMap();

    public MemoDto FindByIdXML(int id);
}
