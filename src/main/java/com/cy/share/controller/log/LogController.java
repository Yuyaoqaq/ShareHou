package com.cy.share.controller.log;


import com.cy.share.common.constant.ResultCode;
import com.cy.share.common.exception.LogGetException;
import com.cy.share.common.exception.LogSaveException;
import com.cy.share.common.exception.LogUpdateException;
import com.cy.share.common.utils.Result;
import com.cy.share.dto.QueryDto;
import com.cy.share.dto.ReleaseDto;
import com.cy.share.mapper.LogMapper;
import com.cy.share.service.LogService;
import com.cy.share.vo.LogDetailVo;
import com.cy.share.vo.LogEditVo;
import com.cy.share.vo.LogListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cy.share.pojo.Log;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    LogService logService;
    @Autowired
    private LogMapper logMapper;

    @GetMapping("/list")
    public Result logList(QueryDto query){
        try {
            List<LogListVo> resultList = logService.queryLogList(query);
            //直接返回成功结果（即使列表为空，也应返回空列表而非null）
            return Result.success(resultList);
        } catch (Exception e) {
            log.error("查询博客列表失败", e); // 记录错误日志
            return Result.fail(ResultCode.DATA_HANDLE_ERROR);
        }
    }
    @PostMapping("/release")
    public Result release (@RequestBody Log log){
            boolean save = logService.add(log);
            if(!save) throw new LogSaveException();
            return Result.success();
    }
    @GetMapping("/detail/{id}")
    public Result logDetail(@PathVariable Integer id){
        LogDetailVo log = logService.findById(id);
        if(log==null) throw new LogGetException();
        System.out.println(log.getCreateTime());
        return Result.success(log);
    }
    @GetMapping("/edit/{id}")
    public Result logEdit(@PathVariable Integer id){
        LogEditVo byId = logService.editById(id);
        if(byId==null) throw new LogGetException();
        return Result.success(byId);
    }
    @PutMapping("/update/{id}")
    public Result update( // 从URL路径中获取博客ID（必须）
                          @PathVariable Integer id,
                          // 从请求体中获取更新的博客数据（必须，绑定JSON到实体）
                          @RequestBody ReleaseDto log){
        log.setId(id);
        boolean ok = logService.updatebyId(log);
        if(!ok) throw new LogUpdateException();
        return Result.success();
    }
}
