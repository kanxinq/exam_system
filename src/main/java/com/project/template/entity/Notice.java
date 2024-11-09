package com.project.template.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.project.template.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 栏目表
 * </p>
 */
@Getter
@Setter
@TableName("notice")
@ApiModel(value = "Notice对象", description = "栏目表")
public class Notice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标识")
    private String name;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("发布用户")
    private String user;


}
