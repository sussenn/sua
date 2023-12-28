package com.itc.sua.system.service;

import com.itc.sua.common.pojo.auth.AuthLoginUser;
import com.itc.sua.system.pojo.dto.LoginParamDTO;
import com.itc.sua.system.pojo.dto.SmsCodeDTO;

import java.util.Map;

/**
 * @ClassName LoginService
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/15
 */
public interface LoginService {

    Map<String, String> getImgCode();

    Boolean getSmsCode(SmsCodeDTO smsCode);

    AuthLoginUser login(LoginParamDTO loginParam);

    void logout();
}
