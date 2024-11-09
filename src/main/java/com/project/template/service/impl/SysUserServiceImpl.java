package com.project.template.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.project.template.dto.EditPasswordDTO;
import com.project.template.dto.SysUserDTO;
import com.project.template.dto.UserLoginDTO;
import com.project.template.dto.UserRegisterDTO;
import com.project.template.entity.SysUser;
import com.project.template.enums.StateType;
import com.project.template.exception.CustomException;
import com.project.template.mapper.SysUserMapper;
import com.project.template.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.template.utils.JwtUtils;
import com.project.template.utils.RoleMenuUtils;
import com.project.template.utils.UserThreadLocal;
import com.project.template.utils.Utils;
import com.project.template.vo.SysUserLoginVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
//它继承自 MyBatis-Plus 的ServiceImpl类，并实现了SysUserService接口。这意味着它将提供SysUserService接口中定义的方法的具体实现，并且使用SysUserMapper来进行数据库操作
    @Value("${resetPassword}")
//    从配置文件中读取名为resetPassword的属性值，并注入到resetPassword变量中。这个属性可能是用于重置密码的默认密码或者其他与密码重置相关的配置
    private String resetPassword;
//定义一个私有字符串变量，用于存储从配置文件中读取的密码重置相关的值

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
//        实现了SysUserService接口中的register方法，用于处理用户注册逻辑
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        //创建一个 Lambda 查询包装器，用于构建查询条件
        wrapper.eq(SysUser::getUsername, userRegisterDTO.getUsername())
                .last("limit 1");
//        设置查询条件为根据用户名查询，并且限制查询结果为一条记录。这是为了检查用户名是否已经存在

        SysUser userInfo = getOne(wrapper);
//        方法查询数据库中是否存在具有相同用户名的用户。如果存在，userInfo将不为null
        if (userInfo != null) {//有值 提示用户名已重复
            throw new CustomException("用户名称重复");
        }
//        如果查询到了用户信息，说明用户名已重复，抛出CustomException异常，提示用户名称重复
        SysUser user = new SysUser();
//        创建一个新的SysUser对象，用于存储注册用户的信息
        BeanUtil.copyProperties(userRegisterDTO, user);
//        使用BeanUtil.copyProperties方法将userRegisterDTO中的属性复制到user对象中
        String salt = Utils.salt();
//        调用Utils.salt方法生成一个随机盐值
        user.setSalt(salt);
//        将生成的盐值设置到user对象的salt属性中
        //密码加密
        user.setPassword(SecureUtil.md5(user.getPassword() + salt));
        // 用户注册需将权限设置为普通用户
        user.setRoleType("USER");
        //账号默认为启用
        user.setState(StateType.ENABLE);
//        将用户的状态设置为StateType.ENABLE，表示账号默认为启用状态。
        user.setCreateTime(new Date());
//        设置用户的创建时间为当前时间
        saveOrUpdate(user);
