package com.qm.controller.system;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.qm.annotation.BackgroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.entity.Tags;
import com.qm.service.TagsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签管理入口
 *
 * @Author: qin
 * @Date: 2023/6/7 17:23
 */

@RestController
@RequestMapping("/system/tags")
@Api(tags = "标签管理")
@RequiredArgsConstructor
public class TagsController {

    private final TagsService tagsService;

    @GetMapping(value = "/list")
    @SaCheckLogin
    @ApiOperation(value = "标签列表", httpMethod = "GET", response = ResponseResult.class, notes = "标签列表")
    public ResponseResult list(String name){
        return tagsService.listTags(name);
    }

    @PostMapping(value = "/add")
    @SaCheckPermission("/system/tags/add")
    @ApiOperation(value = "新增标签", httpMethod = "POST", response = ResponseResult.class, notes = "新增标签")
    @BackgroundOperationLogger(value = "新增标签")
    public ResponseResult insert(@RequestBody Tags tags){
        return tagsService.insertTag(tags);
    }

    @GetMapping(value = "/info")
    @SaCheckPermission("/system/tags/info")
    @ApiOperation(value = "标签详情", httpMethod = "GET", response = ResponseResult.class, notes = "标签详情")
    public ResponseResult getTagsById(Long id){
        return tagsService.getTagsById(id);
    }

    @PostMapping(value = "/update")
    @SaCheckPermission("/system/tags/update")
    @ApiOperation(value = "修改标签", httpMethod = "POST", response = ResponseResult.class, notes = "修改标签")
    @BackgroundOperationLogger(value = "修改标签")
    public ResponseResult update(@RequestBody Tags tags){
        return tagsService.updateTag(tags);
    }

    @DeleteMapping(value = "/remove")
    @SaCheckPermission("/system/tags/remove")
    @ApiOperation(value = "删除标签", httpMethod = "DELETE", response = ResponseResult.class, notes = "删除标签")
    @BackgroundOperationLogger(value = "删除标签")
    public ResponseResult deleteById(Long  id){
        return tagsService.deleteById(id);
    }

    @DeleteMapping(value = "/deleteBatch")
    @SaCheckPermission("/system/tags/deleteBatch")
    @ApiOperation(value = "批量删除标签", httpMethod = "DELETE", response = ResponseResult.class, notes = "批量删除标签")
    @BackgroundOperationLogger(value = "批量删除标签")
    public ResponseResult deleteBatch(@RequestBody List<Long> ids){
        return tagsService.deleteBatch(ids);
    }

    @GetMapping(value = "/top")
    @SaCheckPermission("/system/tags/top")
    @ApiOperation(value = "置顶标签", httpMethod = "GET", response = ResponseResult.class, notes = "置顶标签")
    @BackgroundOperationLogger(value = "置顶标签")
    public ResponseResult top(Long id){
        return tagsService.top(id);
    }
}

