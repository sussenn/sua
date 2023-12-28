package com.itc.sua.system.controller.user;

import com.itc.sua.common.pojo.R;
import com.itc.sua.system.pojo.dto.*;
import com.itc.sua.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;

/**
 * @ClassName SysUserController
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/22
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @PreAuthorize("@sua.check('sys:user:add')")
    @PostMapping("/add")
    @Validated
    public R add(@Valid @RequestBody UserAddReq addReq) {
        userService.add(addReq);
        return R.success();
    }

    @PreAuthorize("@sua.check('sys:user:edit')")
    @PutMapping("/edit")
    @Validated
    public R edit(@Valid @RequestBody UserEditReq editReq) {
        userService.edit(editReq);
        return R.success();
    }

    @PreAuthorize("@sua.check('sys:user:query')")
    @PostMapping("/findPage")
    public R<Map<String, Object>> findPage(@RequestBody UserQueryReq queryReq) {
        return R.success(userService.findPage(queryReq));
    }

    @PreAuthorize("@sua.check('sys:user:del')")
    @DeleteMapping("/del")
    public R del(@RequestBody Long[] ids) {
        userService.delBatch(Arrays.asList(ids));
        return R.success();
    }


    /**
     * 测试接口, 正常情况下不会暴露出来
     */
    //@PreAuthorize("hasRole('ROLE_admin')")
    @PreAuthorize("@sua.check('sys:user:list', '')")
    @GetMapping("/getUserAuthInfo/{id}")
    public R getUserAuthInfo(@PathVariable Long id) {
        return R.success(userService.getUserAuthInfo(id));
    }

}
