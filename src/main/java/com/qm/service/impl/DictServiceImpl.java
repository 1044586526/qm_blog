package com.qm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qm.common.ResponseResult;
import com.qm.common.FieldConstants;
import com.qm.entity.Dict;
import com.qm.entity.DictData;
import com.qm.mapper.DictMapper;
import com.qm.service.DictDataService;
import com.qm.service.DictService;
import com.qm.util.HumpLineUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qm.util.PageUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.qm.common.FieldConstants.LIMIT_ONE;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author blue
 * @since 2021-11-25
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    private final DictDataService dictDataService;

    /**
     * 字典列表
     * @param name
     * @param isPublish
     * @param descColumn
     * @param ascColumn
     * @return
     */
    @Override
    public ResponseResult listDict(String name, Integer isPublish, String descColumn, String ascColumn) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<Dict>();
        if(StringUtils.isNotEmpty(ascColumn)) {
            // 将驼峰转换成下划线
            String column = HumpLineUtils.humpToLine2(ascColumn);
            queryWrapper.orderByAsc(StringUtils.isNotEmpty(ascColumn),column);
        }else if(StringUtils.isNotEmpty(descColumn)) {
            // 将驼峰转换成下划线
            String column = HumpLineUtils.humpToLine2(descColumn);
            queryWrapper.orderByDesc(column);
        } else {
            queryWrapper.orderByDesc(FieldConstants.SORT, FieldConstants.CREATE_TIME);
        }
        queryWrapper.lambda()
                .eq(isPublish != null, Dict::getIsPublish,isPublish)
                .like(StringUtils.isNotBlank(name), Dict::getName,name);
        Page<Dict> page = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        Page<Dict> data = baseMapper.selectPage(page, queryWrapper);
        return ResponseResult.success(data);
    }

    /**
     * 添加字典
     * @param dict
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult insertDict(Dict dict) {
        validateType(dict.getType());
        baseMapper.insert(dict);
        return ResponseResult.success();
    }

    /**
     * 修改字典
     * @param dict
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateDict(Dict dict) {
        Dict temp = baseMapper.selectById(dict.getId());
        if (!temp.getType().equals(dict.getType())){
            validateType(dict.getType());
        }
        baseMapper.updateById(dict);
        return ResponseResult.success();
    }

    /**
     * 删除字典
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteDict(int id) {
        int count  = dictDataService.count(new LambdaQueryWrapper<DictData>().eq(DictData::getDictId,id));
        Assert.isTrue(count == 0,"该字典类型存在字典数据!");
        baseMapper.deleteById(id);
        return ResponseResult.success();
    }

    /**
     * 批量删除字典
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteBatch(List<Long> ids) {
        int count  = dictDataService.count(new LambdaQueryWrapper<DictData>().in(DictData::getDictId,ids));
        Assert.isTrue(count==0,"所选字典类型中存在字典数据!");
        baseMapper.deleteBatchIds(ids);
        return ResponseResult.success();
    }


    public void validateType(String type){
        Dict temp  = baseMapper.selectOne(new LambdaQueryWrapper<Dict>().eq(Dict::getType, type).last(LIMIT_ONE));
        Assert.isNull(temp,"该字典类型已存在!");
    }
}
