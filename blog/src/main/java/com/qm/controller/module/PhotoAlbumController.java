package com.qm.controller.module;


import com.qm.common.ResponseResult;
import com.qm.service.PhotoAlbumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 相册管理入口
 *
 * @Author: qin
 * @Date: 2023/6/7 16:57
 */

@RestController
@RequestMapping("/web/album")
@Api(tags = "相册接口")
@RequiredArgsConstructor
public class PhotoAlbumController {

    private final PhotoAlbumService albumService;

    @GetMapping(value = "/list")
    @ApiOperation(value = "相册列表", httpMethod = "GET", response = ResponseResult.class, notes = "相册列表")
    public ResponseResult webAlbumList(){
        return albumService.webAlbumList();
    }

    @GetMapping(value = "/listPhotos")
    @ApiOperation(value = "照片列表", httpMethod = "GET", response = ResponseResult.class, notes = "照片列表")
    public ResponseResult webListPhotos(Integer albumId){
        return albumService.webListPhotos(albumId);
    }
}

