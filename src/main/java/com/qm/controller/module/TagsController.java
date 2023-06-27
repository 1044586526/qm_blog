package com.qm.controller.module;


import com.qm.annotation.ForegroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.service.CategoryService;
import com.qm.service.TagsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 标签分类管理入口
 *
 * @Author: qin
 * @Date: 2023/6/7 16:58
 */

@RestController
@RequestMapping("/web/tags")
@Api(tags = "标签分类接口")
@RequiredArgsConstructor
public class TagsController {

    private final TagsService tagsService;

    private final CategoryService categoryService;

    @ForegroundOperationLogger(value = "标签模块-用户访问页面",type = "查询",desc = "用户访问页面")
    @GetMapping(value = "/list")
    @ApiOperation(value = "标签列表", httpMethod = "GET", response = ResponseResult.class, notes = "标签列表")
    public ResponseResult tagList(){
        return tagsService.webList();
    }

    @ForegroundOperationLogger(value = "分类模块-用户访问页面",type = "查询",desc = "用户访问页面")
    @GetMapping(value = "/categoryList")
    @ApiOperation(value = "分类列表", httpMethod = "GET", response = ResponseResult.class, notes = "分类列表")
    public ResponseResult categoryList(){
        return categoryService.webList();
    }


}

