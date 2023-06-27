package com.qm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qm.service.DictDataService;
import com.qm.common.ResponseResult;
import com.qm.entity.Dict;
import com.qm.entity.DictData;
import com.qm.mapper.DictDataMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qm.service.DictService;
import com.qm.util.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.qm.common.Constants.*;
import static com.qm.common.ResultCode.DATA_TAG_IS_EXIST;
import static com.qm.common.FieldConstants.LIMIT_ONE;
import static com.qm.enums.PublishEnum.PUBLISH;

/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author blue
 * @since 2021-11-25
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements DictDataService {

    private final DictService dictService;

    /**
     * 获取字典数据列表
     * @param dictId
     * @param isPublish
     * @return
     */
    @Override
    public ResponseResult listDictData(Integer dictId, Integer isPublish) {
        LambdaQueryWrapper<DictData> queryWrapper = new LambdaQueryWrapper<DictData>()
                .eq(DictData::getDictId,dictId).eq(isPublish != null,DictData::getIsPublish,isPublish);
        Page<DictData> data = baseMapper.selectPage(new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize()), queryWrapper);
        data.getRecords().forEach(item ->{
            Dict dict = dictService.getById(item.getDictId());
            item.setDict(dict);
        });
        return ResponseResult.success(data);
    }

    /**
     * 添加字典数据
     * @param dictData
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult insertDictData(DictData dictData) {
        // 判断添加的字典数据是否存在
        isExist(dictData);
        baseMapper.insert(dictData);
        return ResponseResult.success();
    }

    /**
     * 修改字典数据
     * @param sysDictData
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateDictData(DictData sysDictData) {

        DictData dictData = baseMapper.selectOne(new LambdaQueryWrapper<DictData>().eq(DictData::getLabel,sysDictData.getLabel()));
        if (dictData != null && !dictData.getId().equals(sysDictData.getId())){
            return ResponseResult.error("该标签已存在!");
        }

        baseMapper.updateById(sysDictData);
        return ResponseResult.success();
    }

    /**
     * 批量删除字典数据
     * @param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteBatch(List<Long> ids) {
        baseMapper.deleteBatchIds(ids);
        return ResponseResult.success();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteDictData(Long id) {
        baseMapper.deleteById(id);
        return ResponseResult.success();
    }

    /**
     * 根据字典类型获取字典数据
     * @param types
     * @return
     */
    @Override
    public ResponseResult getDataByDictType(List<String> types) {

        //字典信息
        List<Dict> dictList = dictService.list(new LambdaQueryWrapper<Dict>()
                .in(Dict::getType)
                .eq(Dict::getIsPublish,PUBLISH.getCode()));
        if(CollectionUtils.isEmpty(dictList)){
            return ResponseResult.success();
        }

        Map<String, Map<String, Object>> map = new HashMap<>(dictList.size());
        dictList.forEach(item ->{
            List<DictData> dataList = baseMapper.selectList(new LambdaQueryWrapper<DictData>()
                    .eq(DictData::getIsPublish, PUBLISH.getCode())
                    .eq(DictData::getDictId, item.getId())
                    .orderByAsc(DictData::getSort)
            );
            //默认字典数据
            Optional<DictData> defaultDictData = dataList.stream()
                    .filter(dictData -> dictData.getIsDefault().equals(ONE))
                    .findFirst();
            String defaultValue = defaultDictData.map(DictData::getValue).orElse(null);

            Map<String, Object> result = new HashMap<>(2);
            result.put(DEFAULT_VALUE,defaultValue);
            result.put(LIST,dataList);
            map.put(item.getType(),result);
        });
        return ResponseResult.success(map);
    }

    public void isExist(DictData dictData){
        DictData temp = baseMapper.selectOne(new LambdaQueryWrapper<DictData>()
                .eq(DictData::getLabel, dictData.getLabel())
                .eq(DictData::getDictId, dictData.getDictId())
                .last(LIMIT_ONE));
        Assert.notNull(temp,DATA_TAG_IS_EXIST.getDesc());
    }
}
