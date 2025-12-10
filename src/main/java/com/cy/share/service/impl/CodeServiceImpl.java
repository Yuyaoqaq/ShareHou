package com.cy.share.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cy.share.pojo.Code;
import com.cy.share.service.CodeService;
import com.cy.share.mapper.CodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 21701
* @description 针对表【code】的数据库操作Service实现
* @createDate 2025-12-02 22:43:34
*/
@Service
public class CodeServiceImpl extends ServiceImpl<CodeMapper, Code>
    implements CodeService{
    @Autowired
    CodeMapper codeMapper;
    @Override
    public boolean add(String phone ,String code) {
        return codeMapper.add(phone,code);
    }

    @Override
    public boolean getOne(String phone, String code) {
        return codeMapper.getOne(phone,code);
    }
}




