package com.qm.controller.module;


import com.qm.annotation.ForegroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.entity.FriendLink;
import com.qm.service.FriendLinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 友链入口
 *
 * @Author: qin
 * @Date: 2023/6/7 16:56
 */

@RestController
@RequestMapping("/web/friend")
@Api(tags = "友情链接-接口")
@RequiredArgsConstructor
public class FriendLinkController {

    private final FriendLinkService friendLinkService;


    @ForegroundOperationLogger(value = "友链模块-用户申请友链",type = "添加",desc = "用户申请友链")
    @PostMapping(value = "/add")
    @ApiOperation(value = "申请友链", httpMethod = "POST", response = ResponseResult.class, notes = "申请友链")
    public ResponseResult addLink(@RequestBody FriendLink friendLink){
        return friendLinkService.applyFriendLink(friendLink);
    }

    @ForegroundOperationLogger(value = "友链模块-用户访问页面",type = "查询",desc = "友链列表")
    @PostMapping(value = "/list")
    @ApiOperation(value = "友链列表", httpMethod = "POST", response = ResponseResult.class, notes = "友链列表")
    public ResponseResult webFriendLinkList(){
        return friendLinkService.webFriendLinkList();
    }

}

