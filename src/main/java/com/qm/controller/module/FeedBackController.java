package com.qm.controller.module;

import com.qm.annotation.ForegroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.entity.FeedBack;
import com.qm.service.FeedBackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 门户反馈入口
 *
 * @Author: qin
 * @Date: 2023/6/7 16:56
 */

@RestController
@RequestMapping("/web/feedback")
@Api(tags = "门户反馈管理")
@RequiredArgsConstructor
public class FeedBackController {

    private final FeedBackService feedBackService;


    @PostMapping(value = "/add")
    @ApiOperation(value = "添加反馈", httpMethod = "POST", response = ResponseResult.class, notes = "添加反馈")
    @ForegroundOperationLogger(value = "首页-用户添加反馈",type = "添加",desc = "添加反馈")
    public ResponseResult addFeedback(@RequestBody FeedBack feedBack) {
        return  feedBackService.insertFeedback(feedBack);
    }

}
