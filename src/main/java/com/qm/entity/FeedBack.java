package com.qm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qm.util.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author blue
 * @since 2022-01-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("b_feed_back")
@ApiModel(value="FeedBack对象", description="")
public class FeedBack implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "ID")
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "详细内容")
    private String content;

    @ApiModelProperty(value = "添加时间")
      @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = DateUtils.FORMAT_STRING,timezone="GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "图片地址")
    private String imgUrl;

    @ApiModelProperty(value = "反馈类型 1:需求 2：缺陷")
    private Integer type;


}
