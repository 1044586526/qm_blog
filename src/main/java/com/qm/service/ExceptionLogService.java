package com.qm.service;

import com.qm.common.ResponseResult;
import com.qm.entity.ExceptionLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author blue
 * @since 2021-11-11
 */
public interface ExceptionLogService extends IService<ExceptionLog> {

    ResponseResult listExceptionLog();

    ResponseResult deleteBatch(List<Long> ids);
}
