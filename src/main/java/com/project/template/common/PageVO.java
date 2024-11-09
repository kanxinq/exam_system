package com.project.template.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

@Data
//这是 Lombok 提供的注解，它会自动为类生成常用的方法，如 getter、setter、equals、hashCode 和 toString 等，简化了 Java 代码的编写
public class PageVO<T> {
//    定义了一个泛型类PageVO，其中T是一个泛型类型参数，表示这个类可以用于存储不同类型的数据
    List<T> list;
//    一个泛型列表，用于存储查询结果的数据列表
    long total;
//    一个长整型变量，用于存储查询结果的总记录数
    public PageVO(Page<T> page){
//        这是一个接受Page对象作为参数的构造方法。Page是 MyBatis-Plus 框架中的分页对象，通常用于数据库查询分页
        this.setList(page.getRecords());
//        从传入的分页对象中获取查询结果记录列表，并设置到当前对象的list成员变量中
        this.setTotal(page.getTotal());
//        从传入的分页对象中获取总记录数，并设置到当前对象的total成员变量中
    }
}
//这个类的作用是将 MyBatis-Plus 的分页结果封装成一个更通用的对象，方便在业务层进行数据传递和处理。它可以让调用者更方便地获取分页查询的结果数据列表和总记录数，而不需要直接操作 MyBatis-Plus 的分页对象