//        调用saveOrUpdate方法将用户信息保存到数据库中
    }

    @Override
    public void editPassword(EditPasswordDTO dto) {
//        实现了SysUserService接口中的editPassword方法，用于处理用户修改密码的逻辑
        SysUserDTO user = UserThreadLocal.getCurrentUser();
//        通过UserThreadLocal.getCurrentUser方法获取当前登录用户的信息，存储在SysUserDTO对象user中
        //先查询用户
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
//        创建一个 Lambda 查询包装器
        wrapper.eq(SysUser::getId, user.getId())
                .last("limit 1");
//        设置查询条件为根据用户 ID 查询，并且限制查询结果为一条记录。这是为了获取当前用户的详细信息
        SysUser sysUser = getOne(wrapper);
//        通过调用getOne(wrapper)方法查询数据库中当前用户的信息，存储在SysUser对象sysUser中
        if (sysUser != null) {
            LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
//            创建另一个 Lambda 查询包装器
            queryWrapper.eq(SysUser::getPassword, SecureUtil.md5(dto.getPassword() + sysUser.getSalt()))
                    .last("limit 1");
//            设置查询条件为根据加密后的旧密码查询，并且限制查询结果为一条记录。这是为了验证用户输入的旧密码是否正确
            SysUser currentUser = getOne(queryWrapper);
//            通过调用getOne(queryWrapper)方法查询数据库中具有相同加密旧密码的用户信息，存储在SysUser对象currentUser中
            if (currentUser != null) {
//                如果查询结果不为null，说明旧密码验证通过
                String salt = Utils.salt();//重新生成盐值
                currentUser.setSalt(salt);
//                将新生成的盐值设置到currentUser对象的salt属性中
                currentUser.setPassword(SecureUtil.md5(dto.getNewPassword() + salt));
//                使用SecureUtil.md5方法对用户输入的新密码和新生成的盐值进行加密，并将加密后的新密码设置到currentUser对象的password属性中
                saveOrUpdate(currentUser);
//                调用saveOrUpdate方法更新用户的密码信息到数据库中
            } else {
                throw new CustomException("修改失败");
//                如果旧密码验证不通过，抛出CustomException异常，提示修改失败
            }
        } else {
            throw new CustomException("用户未查询到");
        }
    }

    @Override
    public SysUserLoginVO login(UserLoginDTO userLoginDTO) {
//        实现了SysUserService接口中的login方法，用于处理用户登录逻辑
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
//        创建一个 Lambda 查询包装器
        wrapper.eq(SysUser::getUsername, userLoginDTO.getUsername())
                .last("limit 1");
//        设置查询条件为根据用户名查询，并且限制查询结果为一条记录。这是为了获取用户的详细信息
        SysUser userInfo = getOne(wrapper);
//        通过调用getOne(wrapper)方法查询数据库中具有相同用户名的用户信息，存储在SysUser对象userInfo中
        if (userInfo != null) {
//            如果查询结果不为null，说明用户存在
            String salt = userInfo.getSalt();
//            获取用户的盐值

            if (!SecureUtil.md5(userLoginDTO.getPassword() + salt).equals(userInfo.getPassword())) {
                throw new CustomException("请检查用户名密码是否正确");
            }
//使用SecureUtil.md5方法对用户输入的密码和盐值进行加密，然后与数据库中存储的密码进行比较。如果不相等，抛出CustomException异常，提示用户名或密码错误
            //检查用户状态是否是启用
            if (!(StateType.ENABLE.toString().equals(userInfo.getState().getName()))) {
                throw new CustomException("当前用户已经被禁用");
            }
//检查用户的状态是否为启用状态。如果不是，抛出CustomException异常，提示当前用户已被禁用

            //生成jwt
            String token = JwtUtils.generateToken(userInfo);
//            调用JwtUtils.generateToken(userInfo)方法生成 JSON Web Token（JWT），并存储在token变量中
            SysUserLoginVO sysUserLoginVO = new SysUserLoginVO();
//            创建一个SysUserLoginVO对象，用于存储登录后的用户信息和令牌
            BeanUtil.copyProperties(userInfo, sysUserLoginVO);
//            使用BeanUtil.copyProperties方法将userInfo中的属性复制到sysUserLoginVO对象中
            sysUserLoginVO.setToken(token);
//            将生成的 JWT 设置到sysUserLoginVO对象的token属性中

            //生成菜单
            sysUserLoginVO.setRoleMenu(RoleMenuUtils.getRoleMenu(sysUserLoginVO.getRoleType()));
//通过调用RoleMenuUtils.getRoleMenu(sysUserLoginVO.getRoleType())方法获取当前用户的菜单列表，并设置到sysUserLoginVO对象的roleMenu属性中
            return sysUserLoginVO;
//            返回sysUserLoginVO对象，其中包含用户登录后的信息和 JWT
        } else {
            throw new CustomException("请检查用户名密码是否正确");
//            如果用户不存在，抛出CustomException异常，提示用户名或密码错误
        }
    }

    @Override
    public void insertOrUpdate(SysUser user) {
//        实现了SysUserService接口中的insertOrUpdate方法，用于插入或更新用户信息
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
//        创建一个 Lambda 查询包装器
        wrapper.eq(SysUser::getUsername, user.getUsername());
//        设置查询条件为根据用户名查询
        wrapper.ne(user.getId() != null, SysUser::getId, user.getId());
//        如果用户 ID 不为null，设置查询条件为排除当前用户 ID 的其他具有相同用户名的用户。这是为了检查用户名是否重复
        if (count(wrapper) > 0) {
            throw new CustomException("用户名重复");
//            如果查询结果数量大于 0，说明用户名重复，抛出CustomException异常，提示用户名重复
        }
        if (user.getId() == null) {
//            如果用户 ID 为null，说明是插入操作
            String salt = Utils.salt();
//            生成一个随机盐值
            user.setSalt(salt);
//            将生成的盐值设置到user对象的salt属性中

            //密码加密
            user.setPassword(SecureUtil.md5(user.getPassword() + salt));
        }
        saveOrUpdate(user);
//        调用saveOrUpdate方法将用户信息保存到数据库中
    }

    @Override
    public String resetPassword(SysUser user) {
//        实现了SysUserService接口中的resetPassword方法，用于重置用户密码
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
//        创建一个 Lambda 查询包装器
        wrapper.eq(SysUser::getUsername, user.getUsername());
//        设置查询条件为根据用户名查询
        wrapper.eq(SysUser::getId, user.getId());
//        设置查询条件为根据用户 ID 查询
        if (count(wrapper) > 0) {
//            如果查询结果数量大于 0，说明用户存在
            SysUser sysUser = getOne(wrapper);
//            通过调用getOne(wrapper)方法查询数据库中用户的信息，存储在SysUser对象sysUser中
            String salt = Utils.salt();
//            生成一个新的盐值
            sysUser.setSalt(salt);
//            将新生成的盐值设置到sysUser对象的salt属性中

            //密码加密
            sysUser.setPassword(SecureUtil.md5(resetPassword + salt));
            updateById(sysUser);
//            调用updateById方法更新用户的密码信息到数据库中
            return "重置成功，重置密码为：" + resetPassword;
//            返回重置密码成功的提示信息和重置后的密码
        } else {
            throw new CustomException("未找到该用户");
//            如果用户不存在，抛出CustomException异常，提示未找到该用户
        }
    }
}
