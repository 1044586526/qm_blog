package com.qm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qm.common.ResponseResult;
import com.qm.entity.Comment;
import com.qm.dto.CommentDTO;

import java.util.List;

/**
 * <p>
 * 博客文章表 服务类
 * </p>
 *
 * @author blue
 * @since 2021-08-18
 */
public interface CommentService extends IService<Comment> {

    ResponseResult listComment(String keywords);

    ResponseResult deleteBatch(List<Integer> ids);



//    ------web端方法开始------
    ResponseResult comments(Long articleId);

    ResponseResult addComment(CommentDTO comment);

    ResponseResult repliesByComId(Integer commentId);

}
