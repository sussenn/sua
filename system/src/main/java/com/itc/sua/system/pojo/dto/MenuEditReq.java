package com.itc.sua.system.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @ClassName MenuEditReq
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuEditReq extends MenuAddReq{
    private static final long serialVersionUID = 8346879757646114585L;

    @NotEmpty(message = "id不能为空")
    private Long id;
}
