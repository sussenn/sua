package com.itc.sua.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itc.sua.system.convert.SysRoleConverts;
import com.itc.sua.system.mapper.SysRoleMapper;
import com.itc.sua.system.pojo.dto.RoleAddReq;
import com.itc.sua.system.pojo.dto.RoleEditReq;
import com.itc.sua.system.pojo.dto.RoleQueryReq;
import com.itc.sua.system.pojo.entity.SysRoleDO;
import com.itc.sua.system.pojo.entity.SysUserRoleDO;
import com.itc.sua.system.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SysRoleServiceImpl
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/20
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleDO> implements SysRoleService {

    private final SysRoleConverts roleConverts = SysRoleConverts.INSTANCE;

    @Override
    public List<SysRoleDO> getRoleByUserId(Long userId) {
        //select * from sys_role where id in ( select role_id from sys_user_role where user_id = #{userId} )
        return this.list(Wrappers.<SysRoleDO>lambdaQuery().inSql(SysRoleDO::getId,
                "select role_id from sys_user_role where user_id = " + userId));
    }

    @Override
    public void greantRole(Long userId, List<Long> roleIds) {
        List<SysUserRoleDO> userRoles = new ArrayList<>();
        roleIds.forEach(r -> {
            SysUserRoleDO ur = new SysUserRoleDO();
            ur.setUserId(userId)
                    .setRoleId(r);
            userRoles.add(ur);
        });
        baseMapper.delUserRoleTiesByUserId(userId);
        baseMapper.addUserRoleBatch(userRoles);
    }

    @Override
    public Map<String, Object> findPage(RoleQueryReq queryReq) {
        Page<SysRoleDO> p = new Page<>(queryReq.getPageNum(), queryReq.getPageSize());
        Page<SysRoleDO> pages = this.page(p, Wrappers.<SysRoleDO>lambdaQuery()
                .like(StrUtil.isNotBlank(queryReq.getName()), SysRoleDO::getName, queryReq.getName()));
        Map<String, Object> resMap = new HashMap<>(2);
        resMap.put("sysRoleList", pages.getRecords());
        resMap.put("total", pages.getTotal());
        return resMap;
    }

    @Override
    public void add(RoleAddReq addReq) {
        this.save(roleConverts.addReqToRoleDO(addReq));
    }

    @Override
    public void edit(RoleEditReq editReq) {
        this.updateById(roleConverts.editReqToRoleDO(editReq));
    }

    @Override
    public void delBatch(List<Long> ids) {
        this.removeByIds(ids);
        baseMapper.delUserRoleTiesByRoleIds(ids);
    }
}
