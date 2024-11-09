package com.project.template.vo;

import com.project.template.enums.StateType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SysUserListVO {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("创建时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("个人简介")
    private String content;

    @ApiModelProperty("头像")
    private String img;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("电话")
    private String mobile;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("角色类型")
    private String roleType;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("状态")
    private StateType state;


}
//这个类的主要作用是作为系统用户列表信息的载体，在不同组件之间传递数据，并且通过注解提供了清晰的文档描述，方便开发过程中的代码理解和 API 文档生成。
// 例如，在查询用户列表的业务逻辑中，从数据库获取的用户数据可以被转换为SysUserListVO对象，然后传递给前端进行展示，或者在其他需要处理用户列表信息的地方使用这个对象来进行数据的传递和操作
