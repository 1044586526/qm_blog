package com.qm.controller.system;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.qm.annotation.BackgroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.service.FeedBackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台反馈管理入口
 *
 * @Author: qin
 * @Date: 2023/6/7 17:20
 */

@RestController
@RequestMapping("/system/feedback")
@Api(tags = "后台反馈管理")
@RequiredArgsConstructor
public class FeedBackController {

    private final FeedBackService feedBackService;

    @GetMapping(value = "/list")
    @SaCheckLogin
    @ApiOperation(value = "反馈列表", httpMethod = "GET", response = ResponseResult.class, notes = "反馈列表")
    public ResponseResult list(Integer type) {
        return feedBackService.listFeedBack(type);
    }

    @DeleteMapping(value = "/deleteBatch")
    @SaCheckPermission("/system/feedback/deleteBatch")
    @ApiOperation(value = "删除反馈", httpMethod = "DELETE", response = ResponseResult.class, notes = "删除反馈")
    @BackgroundOperationLogger(value = "删除反馈")
    public ResponseResult deleteBatch(@RequestBody List<Integer> ids) {
        return feedBackService.deleteBatch(ids);
    }
}

