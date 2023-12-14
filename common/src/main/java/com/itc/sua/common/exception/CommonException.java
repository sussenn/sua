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
    private static final long serialVersionUID = -6367351351017436312L;

    private String code;
    private Integer status;

    public CommonException(Throwable throwable) {
        super(throwable);
        this.errorCode(ApiErrCode.UNKNOWN);
    }

    public CommonException(String message) {
        this(message, ApiErrCode.UNKNOWN);

    }

    public CommonException(String message, ApiErrCode errorCode) {
        super(message);
        this.errorCode(errorCode);
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
        this.status = errorCode.getCode();
        this.code = errorCode.name();
    }

    /**
     * 异常码
     */
    public CommonException code(String code) {
        this.code = code;
        return this;
    }

    /**
     * 状态码
     */
    public CommonException status(Integer status) {
        this.status = status;
        return this;
    }
}