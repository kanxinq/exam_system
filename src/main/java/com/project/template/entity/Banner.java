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
@ApiModel(value = "Banner对象", description = "轮播图")
public class Banner extends BaseEntity {
//    定义一个名为Banner的实体类，继承自BaseEntity

    private static final long serialVersionUID = 1L;
//定义一个静态的、最终的长整型字段，用于在序列化和反序列化过程中确保版本兼容性
    @ApiModelProperty("轮播备注")
    private String name;
//定义一个私有字符串类型的字段，表示轮播图的备注信息
    @ApiModelProperty("轮播图")
    private String img;
//定义一个私有字符串类型的字段，表示轮播图的图片路径或 URL
    @ApiModelProperty("跳转网址")
    private String link;
//    定义一个私有字符串类型的字段，表示轮播图点击后跳转的网址
}
