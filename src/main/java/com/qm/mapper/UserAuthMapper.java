package com.qm.mapper;

import com.qm.entity.UserAuth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author blue
 * @since 2021-12-25
 */
@Repository
public interface UserAuthMapper extends BaseMapper<UserAuth> {

    void deleteByUserIds(@Param("ids") List<Integer> ids);

    UserAuth getByUserId(Object loginId);
}
