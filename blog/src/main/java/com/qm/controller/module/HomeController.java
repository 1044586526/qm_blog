package com.qm.controller.module;


import com.qm.common.ResponseResult;
import com.qm.service.impl.HomeServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 首页入口
 *
 * @Author: qin
 * @Date: 2023/6/7 16:57
 */

@RestController
@RequestMapping("/web/home")
@Api(tags = "门户首页管理")
@RequiredArgsConstructor
public class HomeController {

    private final HomeServiceImpl homeService;


    @GetMapping(value = "/webSiteInfo")
    @ApiOperation(value = "网站信息", httpMethod = "GET", response = ResponseResult.class, notes = "网站信息")
    public ResponseResult webSiteInfo(){
        return homeService.webSiteInfo();
    }

    @GetMapping(value = "/report")
    @ApiOperation(value = "增加访问量", httpMethod = "GET", response = ResponseResult.class, notes = "增加访问量")
    public ResponseResult report(HttpServletRequest request){
        return homeService.report(request);
    }
}

