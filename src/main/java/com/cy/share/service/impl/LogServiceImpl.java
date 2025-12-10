package com.cy.share.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cy.share.dto.QueryDto;
import com.cy.share.dto.ReleaseDto;
import com.cy.share.pojo.Log;
import com.cy.share.service.LogService;
import com.cy.share.mapper.LogMapper;
import com.cy.share.vo.LogDetailVo;
import com.cy.share.vo.LogEditVo;
import com.cy.share.vo.LogListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 21701
* @description 针对表【log】的数据库操作Service实现
* @createDate 2025-12-02 18:18:33
*/
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log>
    implements LogService{
    @Autowired
    LogMapper logMapper;
    @Override
    public List<LogListVo> queryLogList(QueryDto query) {
        Integer pageNum = query.getPage()-1;
        Integer pageSize = query.getPageSize();
        String key = query.getAuthor();
        List<LogListVo>list = logMapper.queryLogList(pageNum,pageSize,key);
        return list;
    }

    @Override
    public boolean add(Log log) {
        return logMapper.add(log);
    }

    @Override
    public LogDetailVo findById(Integer id) {
        return logMapper.findById(id);
    }

    @Override
    public LogEditVo editById(Integer id) {
        return logMapper.editById(id);
    }

    @Override
    public boolean updatebyId(ReleaseDto log) {
        return logMapper.updatebyId(log);
    }
}




