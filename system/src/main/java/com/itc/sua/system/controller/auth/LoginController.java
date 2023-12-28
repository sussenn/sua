package com.itc.sua.system.controller.auth;

import com.itc.sua.common.pojo.R;
import com.itc.sua.common.pojo.auth.AuthLoginUser;
import com.itc.sua.system.pojo.dto.LoginParamDTO;
import com.itc.sua.system.pojo.dto.SmsCodeDTO;
import com.itc.sua.system.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @ClassName LoginController 放行的接口
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/15
 */
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/getImgCode")
    public R<Map<String, String>> getImgCode() {
        return R.success(loginService.getImgCode());
    }

    @PostMapping("/getSmsCode")
    @Validated
    public R<Object> getSmsCode(@Valid @RequestBody SmsCodeDTO smsCode) {
        Boolean suc = loginService.getSmsCode(smsCode);
        if (suc) {
            return R.success();
        }
        return R.error("短信验证码发送失败");
    }

    @PostMapping("/login")
    public R<AuthLoginUser> login(@RequestBody LoginParamDTO loginParam) {
        return R.success(loginService.login(loginParam));
    }

    @PostMapping("/logout")
    public R logout() {
        loginService.logout();
        return R.success();
    }

}
