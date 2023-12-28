package com.itc.sua.system.controller.user;

import com.itc.sua.common.pojo.R;
import com.itc.sua.system.pojo.dto.RoleAddReq;
import com.itc.sua.system.pojo.dto.RoleEditReq;
import com.itc.sua.system.pojo.dto.RoleQueryReq;
import com.itc.sua.system.pojo.entity.SysRoleDO;
import com.itc.sua.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;

/**
 * @ClassName SysRoleController
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/22
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;

    @PreAuthorize("@sua.check('sys:role:query')")
    @PostMapping("/findPage")
    public R<Map<String, Object>> findPage(@RequestBody RoleQueryReq queryReq) {
        return R.success(roleService.findPage(queryReq));
    }

    @PreAuthorize("@sua.check('sys:user:role')")
    @PostMapping("/grantRole/{userId}")
    public R grantRole(@PathVariable Long userId, @RequestBody Long[] roleIds) {
        roleService.greantRole(userId, Arrays.asList(roleIds));
        return R.success();
    }

    @PreAuthorize("@sua.check('sys:role:add')")
    @PostMapping("/add")
    @Validated
    public R add(@Valid @RequestBody RoleAddReq addReq) {
        roleService.add(addReq);
        return R.success();
    }


    @PreAuthorize("@sua.check('sys:role:edit')")
    @PostMapping("/edit")
    @Validated
    public R edit(@Valid @RequestBody RoleEditReq editReq) {
        roleService.edit(editReq);
        return R.success();
    }

    @PreAuthorize("@sua.check('sys:role:query','sys:role:list')")
    @GetMapping("/findById/{id}")
    public R<SysRoleDO> findById(@PathVariable Long id) {
        return R.success(roleService.getById(id));
    }

    @PreAuthorize("@sua.check('sys:role:del')")
    @DeleteMapping("/del")
    public R del(@RequestBody Long[] ids) {
        roleService.delBatch(Arrays.asList(ids));
        return R.success();
    }

}
