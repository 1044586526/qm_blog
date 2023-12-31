package com.qm.controller.system;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.qm.annotation.BackgroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.entity.Menu;
import com.qm.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统菜单管理入口
 *
 * @Author: qin
 * @Date: 2023/6/7 17:21
 */

@RestController
@RequestMapping("/system/menu")
@Api(tags = "系统菜单管理-接口")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping(value = "/getMenuTree")
    @SaCheckLogin
    @ApiOperation(value = "获取菜单树", httpMethod = "GET", response = ResponseResult.class, notes = "获取菜单树")
    public ResponseResult getMenuTree() {
        List<Menu> result = menuService.listMenuTree(menuService.list());
        return ResponseResult.success("获取菜单树成功", result);
    }

    @GetMapping(value = "/getMenuApi")
    @SaCheckLogin
    @ApiOperation(value = "获取所有接口", httpMethod = "GET", response = ResponseResult.class, notes = "获取所有接口")
    public ResponseResult listMenuApi(Integer id) {
         return menuService.listMenuApi(id);
    }

    @PostMapping(value = "/create")
    @SaCheckPermission("/system/menu/create")
    @ApiOperation(value = "添加菜单", httpMethod = "POST", response = ResponseResult.class, notes = "添加菜单")
    @BackgroundOperationLogger(value = "添加菜单")
    public ResponseResult insert(@RequestBody Menu menu) {
        return menuService.insertMenu(menu);
    }

    @PostMapping(value = "/update")
    @SaCheckPermission("/system/menu/update")
    @ApiOperation(value = "修改菜单", httpMethod = "POST", response = ResponseResult.class, notes = "修改菜单")
    @BackgroundOperationLogger(value = "修改菜单")
    public ResponseResult update(@RequestBody Menu menu) {
        return menuService.updateMenu(menu);
    }

    @DeleteMapping(value = "/remove")
    @SaCheckPermission("/system/menu/remove")
    @ApiOperation(value = "删除菜单", httpMethod = "DELETE", response = ResponseResult.class, notes = "删除菜单")
    @BackgroundOperationLogger(value = "删除菜单")
    public ResponseResult deleteMenuById(Integer id) {
        return menuService.deleteMenuById(id);
    }
}
