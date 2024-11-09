package com.project.template.entity;

import com.project.template.entity.BaseEntity;
import java.io.Serializable;
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
@ApiModel(value = "Link对象", description = "轮播图")
public class Link extends BaseEntity {

    private static final long serialVersionUID = 1L;
    //定义一个静态的、最终的长整型字段，用于在序列化和反序列化过程中确保版本兼容性
    @ApiModelProperty("网站名称")
    private String name;

    @ApiModelProperty("网站网址")
    private String link;
}
