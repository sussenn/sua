package com.itc.sua.system.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @ClassName SmsCodeDTO
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/18
 */
@Data
public class SmsCodeDTO implements Serializable {
    private static final long serialVersionUID = -946251080436665667L;

    @NotBlank
    private String mobile;

    @NotBlank
    private String imgCode;

    @NotBlank
    private String imgId = "";
}
