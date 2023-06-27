package com.qm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qm.common.ResponseResult;
import com.qm.entity.ExceptionLog;
import com.qm.mapper.ExceptionLogMapper;
import com.qm.service.ExceptionLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qm.util.PageUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author blue
 * @since 2021-11-11
 */
@Service
public class ExceptionLogServiceImpl extends ServiceImpl<ExceptionLogMapper, ExceptionLog> implements ExceptionLogService {

    @Override
    public ResponseResult listExceptionLog() {
        Page<ExceptionLog> pg = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        Page<ExceptionLog> sysLogPage = baseMapper.selectPage(pg, new LambdaQueryWrapper<ExceptionLog>().orderByDesc(ExceptionLog::getCreateTime));
        return ResponseResult.success(sysLogPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteBatch(List<Long> ids) {
        int rows = baseMapper.deleteBatchIds(ids);
        return rows > 0 ? ResponseResult.success(): ResponseResult.error("批量删除操作日志失败");
    }
}
