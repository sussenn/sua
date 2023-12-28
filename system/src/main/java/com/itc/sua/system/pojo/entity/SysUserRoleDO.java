package com.itc.sua.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @ClassName SysUserRoleDO
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "sys_user_role")
public class SysUserRoleDO extends Model<SysUserRoleDO> {
    private static final long serialVersionUID = 7258750343775552976L;

    private Long userId;

    private Long roleId;
}
