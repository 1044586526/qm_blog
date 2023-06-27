package com.qm.controller.module;


import com.qm.annotation.ForegroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.entity.Message;
import com.qm.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 评论留言管理入口
 *
 * @Author: qin
 * @Date: 2023/6/7 16:57
 */

@RestController
@RequestMapping("/web/message")
@Api(tags = "评论留言接口")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @ForegroundOperationLogger(value = "留言模块-留言列表",type = "查询",desc = "留言列表")
    @GetMapping(value = "/webMessage")
    @ApiOperation(value = "留言列表", httpMethod = "GET", response = ResponseResult.class, notes = "留言列表")
    public ResponseResult webMessage(){
        return messageService.webMessage();
    }


    @ForegroundOperationLogger(value = "留言模块-用户留言",type = "添加",desc = "用户留言")
    @PostMapping(value = "/add")
    @ApiOperation(value = "添加留言", httpMethod = "POST", response = ResponseResult.class, notes = "添加留言")
    public ResponseResult addMessage(@RequestBody Message message){
        return messageService.webAddMessage(message);
    }

}

