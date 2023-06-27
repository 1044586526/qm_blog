package com.qm.controller.system;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.qm.annotation.BackgroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.entity.Category;
import com.qm.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理入口
 *
 * @Author: qin
 * @Date: 2023/6/7 17:19
 */

@RestController
@RequestMapping("/system/category")
@RequiredArgsConstructor
@Api(tags = "分类管理")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(value = "/list")
    @SaCheckLogin
    @ApiOperation(value = "分类列表", httpMethod = "GET", response = ResponseResult.class, notes = "分类列表")
    public ResponseResult list(String name){
        return categoryService.listCategory(name);
    }

    @GetMapping(value = "/info")
    @SaCheckPermission("/system/category/info")
    @ApiOperation(value = "分类详情", httpMethod = "GET", response = ResponseResult.class, notes = "分类详情")
    public ResponseResult getCategoryById(@RequestParam() Long id){
        return categoryService.getCategoryById(id);
    }

    @PostMapping(value = "/add")
    @SaCheckPermission("/system/category/add")
    @ApiOperation(value = "新增分类", httpMethod = "POST", response = ResponseResult.class, notes = "新增分类")
    @BackgroundOperationLogger(value = "新增分类")
    public ResponseResult insertCategory(@RequestBody Category category){
        return categoryService.insertCategory(category);
    }

    @PostMapping(value = "/update")
    @SaCheckPermission("/system/category/update")
    @ApiOperation(value = "修改分类", httpMethod = "POST", response = ResponseResult.class, notes = "修改分类")
    @BackgroundOperationLogger(value = "修改分类")
    public ResponseResult update(@RequestBody Category category){
        return categoryService.updateCategory(category);
    }

    @DeleteMapping(value = "/delete")
    @SaCheckPermission("/system/category/delete")
    @ApiOperation(value = "删除分类", httpMethod = "DELETE", response = ResponseResult.class, notes = "删除分类")
    @BackgroundOperationLogger(value = "删除分类")
    public ResponseResult deleteCategory(Long id){
        return categoryService.deleteCategory(id);
    }

    @DeleteMapping(value = "/deleteBatch")
    @SaCheckPermission("/system/category/deleteBatch")
    @ApiOperation(value = "批量删除分类", httpMethod = "DELETE", response = ResponseResult.class, notes = "批量删除分类")
    @BackgroundOperationLogger(value = "批量删除分类")
    public ResponseResult deleteBatch(@RequestBody List<Category> list){
        return categoryService.deleteBatch(list);
    }

    @GetMapping(value = "/top")
    @SaCheckPermission("/system/category/top")
    @ApiOperation(value = "置顶分类", httpMethod = "GET", response = ResponseResult.class, notes = "置顶分类")
    @BackgroundOperationLogger(value = "置顶分类")
    public ResponseResult top(Long id){
        return categoryService.top(id);
    }
}

