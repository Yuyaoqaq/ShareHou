package com.cy.share.service;

import com.cy.share.dto.QueryDto;
import com.cy.share.dto.ReleaseDto;
import com.cy.share.pojo.Log;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cy.share.vo.LogDetailVo;
import com.cy.share.vo.LogEditVo;
import com.cy.share.vo.LogListVo;

import java.util.List;

/**
* @author 21701
* @description 针对表【log】的数据库操作Service
* @createDate 2025-12-02 18:18:33
*/
public interface LogService extends IService<Log> {

    List<LogListVo> queryLogList(QueryDto query);

    boolean add(Log log);

    LogDetailVo findById(Integer id);

    LogEditVo editById(Integer id);

    boolean updatebyId(ReleaseDto log);
}
