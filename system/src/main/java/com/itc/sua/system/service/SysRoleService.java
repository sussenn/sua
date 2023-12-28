package com.itc.sua.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itc.sua.system.pojo.dto.RoleAddReq;
import com.itc.sua.system.pojo.dto.RoleEditReq;
import com.itc.sua.system.pojo.dto.RoleQueryReq;
import com.itc.sua.system.pojo.entity.SysRoleDO;

import java.util.List;
import java.util.Map;

/**
 * @ClassName RoleService
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/20
 */
public interface SysRoleService extends IService<SysRoleDO> {

    List<SysRoleDO> getRoleByUserId(Long userId);

    /**
     * 角色授权给用户
     */
    void greantRole(Long userId, List<Long> roleIds);

    Map<String, Object> findPage(RoleQueryReq queryReq);

    void add(RoleAddReq addReq);

    void edit(RoleEditReq editReq);

    void delBatch(List<Long> ids);
}
