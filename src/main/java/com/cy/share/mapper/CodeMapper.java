package com.cy.share.mapper;

import com.cy.share.pojo.Code;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 21701
* @description 针对表【code】的数据库操作Mapper
* @createDate 2025-12-02 22:43:34
* @Entity com.cy.share.pojo.Code
*/

public interface CodeMapper extends BaseMapper<Code> {

    boolean add(@Param("phone") String phone,@Param("code")String code);

    boolean getOne(@Param("phone") String phone,@Param("code") String code);
}




