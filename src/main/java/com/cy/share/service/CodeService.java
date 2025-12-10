package com.cy.share.service;

import com.cy.share.pojo.Code;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 21701
* @description 针对表【code】的数据库操作Service
* @createDate 2025-12-02 22:43:34
*/
public interface CodeService extends IService<Code> {

    boolean add(String phone,String code);

    boolean getOne(String phone, String code);
}
