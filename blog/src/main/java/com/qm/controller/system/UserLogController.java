package com.qm.controller.system;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.qm.annotation.BackgroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.service.UserLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户日志管理入口
 *
 * @Author: qin
 * @Date: 2023/6/7 17:23
 */

@RestController
@RequestMapping("/system/userLog")
@RequiredArgsConstructor
@Api(tags = "用户日志管理")
public class UserLogController {

    private final UserLogService userLogService;

    @GetMapping(value = "/list")
    @SaCheckLogin
    @ApiOperation(value = "用户日志列表", httpMethod = "GET", response = ResponseResult.class, notes = "用户日志列表")
    public ResponseResult list() {
        return userLogService.listUserLog();
    }

    @DeleteMapping(value = "/delete")
    @SaCheckPermission("/system/userLog/delete")
    @BackgroundOperationLogger(value = "删除用户日志")
    @ApiOperation(value = "删除用户日志", httpMethod = "DELETE", response = ResponseResult.class, notes = "删除用户日志")
    public ResponseResult deleteBatch(@RequestBody List<Long> ids) {
        return userLogService.deleteBatch(ids);
    }
}

