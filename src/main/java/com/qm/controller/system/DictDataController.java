package com.qm.controller.system;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.qm.service.DictDataService;
import com.qm.annotation.BackgroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.entity.DictData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典数据管理入口
 *
 * @Author: qin
 * @Date: 2023/6/7 17:19
 */

@RestController
@RequestMapping("/system/dictData")
@Api(tags = "字典数据管理")
@RequiredArgsConstructor
public class DictDataController {

    private final DictDataService dictDataService;

    @GetMapping(value = "/list")
    @SaCheckLogin
    @ApiOperation(value = "字典数据列表", httpMethod = "GET", response = ResponseResult.class, notes = "字典数据列表")
    public ResponseResult list(Integer dictId, Integer isPublish){
        return dictDataService.listDictData(dictId,isPublish);
    }

    @PostMapping(value = "/add")
    @SaCheckPermission("/system/dictData/add")
    @ApiOperation(value = "添加字典数据", httpMethod = "POST", response = ResponseResult.class, notes = "添加字典数据")
    @BackgroundOperationLogger(value = "添加字典数据")
    public ResponseResult insert(@RequestBody DictData dictData){
        return dictDataService.insertDictData(dictData);
    }

    @PostMapping(value = "/update")
    @SaCheckPermission("/system/dictData/update")
    @ApiOperation(value = "修改字典数据", httpMethod = "POST", response = ResponseResult.class, notes = "修改字典数据")
    @BackgroundOperationLogger(value = "修改字典数据")
    public ResponseResult update(@RequestBody DictData dictData){
        return dictDataService.updateDictData(dictData);
    }

    @DeleteMapping(value = "/delete")
    @SaCheckPermission("/system/dictData/delete")
    @ApiOperation(value = "删除字典数据", httpMethod = "DELETE", response = ResponseResult.class, notes = "删除字典数据")
    @BackgroundOperationLogger(value = "删除字典数据")
    public ResponseResult deleteDictData(Long id){
        return dictDataService.deleteDictData(id);
    }

    @DeleteMapping(value = "/deleteBatch")
    @SaCheckPermission("/system/dictData/deleteBatch")
    @ApiOperation(value = "批量删除字典数据", httpMethod = "DELETE", response = ResponseResult.class, notes = "批量删除字典数据")
    @BackgroundOperationLogger(value = "批量删除字典数据")
    public ResponseResult deleteBatch(@RequestBody List<Long> ids){
        return dictDataService.deleteBatch(ids);
    }

    @PostMapping(value = "/getDataByDictType")
    public ResponseResult getDataByDictType(@RequestBody List<String> types){
        return dictDataService.getDataByDictType(types);
    }
}

