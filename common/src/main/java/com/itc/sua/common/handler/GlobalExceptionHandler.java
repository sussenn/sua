package com.itc.sua.common.handler;

import com.itc.sua.common.exception.CommonException;
import com.itc.sua.common.pojo.ApiError;
import com.itc.sua.common.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @ClassName GlobalExceptionHandler
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/13
 */
@Slf4j
public abstract class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public R<CommonException> handleCommonException(CommonException ex) {
        // 打印异常响应
        log.info(">>> Exception response, code=[{}], msg=[{}]", ex.getCode(), ex.getMsg());
        return new R<CommonException>().setCode(ex.getCode()).setMsg(ex.getMsg());
    }

    /**
     * 接口参数检验失败的异常
     */
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<ApiError> validFailException(BindException e) {
        // 打印堆栈信息
        log.error(getStackTrace(e));
        return buildResponseEntity(ApiError.error(e.getMessage()));
    }

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> handleException(Throwable e) {
        // 打印堆栈信息
        log.error(getStackTrace(e));
        return buildResponseEntity(ApiError.error(e.getMessage()));
    }

    /**
     * 获取堆栈信息
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }

    /**
     * 统一返回
     */
    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatus()));
    }
}
