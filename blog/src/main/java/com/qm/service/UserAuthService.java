package com.qm.service;

import com.qm.common.ResponseResult;
import com.qm.entity.UserAuth;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qm.dto.EmailLoginDTO;
import com.qm.dto.EmailRegisterDTO;
import com.qm.dto.QQLoginDTO;
import com.qm.dto.UserAuthDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author blue
 * @since 2021-12-25
 */
public interface UserAuthService extends IService<UserAuth> {

    ResponseResult emailRegister(EmailRegisterDTO emailRegisterDTO);

    ResponseResult updatePassword(EmailRegisterDTO emailRegisterDTO);

    ResponseResult emailLogin(EmailLoginDTO emailLoginDTO);

    ResponseResult qqLogin(QQLoginDTO qqLoginDTO);

    ResponseResult weiboLogin(String code);

    ResponseResult giteeLogin(String code);

    ResponseResult sendEmailCode(String email);

    ResponseResult bindEmail(UserAuthDTO vo);

    ResponseResult updateUser(UserAuthDTO vo);

}
