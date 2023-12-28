package com.itc.sua.common.exception;

import com.itc.sua.common.enums.ApiErrCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName CommonException
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/13
 */
@Getter
@Setter
public class CommonException extends RuntimeException {
    private static final long serialVersionUID = -6139060581415880867L;

    private Integer code;
    private String msg;

    public CommonException(Throwable throwable) {
        super(throwable);
        this.errorCode(ApiErrCode.UNKNOWN);
    }

    public CommonException(String message) {
        this.code = ApiErrCode.UNKNOWN.getCode();
        this.msg = message;
    }

    public CommonException(Throwable throwable, String message, ApiErrCode errorCode) {
        super(message, throwable);
        this.errorCode(errorCode);
    }

    public CommonException(Throwable throwable, String message) {
        super(message, throwable);
    }

    public CommonException(ApiErrCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode(errorCode);
    }

    /**
     * 根据异常码创建异常
     */
    public void errorCode(ApiErrCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMessage();
    }

    /**
     * 异常码
     */
    public CommonException msg(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * 状态码
     */
    public CommonException code(Integer code) {
        this.code = code;
        return this;
    }
}