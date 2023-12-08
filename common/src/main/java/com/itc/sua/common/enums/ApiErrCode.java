package com.itc.sua.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author sussenn
 * @date 2023/12/2
 * @since 1.0.0
 */
@ToString
@AllArgsConstructor
@Getter
public enum ApiErrCode {

    /**
     * 失败
     */
    UNKNOWN(-1, "未知错误"),

    SUCCESS(0, "成功");

    private final Integer code;

    private final String message;
}
