package com.qm.controller.system;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.qm.annotation.BackgroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.entity.Role;
import com.qm.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理入口
 *
 * @Author: qin
 * @Date: 2023/6/7 17:22
 */

@RestController
@RequestMapping("/system/role")
@Api(tags = "角色管理-接口")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping(value = "list")
    @SaCheckLogin
    @ApiOperation(value = "角色列表", httpMethod = "GET", response = ResponseResult.class, notes = "角色列表")
    public ResponseResult list(String name) {
        return roleService.listRole(name);
    }

    @GetMapping(value = "queryUserRole")
    @SaCheckLogin
    @ApiOperation(value = "获取当前登录用户所拥有的权限", httpMethod = "GET", response = ResponseResult.class, notes = "获取当前登录用户所拥有的权限")
    public ResponseResult getCurrentUserRole() {
        return roleService.getCurrentUserRole();
    }

    @GetMapping(value = "queryRoleId")
    @SaCheckLogin
    @ApiOperation(value = "获取该角色所有的权限", httpMethod = "GET", response = ResponseResult.class, notes = "获取该角色所有的权限")
    public ResponseResult selectById(Integer roleId) {
        return roleService.selectById(roleId);
    }

    @PostMapping(value = "create")
    @SaCheckPermission("/system/role/create")
    @ApiOperation(value = "添加角色", httpMethod = "POST", response = ResponseResult.class, notes = "添加角色")
    @BackgroundOperationLogger(value = "添加角色")
    public ResponseResult insert(@RequestBody Role role) {
        return roleService.insertRole(role);
    }

    @PostMapping(value = "update")
    @SaCheckPermission("/system/role/update")
    @ApiOperation(value = "修改角色", httpMethod = "POST", response = ResponseResult.class, notes = "修改角色")
    @BackgroundOperationLogger(value = "修改角色")
    public ResponseResult update(@RequestBody Role role) {
        return roleService.updateRole(role);
    }

    @DeleteMapping(value = "remove")
    @SaCheckPermission("/system/role/remove")
    @ApiOperation(value = "删除角色", httpMethod = "DELETE", response = ResponseResult.class, notes = "删除角色")
    @BackgroundOperationLogger(value = "删除角色")
    public ResponseResult deleteBatch(@RequestBody List<Integer> ids) {
        return roleService.deleteBatch(ids);
    }


}
