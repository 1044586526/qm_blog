package com.qm.controller.module;

import com.qm.annotation.ForegroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 文章管理入口
 *
 * @Author: qin
 * @Date: 2023/6/7 14:50
 */

@RestController
@RequestMapping("/web/article")
@Api(tags = "前台文章管理")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @ForegroundOperationLogger(value = "首页-首页访问",type = "查询",desc = "查询所有文章")
    @GetMapping(value = "/list")
    @ApiOperation(value = "文章列表", httpMethod = "GET", response = ResponseResult.class, notes = "文章列表")
    public ResponseResult listWebArticle() {
        return  articleService.listWebArticle();
    }

    @ForegroundOperationLogger(value = "首页-文章搜索",type = "查询",desc = "文章搜索")
    @GetMapping(value = "/searchArticle")
    @ApiOperation(value = "文章搜索", httpMethod = "GET", response = ResponseResult.class, notes = "文章搜索")
    public ResponseResult searchArticle(String keywords) {
        return  articleService.searchArticle(keywords);
    }

    @ForegroundOperationLogger(value = "首页-归档",type = "查询",desc = "归档")
    @GetMapping(value = "/archive")
    @ApiOperation(value = "归档", httpMethod = "GET", response = ResponseResult.class, notes = "归档")
    public ResponseResult archive() {
        return  articleService.archive();
    }

    @ForegroundOperationLogger(value = "分类标签文章列表",type = "查询",desc = "分类标签文章列表")
    @GetMapping(value = "/condition")
    @ApiOperation(value = "分类标签文章列表", httpMethod = "GET", response = ResponseResult.class, notes = "分类标签文章列表")
    public ResponseResult condition(Long categoryId, Long tagId, @RequestParam(defaultValue = "6") Integer pageSize) {
        return  articleService.condition(categoryId,tagId,pageSize);
    }

    @ForegroundOperationLogger(value = "门户-用户查看文章详情",type = "查询",desc = "查看文章详情")
    @GetMapping(value = "/info")
    @ApiOperation(value = "文章详情", httpMethod = "GET", response = ResponseResult.class, notes = "文章详情")
    public ResponseResult getArticle(Integer id) {
        return articleService.webArticleInfo(id);
    }

    @ForegroundOperationLogger(value = "门户-文章点赞",type = "查询",desc = "文章点赞")
    @GetMapping(value = "/articleLike")
    @ApiOperation(value = "文章点赞", httpMethod = "GET", response = ResponseResult.class, notes = "文章点赞")
    public ResponseResult articleLike(Integer articleId) {
        return articleService.articleLike(articleId);
    }

    @ForegroundOperationLogger(value = "文章中-用户验证秘钥",type = "查询",desc = "验证秘钥")
    @GetMapping(value = "/checkSecret")
    @ApiOperation(value = "验证秘钥", httpMethod = "GET", response = ResponseResult.class, notes = "验证秘钥")
    public ResponseResult checkSecret(String code) {
        return articleService.checkSecret(code);
    }
}
