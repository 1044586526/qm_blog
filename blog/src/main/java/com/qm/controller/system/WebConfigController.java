package com.qm.controller.system;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.qm.annotation.BackgroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.entity.WebConfig;
import com.qm.service.WebConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 网站配置管理入口
 *
 * @Author: qin
 * @Date: 2023/6/7 17:23
 */

@RestController
@RequestMapping("/system/webConfig")
@Api(tags = "网站配置管理")
@RequiredArgsConstructor
public class WebConfigController {

    private final WebConfigService webConfigService;

    @GetMapping(value = "/list")
    @SaCheckLogin
    @ApiOperation(value = "网站配置列表", httpMethod = "GET", response = ResponseResult.class, notes = "网站配置列表")
    public ResponseResult list() {
        return webConfigService.listWebConfig();
    }

    @PostMapping(value = "/update")
    @SaCheckPermission("/system/webConfig/update")
    @ApiOperation(value = "修改网站配置", httpMethod = "POST", response = ResponseResult.class, notes = "网站配置列表")
    @BackgroundOperationLogger(value = "修改网站配置")
    public ResponseResult update(@RequestBody WebConfig webConfig) {
        return webConfigService.updateWebConfig(webConfig);
    }
}

