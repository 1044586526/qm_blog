package com.qm.controller.module;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.qm.annotation.ForegroundOperationLogger;
import com.qm.common.ResponseResult;
import com.qm.service.UserAuthService;
import com.qm.dto.EmailLoginDTO;
import com.qm.dto.EmailRegisterDTO;
import com.qm.dto.QQLoginDTO;
import com.qm.dto.UserAuthDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 登录入口
 *
 * @Author: qin
 * @Date: 2023/6/7 16:45
 */

@RequestMapping("/user")
@RestController
@Api(tags = "登录接口")
@RequiredArgsConstructor
public class UserController {

    private final UserAuthService userAuthService;

    @PostMapping(value = "/emailLogin")
    @ApiOperation(value = "邮箱登录", httpMethod = "POST", response = ResponseResult.class, notes = "邮箱登录")
    public ResponseResult emailLogin(@Valid @RequestBody EmailLoginDTO emailLoginDTO){
        return userAuthService.emailLogin(emailLoginDTO);
    }

    @PostMapping(value = "/emailRegister")
    @ApiOperation(value = "邮箱账号注册", httpMethod = "POST", response = ResponseResult.class, notes = "邮箱账号注册")
    public ResponseResult emailRegister(@Valid @RequestBody EmailRegisterDTO emailRegisterDTO){
        return userAuthService.emailRegister(emailRegisterDTO);
    }

    @ForegroundOperationLogger(value = "个人中心模块-邮箱账号修改密码",type = "修改",desc = "邮箱账号修改密码")
    @PostMapping(value = "/updatePassword")
    public ResponseResult updatePassword(@Valid @RequestBody EmailRegisterDTO emailRegisterDTO){
        return userAuthService.updatePassword(emailRegisterDTO);
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "QQ登录", httpMethod = "POST", response = ResponseResult.class, notes = "QQ登录")
    public ResponseResult login(@Valid @RequestBody QQLoginDTO qqLoginDTO){
        return userAuthService.qqLogin(qqLoginDTO);
    }

    @GetMapping(value = "/gitEELogin")
    @ApiOperation(value = "gitEE登录", httpMethod = "GET", response = ResponseResult.class, notes = "gitEE登录")
    public ResponseResult gitEeLogin(String code){
        return userAuthService.giteeLogin(code);
    }

    @GetMapping(value = "/weiboLogin")
    @ApiOperation(value = "微博登录", httpMethod = "GET", response = ResponseResult.class, notes = "微博登录")
    public ResponseResult weiboLogin(String code){
        return userAuthService.weiboLogin(code);
    }

    @GetMapping(value = "/sendEmailCode")
    @ApiOperation(value = "发送邮箱验证码", httpMethod = "GET", response = ResponseResult.class, notes = "发送邮箱验证码")
    public ResponseResult sendEmailCode(String email){
        return userAuthService.sendEmailCode(email);
    }

    @ForegroundOperationLogger(value = "个人中心模块-绑定邮箱",type = "修改",desc = "绑定邮箱")
    @PostMapping(value = "/bindEmail")
    @SaCheckLogin
    public ResponseResult bindEmail(@RequestBody UserAuthDTO vo){
        return userAuthService.bindEmail(vo);
    }

    @ForegroundOperationLogger(value = "个人中心模块-修改用户信息",type = "修改",desc = "修改用户信息")
    @SaCheckLogin
    @PostMapping(value = "/updateUser")
    public ResponseResult updateUser(@RequestBody UserAuthDTO vo){
        return userAuthService.updateUser(vo);
    }
}

