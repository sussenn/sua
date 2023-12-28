package com.itc.sua.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @ClassName SysRoleMenuDO
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "sys_role_menu")
public class SysRoleMenuDO extends Model<SysRoleMenuDO> {
    private static final long serialVersionUID = 5048247410247282750L;

    private Long roleId;

    private Long menuId;
}
