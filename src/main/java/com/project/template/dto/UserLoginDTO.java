package com.project.template.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginDTO {

    @Length(min = 2,max = 15,message = "用户名长度需要在[2和15]之间")
//    用长度校验注解，规定用户名的长度必须在 2 到 15 个字符之间
    @ApiModelProperty("用户名")
//    使用 Swagger 的注解来描述这个字段在 API 文档中的含义为 “用户名”
    private String username;
//    定义一个私有字符串类型的字段，表示用户的用户名
    @Length(min = 2,max = 15,message = "密码需要在[2和15]之间")
//    使用长度校验注解，规定密码的长度必须在 2 到 15 个字符之间
    @ApiModelProperty("密码")
    private String password;
//    定义一个私有字符串类型的字段，表示用户的密码

}
