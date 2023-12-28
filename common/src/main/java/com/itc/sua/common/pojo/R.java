package com.itc.sua.common.pojo;

import com.itc.sua.common.enums.ApiErrCode;
import com.yomahub.tlog.context.TLogContext;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName R
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/2
 */
@Data
@Accessors(chain = true)
public class R<T> implements Serializable {
    private static final long serialVersionUID = 8208198339718781420L;

    private int code;
    private String msg;
    private T data;
    private String traceId;

    public R() {
        this.traceId = TLogContext.getTraceId();
    }

    public static <T> R<T> error(ApiErrCode code, T data) {
        return new R<T>().setCode(code.getCode()).setMsg(code.getMessage())
                .setData(data);
    }

    public static <T> R<T> error(Integer code, String msg) {
        return new R<T>().setCode(code).setMsg(msg);
    }

    public static <T> R<T> error(String msg) {
        return new R<T>().setCode(ApiErrCode.UNKNOWN.getCode()).setMsg(msg);
    }

    public static <T> R<T> error(ApiErrCode code) {
        return error(code, null);
    }

    public static <T> R<T> success() {
        return error(ApiErrCode.SUCCESS);
    }

    public static <T> R<T> success(T data) {
        R<T> error = error(ApiErrCode.SUCCESS);
        return error.setData(data);
    }

    public boolean isSuccess() {
        return code == ApiErrCode.SUCCESS.getCode();
    }
}
