package com.qm.controller.system;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.qm.annotation.BackgroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论管理入口
 *
 * @Author: qin
 * @Date: 2023/6/7 17:19
 */

@RestController
@RequestMapping("/system/comment")
@Api(tags = "评论管理")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping(value = "/list")
    @SaCheckLogin
    @ApiOperation(value = "评论列表", httpMethod = "GET", response = ResponseResult.class, notes = "评论列表")
    public ResponseResult list(String keywords){
        return commentService.listComment(keywords);
    }

    @DeleteMapping(value = "/deleteBatch")
    @SaCheckPermission("/system/comment/deleteBatch")
    @ApiOperation(value = "批量删除评论", httpMethod = "DELETE", response = ResponseResult.class, notes = "批量删除评论")
    @BackgroundOperationLogger(value = "删除评论")
    public ResponseResult deleteBatch(@RequestBody List<Integer> ids){
        return commentService.deleteBatch(ids);
    }

}
