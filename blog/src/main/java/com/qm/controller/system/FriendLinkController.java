package com.qm.controller.system;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.qm.annotation.BackgroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.entity.FriendLink;
import com.qm.service.FriendLinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 友情链接入口
 *
 * @Author: qin
 * @Date: 2023/6/7 17:20
 */

@RestController
@RequestMapping("/system/friend")
@Api(tags = "友情链接后端-接口")
@RequiredArgsConstructor
public class FriendLinkController {

    private final FriendLinkService friendLinkService;

    @GetMapping(value = "/list")
    @SaCheckLogin
    @ApiOperation(value = "友链列表", httpMethod = "GET", response = ResponseResult.class, notes = "友链列表")
    public ResponseResult list(String name, Integer status){
        return friendLinkService.listFriendLink(name,status);
    }

    @PostMapping(value = "/create")
    @SaCheckPermission("/system/friend/create")
    @ApiOperation(value = "添加友链", httpMethod = "POST", response = ResponseResult.class, notes = "添加友链")
    @BackgroundOperationLogger(value = "添加友链")
    public ResponseResult insert(@RequestBody FriendLink friendLink){
        return friendLinkService.insertFriendLink(friendLink);
    }

    @PostMapping(value = "/update")
    @SaCheckPermission("/system/friend/update")
    @ApiOperation(value = "修改友链", httpMethod = "POST", response = ResponseResult.class, notes = "修改友链")
    @BackgroundOperationLogger(value = "修改友链")
    public ResponseResult update(@RequestBody FriendLink friendLink){
        return friendLinkService.updateFriendLink(friendLink);
    }

    @DeleteMapping(value = "/remove")
    @SaCheckPermission("/system/friend/remove")
    @ApiOperation(value = "删除友链", httpMethod = "DELETE", response = ResponseResult.class, notes = "删除友链")
    @BackgroundOperationLogger(value = "删除友链")
    public ResponseResult deleteBatch(@RequestBody List<Integer> ids){
        return friendLinkService.deleteBatch(ids);
    }

    @GetMapping(value = "/top")
    @SaCheckPermission("/system/friend/top")
    @ApiOperation(value = "置顶友链", httpMethod = "GET", response = ResponseResult.class, notes = "置顶友链")
    @BackgroundOperationLogger(value = "置顶友链")
    public ResponseResult top(Integer id){
        return friendLinkService.top(id);
    }
}

