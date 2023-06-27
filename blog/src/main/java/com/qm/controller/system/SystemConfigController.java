package com.qm.controller.system;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.qm.annotation.BackgroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.entity.SystemConfig;
import com.qm.service.SystemConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 系统配置管理入口
 *
 * @Author: qin
 * @Date: 2023/6/7 17:23
 */

@RestController
@RequestMapping("/system/config")
@Api(tags = "系统配置管理")
@RequiredArgsConstructor
public class SystemConfigController {

    private final SystemConfigService systemConfigService;

    @GetMapping(value = "/getConfig")
    @SaCheckLogin
    @ApiOperation(value = "查询系统配置", httpMethod = "GET", response = ResponseResult.class, notes = "查询系统配置")
    public ResponseResult getConfig(){
        return systemConfigService.getConfig();
    }

    @PostMapping(value = "/update")
    @SaCheckPermission("/system/config/update")
    @ApiOperation(value = "修改系统配置", httpMethod = "POST", response = ResponseResult.class, notes = "修改系统配置")
    @BackgroundOperationLogger(value = "修改系统配置")
    public ResponseResult update(@RequestBody SystemConfig systemConfig){
        return systemConfigService.updateConfig(systemConfig);
    }
}

