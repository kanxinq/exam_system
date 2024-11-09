package com.project.template.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class EditPasswordDTO {
//定义一个名为EditPasswordDTO的数据传输对象类
    @ApiModelProperty("id")
    private Integer id;
//定义一个私有整数类型的字段，表示用户的 ID
    @ApiModelProperty("密码")
    private String password;
//    定义一个私有字符串类型的字段，表示用户的当前密码
    @ApiModelProperty("新密码")
    private String newPassword;
//    定义一个私有字符串类型的字段，表示用户的新密码
}
