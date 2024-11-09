package com.project.template.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysUserDTO {

    @ApiModelProperty("id")
    private Integer id;
//定义一个私有整数类型的字段，表示用户的 ID
    @ApiModelProperty("用户名")
    private String username;
//定义一个私有字符串类型的字段，表示用户的用户名
    @ApiModelProperty("角色类型")
    private String roleType;
//定义一个私有字符串类型的字段，表示用户的角色类型
}
