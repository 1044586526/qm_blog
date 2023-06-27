package com.qm.controller.system;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.qm.annotation.BackgroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.entity.PhotoAlbum;
import com.qm.service.PhotoAlbumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 相册管理入口
 *
 * @Author: qin
 * @Date: 2023/6/7 17:22
 */

@RestController
@RequestMapping("/system/album")
@Api(tags = "相册管理")
@RequiredArgsConstructor
public class PhotoAlbumController {

    private final PhotoAlbumService albumService;


    @GetMapping(value = "/list")
    @SaCheckLogin
    @ApiOperation(value = "相册列表", httpMethod = "GET", response = ResponseResult.class, notes = "相册列表")
    public ResponseResult list(String name) {
        return albumService.listAlbum(name);
    }

    @GetMapping(value = "/info")
    @SaCheckPermission("/system/album/info")
    @ApiOperation(value = "相册详情", httpMethod = "GET", response = ResponseResult.class, notes = "相册详情")
    public ResponseResult getAlbumById(Integer id) {
        return albumService.getAlbumById(id);
    }

    @PostMapping(value = "/add")
    @SaCheckPermission("/system/album/add")
    @ApiOperation(value = "添加相册", httpMethod = "POST", response = ResponseResult.class, notes = "添加相册")
    @BackgroundOperationLogger(value = "添加相册")
    public ResponseResult insertAlbum(@RequestBody PhotoAlbum photoAlbum) {
        return albumService.insertAlbum(photoAlbum);
    }

    @PostMapping(value = "/update")
    @SaCheckPermission("/system/album/update")
    @ApiOperation(value = "修改相册", httpMethod = "POST", response = ResponseResult.class, notes = "修改相册")
    @BackgroundOperationLogger(value = "修改相册")
    public ResponseResult updateAlbum(@RequestBody PhotoAlbum photoAlbum) {
        return albumService.updateAlbum(photoAlbum);
    }

    @DeleteMapping(value = "/delete")
    @SaCheckPermission("/system/album/delete")
    @ApiOperation(value = "删除相册", httpMethod = "DELETE", response = ResponseResult.class, notes = "删除相册")
    @BackgroundOperationLogger(value = "删除相册")
    public ResponseResult deleteAlbumById(Integer id) {
        return albumService.deleteAlbumById(id);
    }
}

