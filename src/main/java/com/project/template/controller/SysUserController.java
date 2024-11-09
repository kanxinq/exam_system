package com.project.template.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.common.PageVO;
import com.project.template.common.Result;
import com.project.template.dto.*;
import com.project.template.entity.Score;
import com.project.template.entity.SysUser;
import com.project.template.enums.RoleType;
import com.project.template.utils.Utils;
import com.project.template.vo.SysUserListVO;
import com.project.template.vo.SysUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 */
@Api(value = "用户模块", tags = "用户模块")

@RestController
@RequestMapping("/sysUser")
public class SysUserController extends BaseController {
//它继承自BaseController，可能用于获取一些通用的服务注入或共享一些通用的方法
    /**
     * 列表
     * @return
     */
    @ApiOperation(value = "用户列表", notes = "")
    @GetMapping("/list")
    public Result<List<SysUser>> list() {
        return new Result<>().success(sysUserService.list());
    }
//方法返回一个封装了系统用户列表的统一响应结果对象，通过调用系统用户服务的list方法获取系统用户列表并封装到响应结果中
    /**
     * 分页查询
     * @param user
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public Result<PageVO<SysUser>> findPage(@RequestParam(defaultValue = "") String user,
                                            @RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
//        创建一个用于构建查询条件的LambdaQueryWrapper对象，用于查询SysUser实体类

        //查出的数据根据id降序排列
        wrapper.orderByDesc(SysUser::getId);

        //且支持用户名称模糊查询
//        如果user参数不为空，则进行用户名称的模糊查询，根据SysUser实体的username属性进行模糊匹配
        wrapper.like(StringUtils.isNotBlank(user), SysUser::getUsername, user);
        if (RoleType.TEACHER.getKey().equals(Utils.getUser().getRoleType())) {
            wrapper.eq(SysUser::getRoleType, RoleType.USER.getKey());
        }
//        如果当前用户的角色是老师（通过比较获取到的当前用户角色类型与RoleType.TEACHER.getKey()是否相等来判断），
//        则设置查询条件为只查询角色类型为用户（RoleType.USER.getKey()）的用户
        Page<SysUser> page = sysUserService.page(new Page<>(pageNum,pageSize),wrapper);
//        调用sysUserService的page方法进行分页查询，传入页码、每页条数和查询条件包装器，得到一个包含SysUser实体的分页结果对象
        Page<SysUserListVO> voPage = new Page<>();
//        创建一个新的分页对象用于存储转换后的结果
        BeanUtil.copyProperties(page,voPage);
//        将原始分页结果的属性复制到新的分页对象中
        List<SysUser> records = page.getRecords();
//        获取原始分页结果中的记录列表
        List<SysUserListVO> listVOList = new ArrayList<>();
//        创建一个用于存储转换后数据的列表
        records.forEach(item->{
            SysUserListVO sysUserListVO = new SysUserListVO();
            BeanUtil.copyProperties(item,sysUserListVO);
            listVOList.add(sysUserListVO);
        });
//        通过遍历原始记录列表，将每个SysUser对象复制到一个新的SysUserListVO对象中，并添加到转换后的列表中
        voPage.setRecords(listVOList);
//        将转换后的记录列表设置到新的分页对象中
        return new Result<>().success(new PageVO<>(voPage));
//        创建一个新的Result对象，并将转换后的分页结果封装在PageVO对象中，作为成功的响应返回
    }
//方法接收用户名称（用于模糊查询）、页码和每页条数作为请求参数。首先创建一个 Lambda 查询包装器，设置查询条件为根据用户 ID 降序排列，并在用户名称不为空时进行模糊查询。
// 如果当前用户是教师角色，则只查询普通用户（RoleType.USER）。然后进行分页查询，将查询结果复制到自定义的视图对象列表中，并返回一个封装了分页结果的统一响应结果对象
    /**
     * 数据新增
     * @param user
     * @return
     */
    @ApiOperation(value = "数据新增", notes = "数据新增")
    @PostMapping("/add")
    public Result add(@Validated @RequestBody SysUser user) {
        sysUserService.insertOrUpdate(user);
        return new Result<>().success();
    }
//方法接收一个系统用户实体对象作为请求体参数，通过调用系统用户服务的insertOrUpdate方法进行插入或更新操作，并返回一个封装了成功信息的统一响应结果对象
    /**
     * 数据更新
     * @param user
     * @return
     */
    @ApiOperation(value = "数据更新", notes = "数据更新")
    @PutMapping("/update")
    public Result updateById(@Validated @RequestBody SysUser user) {
        sysUserService.insertOrUpdate(user);
        return new Result<>().success();
    }
//方法接收一个系统用户实体对象作为请求体参数，通过调用系统用户服务的insertOrUpdate方法进行插入或更新操作，并返回一个封装了成功信息的统一响应结果对象
    /**
     * 根据id删除
     * @param ids
     * @return
     */
    @ApiOperation(value = "数据根据id批量删除", notes = "数据根据id批量删除")
    @DeleteMapping("/delBatch/{ids}")
    public Result delBatch(@PathVariable List<Integer> ids) {
        sysUserService.removeByIds(ids);
        return new Result<>().success();
    }
//方法接收一个包含系统用户 ID 的列表作为路径变量，通过调用系统用户服务的removeByIds方法批量删除系统用户数据，并返回一个封装了成功信息的统一响应结果对象
    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public Result<SysUserVO> getById(@RequestParam("id") Integer id) {
        SysUser byId = sysUserService.getById(id);
        SysUserVO userVO = new SysUserVO();
        BeanUtils.copyProperties(byId, userVO);
        return new Result<>().success(userVO);
    }
//方法接收系统用户 ID 作为请求参数，通过查询获取系统用户实体对象，然后将其属性复制到系统用户视图对象中，并返回一个封装了视图对象的统一响应结果对象
    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result login(@Validated @RequestBody UserLoginDTO userLoginDTO) {
        return new Result<>().success(sysUserService.login(userLoginDTO));
    }
//方法接收用户登录数据传输对象作为请求体参数，通过调用系统用户服务的login方法进行登录操作，并返回一个封装了登录结果的统一响应结果对象
    /**
     * 用户注册
     * @param userRegisterDTO
     * @return
     */
    @PostMapping("/register")
    public Result register(@Validated @RequestBody UserRegisterDTO userRegisterDTO) {
        sysUserService.register(userRegisterDTO);
        return new Result<>().success();
    }
//方法接收用户注册数据传输对象作为请求体参数，通过调用系统用户服务的register方法进行注册操作，并返回一个封装了成功信息的统一响应结果对象
    /**
     * 修改密码
     * @param dto
     * @return
     */
    @PutMapping("/editPassword")
    public Result editPassword(@RequestBody EditPasswordDTO dto){
        sysUserService.editPassword(dto);
        return new Result<>().success();
    }
//方法接收修改密码的数据传输对象作为请求体参数，通过调用系统用户服务的editPassword方法进行密码修改操作，并返回一个封装了成功信息的统一响应结果对象
    /**
     * 数据更新
     * @param user
     * @return
     */
    @ApiOperation(value = "密码重置", notes = "密码重置")
    @PutMapping("/resetPassword")
    public Result resetPassword(@RequestBody SysUser user) {
        return new Result<>().success(sysUserService.resetPassword(user));
    }
//方法接收系统用户实体对象作为请求体参数，通过调用系统用户服务的resetPassword方法进行密码重置操作，并返回一个封装了成功信息的统一响应结果对象
}
