package com.example.wordmanagefilesystem.GlobalExceptionHander;

import com.example.wordmanagefilesystem.Pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHander {
    //全局异常处理器（RuntimeException）
    @ExceptionHandler(RuntimeException.class)
    public Result GlobalExceptionHanderRuntimeException(RuntimeException e){
        log.error("全局异常处理器（RuntimeException），报出异常{}", e.getMessage());
        return Result.exception("运行时出现异常："+e.getMessage());
    }

    //全局异常处理器（Exception）
    @ExceptionHandler(Exception.class)
    public Result GlobalExceptionHanderException(Exception e){
        log.error("全局异常处理器（Exception），报出异常{}", e.getMessage());
        return Result.exception("系统出现异常："+e.getMessage());
    }
}
