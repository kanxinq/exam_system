package com.project.template.utils;

import cn.hutool.crypto.SecureUtil;
import com.project.template.dto.SysUserDTO;

import java.util.HashMap;
import java.util.Random;

public class Utils {

    private static char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
//声明了一个私有静态字符数组hex，并初始化它包含了十六进制的所有字符。这个数组将用于生成随机盐值，通过随机选择其中的字符来构建盐值字符串
    /**
     * 自定义简单生成盐，是一个随机生成的长度为16的字符串，每一个字符是随机的十六进制字符
     */
    public static String salt() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(16);
//        创建了一个StringBuilder对象sb，并指定其初始容量为 16。StringBuilder用于高效地构建字符串，比直接使用字符串拼接操作更节省内存和性能
        for (int i = 0; i < sb.capacity(); i++) {
            //        开始一个循环，循环次数为StringBuilder的容量，即 16 次。这个循环将用于逐个添加随机的十六进制字符到StringBuilder中，以构建盐值字符串
            sb.append(hex[random.nextInt(16)]);
//            在每次循环中，通过random.nextInt(16)生成一个 0 到 15 之间的随机整数，作为hex数组的索引，
//            然后将对应的十六进制字符添加到StringBuilder中。这样就随机生成了一个十六进制字符并添加到盐值字符串中
        }

        return sb.toString();
    }
    public static void main(String[] args) {
        String salt = salt();
//        调用salt方法生成一个盐值，并将其赋值给salt变量。这行代码演示了如何使用salt方法获取盐值
        System.out.println(salt);
        //2e400f7fe59147f895338b4ee9011239
        System.out.println(SecureUtil.md5("getToken" + salt));
//        调用SecureUtil的md5方法计算字符串 "getToken" 与生成的盐值拼接后的 MD5 值，并打印输出。
//        这行代码展示了如何使用 Hutool 的加密工具计算 MD5 值，可能用于密码加密或其他需要生成哈希值的场景，结合盐值可以增加密码的安全性
    }

    public static SysUserDTO getUser(){
        SysUserDTO currentUser = UserThreadLocal.getCurrentUser();
//        通过调用UserThreadLocal.getCurrentUser()方法获取当前用户信息，并将其赋值给currentUser变量。
//        假设UserThreadLocal类用于在当前线程中存储和获取用户相关的数据，这样可以在整个请求处理过程中方便地获取当前用户信息，而不需要在每个方法中传递用户对象作为参数
        return currentUser;

    }
//定义了一个公共静态方法getUser，用于获取当前用户的信息。这个方法可能在整个项目中用于获取当前登录用户的上下文信息，以便进行权限验证、数据个性化处理等操作
}

