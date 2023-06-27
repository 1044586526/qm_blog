package com.qm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qm.common.ResponseResult;
import com.qm.entity.FeedBack;
import com.qm.mapper.FeedBackMapper;
import com.qm.service.FeedBackService;
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
 * @since 2022-01-13
 */
@Service
public class FeedBackServiceImpl extends ServiceImpl<FeedBackMapper, FeedBack> implements FeedBackService {

    /**
     * 反馈列表
     * @param type
     * @return
     */
    @Override
    public ResponseResult listFeedBack(Integer type) {
        Page<FeedBack> feedBackPage = baseMapper.selectPage(new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize())
                , new LambdaQueryWrapper<FeedBack>()
                        .eq(null != type,FeedBack::getType,type)
                        .orderByDesc(FeedBack::getCreateTime));

        return ResponseResult.success(feedBackPage);
    }

    /**
     * 删除反馈
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteBatch(List<Integer> ids) {
        int rows = baseMapper.deleteBatchIds(ids);
        return rows > 0 ? ResponseResult.success(): ResponseResult.error("删除反馈失败");
    }

    /**
     * 添加反馈
     * @param feedBack
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult insertFeedback(FeedBack feedBack) {
        int rows = baseMapper.insert(feedBack);
        return rows > 0 ? ResponseResult.success(): ResponseResult.error("添加反馈失败");
    }
}
