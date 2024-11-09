package com.project.template.controller;

import com.project.template.service.BannerService;
import com.project.template.service.NoticeService;
import com.project.template.service.SysRoleService;
import com.project.template.service.SysUserService;

import com.project.template.service.impl.SysRoleServiceImpl;
import com.project.template.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BaseController {
    @Resource
    SysUserService sysUserService;
//定义一个系统用户服务接口的实例变量，用于调用系统用户服务的方法
    @Resource
    NoticeService noticeService;
//定义一个通知服务接口的实例变量，用于调用通知服务的方法
    @Resource
    SysRoleService sysRoleService;
//定义一个系统角色服务接口的实例变量，用于调用系统角色服务的方法
    @Resource
    BannerService bannerService;
//    定义一个轮播图服务接口的实例变量，用于调用轮播图服务的方法
}
