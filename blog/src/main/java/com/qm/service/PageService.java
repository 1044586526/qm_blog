package com.qm.service;

import com.qm.common.ResponseResult;
import com.qm.entity.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author blue
 * @since 2021-12-26
 */
public interface PageService extends IService<Page> {

    ResponseResult listPage();

    ResponseResult insertPage(Page page);

    ResponseResult updatePage(Page page);

    ResponseResult deletePageById(Long id);
}
