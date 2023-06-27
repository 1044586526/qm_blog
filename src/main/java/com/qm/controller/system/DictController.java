package com.qm.controller.system;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.qm.annotation.BackgroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.entity.Dict;
import com.qm.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典类型管理入口
 *
 * @Author: qin
 * @Date: 2023/6/7 17:19
 */

@RestController
@RequestMapping("/system/dict")
@Api(tags = "字典类型管理")
@RequiredArgsConstructor
public class DictController {

    private final DictService dictService;

    @GetMapping(value = "/list")
    @SaCheckLogin
    @ApiOperation(value = "字典类型列表", httpMethod = "GET", response = ResponseResult.class, notes = "字典类型列表")
    public ResponseResult list(String name, Integer isPublish, String descColumn, String ascColumn){
        return dictService.listDict(name,isPublish,descColumn,ascColumn);
    }

    @PostMapping(value = "/add")
    @SaCheckPermission("/system/dict/add")
    @ApiOperation(value = "添加字典", httpMethod = "POST", response = ResponseResult.class, notes = "添加字典")
    @BackgroundOperationLogger(value = "添加字典")
    public ResponseResult insert(@RequestBody Dict dict){
        return dictService.insertDict(dict);
    }

    @PostMapping(value = "/update")
    @SaCheckPermission("/system/dict/update")
    @ApiOperation(value = "修改字典", httpMethod = "POST", response = ResponseResult.class, notes = "修改字典")
    @BackgroundOperationLogger(value = "修改字典")
    public ResponseResult update(@RequestBody Dict dict){
        return dictService.updateDict(dict);
    }

    @DeleteMapping(value = "/delete")
    @SaCheckPermission("/system/dict/delete")
    @ApiOperation(value = "删除字典", httpMethod = "DELETE", response = ResponseResult.class, notes = "删除字典")
    @BackgroundOperationLogger(value = "删除字典")
    public ResponseResult deleteDict(int id){
        return dictService.deleteDict(id);
    }

    @DeleteMapping(value = "/deleteBatch")
    @SaCheckPermission("/system/dict/deleteBatch")
    @ApiOperation(value = "批量删除字典", httpMethod = "DELETE", response = ResponseResult.class, notes = "批量删除字典")
    @BackgroundOperationLogger(value = "批量删除字典")
    public ResponseResult deleteBatch(@RequestBody List<Long> list){
        return dictService.deleteBatch(list);
    }
}

