package com.itc.sua.system.controller.user;

import com.itc.sua.common.pojo.R;
import com.itc.sua.system.pojo.dto.UserInfoResp;
import com.itc.sua.system.pojo.dto.UserPasswordReq;
import com.itc.sua.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @ClassName SysApiController system模块不走gateway Auth过滤器,
 *          前端写死,后端直接放行的接口
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/28
 */
@RestController
@RequestMapping("/sys/api")
public class SysApiController {

    @Autowired
    private SysUserService userService;

    @PutMapping("/updatePassword")
    @Validated
    public R updatePassword(@Valid @RequestBody UserPasswordReq pswReq) {
        Boolean suc = userService.updatePassword(pswReq);
        if (suc) {
            return R.success();
        }
        return R.error("修改个人密码失败");
    }

    @GetMapping("/getUsrInfo")
    public R<UserInfoResp> getUserInfo() {
        return R.success(userService.getUserInfo());
    }
}
