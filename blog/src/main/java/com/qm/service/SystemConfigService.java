package com.qm.service;

import com.qm.common.ResponseResult;
import com.qm.entity.SystemConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统配置表 服务类
 * </p>
 *
 * @author blue
 * @since 2021-11-25
 */
public interface SystemConfigService extends IService<SystemConfig> {

    ResponseResult getConfig();

    ResponseResult updateConfig(SystemConfig systemConfig);

    SystemConfig getCustomizeOne();
}
