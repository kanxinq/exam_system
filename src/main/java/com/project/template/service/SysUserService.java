package com.project.template.service;

import com.project.template.dto.EditPasswordDTO;
import com.project.template.dto.UserLoginDTO;
import com.project.template.dto.UserRegisterDTO;
import com.project.template.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.template.vo.SysUserLoginVO;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 */
public interface SysUserService extends IService<SysUser> {
//该接口继承自 MyBatis-Plus 的IService接口，泛型参数为SysUser，表示这个服务接口是针对SysUser实体类进行操作的
    void register(UserRegisterDTO userRegisterDTO);
//方法用于处理用户注册逻辑，接受一个UserRegisterDTO类型的参数，表示用户注册时提交的数据
    void editPassword(EditPasswordDTO dto);
//方法用于编辑用户密码，接受一个EditPasswordDTO类型的参数
    SysUserLoginVO login(UserLoginDTO userLoginDTO);
//方法用于处理用户登录逻辑，接受一个UserLoginDTO类型的参数，表示用户登录时提交的用户名和密码等信息
    void insertOrUpdate(SysUser user);
//方法用于插入或更新用户信息，接受一个SysUser类型的参数，表示用户的实体对象
    String resetPassword(SysUser user);
//    方法用于重置用户密码，接受一个SysUser类型的参数
}
