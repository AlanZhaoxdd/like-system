package com.example.likesystem.exception;

import com.example.likesystem.common.BaseResponse;
import com.example.likesystem.common.ErrorCode;
import com.example.likesystem.common.ResultUtils;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * @author alanz
 */
@RestControllerAdvice
@Slf4j
//在接口文档中隐藏
@Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error(e.getMessage(), e);
        return ResultUtils.error(ErrorCode.OPERATION_ERROR, e.getMessage());
    }
}
