package com.cy.share.common.handler;

import com.cy.share.common.exception.*;
import com.cy.share.common.utils.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 全局异常处理类（@RestControllerAdvice = @ControllerAdvice + @ResponseBody）
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserRegistException.class)
    public Result handleParamValidException(UserRegistException e){
        return Result.fail(e.getCode(), e.getMsg());
    }
    // 处理所有 RuntimeException 及其子类
    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e) {
        // 初始化默认响应（系统异常）
        int code = 500;
        String message = "系统异常，请稍后重试";
        // 判断具体异常类型
        if (e instanceof CodeSendException) {
            CodeSendException ex = (CodeSendException) e;
            code = ex.getCode();
            message = ex.getMsg();
        } else if (e instanceof CodeWrongException) {
            CodeWrongException ex = (CodeWrongException) e;
            code = ex.getCode();
            message = ex.getMsg();
        } else if (e instanceof UserSaveException) {
            UserSaveException ex = (UserSaveException) e;
            code = ex.getCode();
            message = ex.getMsg();
        } else if (e instanceof UserRegistException) {
            UserRegistException ex = (UserRegistException) e;
            code = ex.getCode();
            message = ex.getMsg();
        } else if(e instanceof LogSaveException){
            LogSaveException ex = (LogSaveException) e;
            code = ex.getCode();
            message = ex.getMsg();
        } else if(e instanceof LogGetException){
            LogGetException ex = (LogGetException) e;
            code = ex.getCode();
            message = ex.getMsg();
        }
        return Result.fail(code, message);
    }
}
