package com.itc.sua.system.controller.user;

import com.itc.sua.common.pojo.R;
import com.itc.sua.common.pojo.auth.Menu;
import com.itc.sua.system.pojo.dto.MenuAddReq;
import com.itc.sua.system.pojo.dto.MenuEditReq;
import com.itc.sua.system.pojo.entity.SysMenuDO;
import com.itc.sua.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName SysMenuController
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/22
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService menuService;

    @PreAuthorize("@sua.check('sys:menu:query')")
    @GetMapping("/findAll")
    public R<List<Menu>> findAll() {
        return R.success(menuService.findAll());
    }

    @PreAuthorize("@sua.check('sys:role:menu')")
    @PostMapping("/getRoleMenu/{roleId}")
    public R<List<Long>> getRoleMenu (@PathVariable Long roleId) {
        return R.success(menuService.getRoleMenu(roleId));
    }

    @PreAuthorize("@sua.check('sys:role:menu')")
    @PostMapping("/grantMenu/{roleId}")
    public R grantMenu(@PathVariable Long roleId, @RequestBody Long[] ids) {
        menuService.grantMenu(roleId, Arrays.asList(ids));
        return R.success();
    }

    @PreAuthorize("@sua.check('sys:menu:add')")
    @PostMapping("/add")
    @Validated
    public R add(@Valid @RequestBody MenuAddReq addReq) {
        menuService.add(addReq);
        return R.success();
    }

    @PreAuthorize("@sua.check('sys:menu:edit')")
    @PutMapping("/edit")
    @Validated
    public R edit(@Valid @RequestBody MenuEditReq editReq) {
        menuService.edit(editReq);
        return R.success();
    }

    @PreAuthorize("@sua.check('sys:menu:query','sys:menu:list')")
    @GetMapping("/findById/{id}")
    public R<SysMenuDO> findById(@PathVariable Long id) {
        return R.success(menuService.getById(id));
    }

    @PreAuthorize("@sua.check('sys:menu:del')")
    @DeleteMapping("/del/{id}")
    public R del(@PathVariable Long id) {
        menuService.del(id);
        return R.success();
    }

}
