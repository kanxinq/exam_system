package com.project.template.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.template.enums.StateType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
//注解标识这个类将自动生成 getter、setter、equals、hashCode 和 toString 方法
public class BaseEntity implements Serializable {

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
//    使用 MyBatis-Plus 的主键注解，指定主键字段名为 “id”，并使用自动生成的主键策略。
    private Integer id;
//定义一个私有整数类型的字段，表示实体的唯一标识 ID
}
