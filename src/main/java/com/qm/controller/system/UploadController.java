package com.qm.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.qm.annotation.BackgroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.service.CloudOssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传入口
 *
 * @Author: qin
 * @Date: 2023/6/7 17:23
 */

@RestController
@RequestMapping("/file")
@Api(tags = "图片上传-接口")
@RequiredArgsConstructor
public class UploadController {

    private final CloudOssService cloudOssService;

    @PostMapping(value = "/upload")
    @SaCheckPermission("/file/upload")
    @ApiOperation(value = "上传图片",httpMethod = "POST", response = ResponseResult.class, notes = "上传图片")
    public ResponseResult upload(MultipartFile multipartFile){
        return cloudOssService.upload(multipartFile);
    }

    @PostMapping(value = "/delBatchFile")
    @SaCheckPermission("/file/delBatchFile")
    @ApiOperation(value = "批量删除文件",httpMethod = "POST", response = ResponseResult.class, notes = "批量删除文件")
    @BackgroundOperationLogger("批量删除图片")
    public ResponseResult delBatchFile(String key){
        return cloudOssService.delBatchFile(key);
    }
}
