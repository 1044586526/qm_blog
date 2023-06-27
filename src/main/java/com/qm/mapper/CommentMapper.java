package com.qm.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qm.vo.CommentVO;
import com.qm.vo.ReplyCountVO;
import com.qm.vo.ReplyVO;
import com.qm.vo.SystemCommentVO;
import com.qm.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 博客评论表 Mapper 接口
 * </p>
 *
 * @author blue
 * @since 2021-07-19
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    List<CommentVO> listComments(@Param("page") int page, @Param("size") int size, @Param("articleId") Long articleId);

    List<ReplyVO> listReplies(Integer id);

    ReplyCountVO listReplyCountByCommentId(Integer id);

    Page<SystemCommentVO> selectPageList(@Param("page")Page<Object> objectPage, @Param("keywords")String keywords);
}
