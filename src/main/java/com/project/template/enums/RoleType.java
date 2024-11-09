package com.project.template.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum RoleType {

    ADMIN("ADMIN", "超级管理员"),
//    定义一个枚举常量ADMIN，并传入两个参数，分别对应数据库存储的值和前端显示的值
    USER("USER", "普通用户"),
    TEACHER("TEACHER", "老师");

    /**
     * 存到数据库的值
     */
    @EnumValue
//    使用 MyBatis-Plus 的枚举值注解，表明这个字段是枚举类型在数据库中存储的值
    private String key;
//定义一个私有字符串类型的字段，表示枚举值在数据库中的存储值
    /**
     * 前端显示的值
     */
    @JsonValue
//    使用 Jackson 的 JSON 值注解，表明这个字段是枚举类型在 JSON 序列化时输出的值
    private String name;
//定义一个私有字符串类型的字段，表示枚举值在前端显示的值

    RoleType(String key, String name) {
//        定义一个构造方法，用于初始化枚举常量的key和name字段
        this.key = key;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
//重写Object类的toString方法，返回枚举常量的前端显示值name

}
