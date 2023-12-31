package com.qm.controller.system;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.qm.annotation.BackgroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.service.AdminLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 操作日志管理入口
 *
 * @Author: qin
 * @Date: 2023/6/7 17:18
 */

@RestController
@RequestMapping("/system/adminLog")
@RequiredArgsConstructor
@Api(tags = "操作日志管理")
public class AdminLogController {

    private final AdminLogService adminLogService;

    @GetMapping(value = "/list")
    @SaCheckLogin
    @ApiOperation(value = "操作日志列表", httpMethod = "GET", response = ResponseResult.class, notes = "操作日志列表")
    public ResponseResult list() {
        return adminLogService.listAdminLog();
    }

    @DeleteMapping(value = "/delete")
    @BackgroundOperationLogger(value = "删除操作日志")
    @SaCheckPermission("/system/adminLog/delete")
    @ApiOperation(value = "删除操作日志", httpMethod = "DELETE", response = ResponseResult.class, notes = "删除操作日志")
    public ResponseResult delete(@RequestBody List<Long> ids) {
        return adminLogService.deleteAdminLog(ids);
    }
}

