package com.qm.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qm.common.ResponseResult;
import com.qm.entity.SystemConfig;
import com.qm.entity.User;
import com.qm.mapper.SystemConfigMapper;
import com.qm.service.SystemConfigService;
import com.qm.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.qm.common.Constants.USER_ROLE_ID;
import static com.qm.common.FieldConstants.LIMIT_ONE;

/**
 * <p>
 * 系统配置表 服务实现类
 * </p>
 *
 * @author blue
 * @since 2021-11-25
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SystemConfigServiceImpl extends ServiceImpl<SystemConfigMapper, SystemConfig> implements SystemConfigService {


    private final UserService userService;

    /**
     * 获取系统配置
     * @return
     */
    @Override
    public ResponseResult getConfig() {
        LambdaQueryWrapper<SystemConfig> queryWrapper = new LambdaQueryWrapper<>();
        User user = userService.getById(StpUtil.getLoginIdAsInt());
        if (user.getRoleId() > USER_ROLE_ID){
            queryWrapper.orderByDesc(SystemConfig::getId);
        }
        queryWrapper.last(LIMIT_ONE);
        SystemConfig systemConfig = baseMapper.selectOne(queryWrapper);
        return ResponseResult.success(systemConfig);
    }

    /**
     * 修改系统配置
     * @param systemConfig
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateConfig(SystemConfig systemConfig) {
        baseMapper.updateById(systemConfig);
        return ResponseResult.success();
    }

    //---------自定义方法----------
    @Override
    public SystemConfig getCustomizeOne() {
        return baseMapper.selectOne(new QueryWrapper<SystemConfig>().last(LIMIT_ONE));
    }
}
