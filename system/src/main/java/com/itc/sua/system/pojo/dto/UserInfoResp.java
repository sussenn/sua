package com.itc.sua.system.pojo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.itc.sua.system.pojo.entity.SysRoleDO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName UserInfoResp
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/23
 */
@Accessors(chain = true)
@Data()
public class UserInfoResp implements Serializable {
    private static final long serialVersionUID = -3012659457898324142L;

    private Long id;

    private String username;

    private String mobile;

    private String email;

    private Boolean enabled;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SysRoleDO> roleList;
}
