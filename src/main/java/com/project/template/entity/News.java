package com.project.template.entity;

import com.project.template.entity.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 轮播图
 * </p>
 */
@Getter
@Setter
@ApiModel(value = "News对象", description = "轮播图")
public class News extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标题")
    private String name;

    @ApiModelProperty("封面图")
    private String img;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("浏览量")
    private Integer views;

    @ApiModelProperty("发布时间")
    private Date createTime;

    @ApiModelProperty("发布用户")
    private String user;
}
