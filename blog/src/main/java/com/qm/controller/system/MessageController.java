package com.qm.controller.system;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.qm.annotation.BackgroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 留言管理入口
 *
 * @Author: qin
 * @Date: 2023/6/7 17:21
 */

@RestController
@RequestMapping("/system/message")
@Api(tags = "留言管理-接口")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping(value="/list")
    @SaCheckLogin
    @ApiOperation(value = "留言列表", httpMethod = "GET", response = ResponseResult.class, notes = "留言列表")
    public ResponseResult list(String name){
        return messageService.listMessage(name);
    }

    @PostMapping(value="/passBatch")
    @SaCheckPermission("/system/message/passBatch")
    @BackgroundOperationLogger(value = "批量通过")
    @ApiOperation(value = "批量通过", httpMethod = "POST", response = ResponseResult.class, notes = "批量通过")
    public ResponseResult passBatch(@RequestBody List<Integer> ids){
        return messageService.passBatch(ids);
    }


    @DeleteMapping(value = "/remove")
    @SaCheckPermission("/system/message/remove")
    @BackgroundOperationLogger(value = "删除留言")
    @ApiOperation(value = "删除留言", httpMethod = "DELETE", response = ResponseResult.class, notes = "删除留言")
    public ResponseResult deleteMessageById(int id){
        return messageService.deleteMessageById(id);
    }


    @DeleteMapping(value = "/deleteBatch")
    @SaCheckPermission("/system/message/deleteBatch")
    @BackgroundOperationLogger(value = "批量删除留言")
    @ApiOperation(value = "批量删除留言", httpMethod = "DELETE", response = ResponseResult.class, notes = "批量删除留言")
    public ResponseResult deleteBatch(@RequestBody List<Integer> ids){
        return messageService.deleteBatch(ids);
    }
}

