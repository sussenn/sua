package com.itc.sua.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itc.sua.system.pojo.dto.*;
import com.itc.sua.system.pojo.entity.SysUserDO;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SysUserService
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/18
 */
public interface SysUserService extends IService<SysUserDO> {

    void add(UserAddReq addReq);

    void edit(UserEditReq editReq);

    Boolean updatePassword(UserPasswordReq pswReq);

    Map<String, Object> findPage(UserQueryReq queryReq);

    UserInfoResp getUserInfo();

    void delBatch(List<Long> ids);

    /**
     * 获取用户权限信息
     */
    List<String> getUserAuthInfo(Long userId);

    SysUserDO getByUsername(String username);
}
