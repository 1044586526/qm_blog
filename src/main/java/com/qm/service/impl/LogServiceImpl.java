package com.qm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qm.common.ResponseResult;
import com.qm.entity.UserLog;
import com.qm.mapper.UserLogMapper;
import com.qm.service.UserLogService;
import com.qm.util.PageUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author blue
 * @since 2021-11-09
 */
@Service
public class LogServiceImpl extends ServiceImpl<UserLogMapper, UserLog> implements UserLogService {

    /**
     * 用户日志列表
     * @return
     */
    @Override
    public ResponseResult listUserLog() {
        LambdaQueryWrapper<UserLog> queryWrapper = new LambdaQueryWrapper<UserLog>()
                .orderByDesc(UserLog::getCreateTime);
        Page<UserLog> sysLogPage = baseMapper.selectPage(new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize()), queryWrapper);
        return ResponseResult.success(sysLogPage);
    }

    /**
     * 批量删除用户日志
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteBatch(List<Long> ids) {
        int rows = baseMapper.deleteBatchIds(ids);
        return rows > 0 ? ResponseResult.success(): ResponseResult.error("批量删除用户日志失败");
    }
}
