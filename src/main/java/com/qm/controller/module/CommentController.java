package com.qm.controller.module;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.qm.annotation.ForegroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.dto.CommentDTO;
import com.qm.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 评论管理入口
 *
 * @Author: qin
 * @Date: 2023/6/7 16:40
 */

@RestController
@RequestMapping("/web/comment")
@Api(tags = "评论接口")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @ForegroundOperationLogger(value = "评论模块-用户评论",type = "添加",desc = "用户评论")
    @PostMapping(value = "/addComment")
    @SaCheckLogin
    @ApiOperation(value = "添加评论", httpMethod = "POST", response = ResponseResult.class, notes = "添加评论")
    public ResponseResult addComment(@RequestBody CommentDTO comment){
        return commentService.addComment(comment);
    }

    @GetMapping(value = "/comments")
    @ApiOperation(value = "查询文章评论", httpMethod = "GET", response = ResponseResult.class, notes = "查询文章评论")
    public ResponseResult comments(Long articleId){
        return commentService.comments(articleId);
    }

    @GetMapping(value = "/repliesByComId")
    @ApiOperation(value = "查询评论回复", httpMethod = "GET", response = ResponseResult.class, notes = "查询文章评论")
    public ResponseResult repliesByComId(Integer commentId){
        return commentService.repliesByComId(commentId);
    }
}
