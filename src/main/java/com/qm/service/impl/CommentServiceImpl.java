package com.qm.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qm.exception.BusinessException;
import com.qm.vo.CommentVO;
import com.qm.vo.ReplyCountVO;
import com.qm.vo.ReplyVO;
import com.qm.common.ResponseResult;
import com.qm.vo.SystemCommentVO;
import com.qm.entity.Comment;
import com.qm.entity.UserAuth;
import com.qm.util.PageUtils;
import com.qm.dto.CommentDTO;
import com.qm.mapper.CommentMapper;
import com.qm.mapper.UserAuthMapper;
import com.qm.service.CommentService;
import com.qm.util.DateUtils;
import com.qm.util.HTMLUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 博客文章表 服务实现类
 * </p>
 *
 * @author blue
 * @since 2021-08-18
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private final UserAuthMapper userAuthMapper;

    /**
     * 评论列表
     *
     * @param keywords
     * @return
     */
    @Override
    public ResponseResult listComment(String keywords) {
        Page<SystemCommentVO> dtoPage = baseMapper.selectPageList(new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize()), keywords);
        return ResponseResult.success(dtoPage);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @Override
    public ResponseResult deleteBatch(List<Integer> ids) {
        baseMapper.deleteBatchIds(ids);
        return ResponseResult.success();
    }


    @Override
    public ResponseResult comments(Long articleId) {
        // 查询文章评论量
        Integer commentCount = baseMapper.selectCount(new LambdaQueryWrapper<Comment>()
                .eq(Objects.nonNull(articleId), Comment::getArticleId, articleId)
                .isNull(Objects.isNull(articleId), Comment::getArticleId)
                .isNull(Comment::getParentId));
        if (commentCount == 0) {
            return ResponseResult.success();
        }
        Page<Comment> pages = baseMapper.selectPage(new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize()),
                new LambdaQueryWrapper<Comment>().eq(Comment::getArticleId, articleId).isNull(Comment::getParentId)
                        .orderByDesc(Comment::getId));
        // 分页查询评论集合
        List<Comment> comments = pages.getRecords();
        if (CollectionUtils.isEmpty(comments)) {
            return ResponseResult.success();
        }

        //参数设置
        List<CommentVO> commentVoList = comments.stream().map(c ->{
            UserAuth userAuth = userAuthMapper.getByUserId(c.getUserId());
            ReplyCountVO replyCountVO = baseMapper.listReplyCountByCommentId(c.getId());
            CommentVO dto = new CommentVO();
            dto.setId(c.getId());
            dto.setUserId(c.getUserId());
            dto.setCommentContent(c.getContent());
            dto.setCreateTime(c.getCreateTime());
            dto.setAvatar(userAuth.getAvatar());
            dto.setNickname(userAuth.getNickname());
            // 根据评论id集合查询回复数据
            dto.setReplyVOList(baseMapper.listReplies(c.getId()));
            dto.setReplyCount(replyCountVO == null ? 0 : replyCountVO.getReplyCount());
            return dto;
        }).collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>(2);
        map.put("commentCount", commentCount);
        map.put("commentDTOList", commentVoList);
        return ResponseResult.success(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addComment(CommentDTO commentDTO) {
        if (commentDTO.getReplyUserId() != null && commentDTO.getUserId() == null) {
            throw new BusinessException("非法请求评论!");
        } else {
            if (commentDTO.getUserId() != null) {
                throw new BusinessException("非法请求评论!");
            }
        }

        // 过滤标签
        commentDTO.setCommentContent(HTMLUtils.deleteTag(commentDTO.getCommentContent()));
        Comment comment = Comment.builder()
                .userId(StpUtil.getLoginIdAsLong())
                .replyUserId(commentDTO.getReplyUserId())
                .articleId(commentDTO.getArticleId())
                .content(commentDTO.getCommentContent())
                .parentId(commentDTO.getParentId()).createTime(DateUtils.getNowDate())
                .build();
        int rows = baseMapper.insert(comment);

        return rows > 0 ? ResponseResult.success(comment) : ResponseResult.error("评论失败");
    }

    @Override
    public ResponseResult repliesByComId(Integer commentId) {
        Page<Comment> page = baseMapper.selectPage(new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize()),
                new LambdaQueryWrapper<Comment>().eq(Comment::getParentId, commentId));
        List<ReplyVO> result = page.getRecords().stream()
                .map(r ->{
                    UserAuth userAuth = getUserAuth(r.getUserId());
                    UserAuth replyUser = getUserAuth(r.getReplyUserId().longValue());
                    ReplyVO dto = new ReplyVO();
                    dto.setId(r.getId());
                    dto.setAvatar(userAuth.getAvatar());
                    dto.setNickname(userAuth.getNickname());
                    dto.setContent(r.getContent());
                    dto.setCreateTime(r.getCreateTime());
                    dto.setParentId(commentId);
                    dto.setReplyNickname(replyUser.getNickname());
                    return dto;
                }).collect(Collectors.toList());
        return ResponseResult.success(result);
    }

    private UserAuth getUserAuth(Long id){
        return userAuthMapper.getByUserId(id);
    }
}
