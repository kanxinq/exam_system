package com.project.template.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;
/**
 * <p>
 * 轮播图 服务类
 * </p>
 */
public interface BannerService extends IService<Banner> {
//        该接口继承自 MyBatis-Plus 的IService接口，泛型参数为Banner，表示这个服务接口是针对Banner实体类进行操作的
        Page<Banner> page(Map<String, Object> query, Integer pageNum, Integer pageSize);
//        方法用于分页查询轮播图数据。
//接受一个包含查询参数的Map<String, Object>类型的参数query，可以用于传递各种查询条件
//还接受页码pageNum和每页条数pageSize两个整数参数，用于指定分页查询的参数。
//返回一个Page<Banner>类型的对象，其中包含查询到的轮播图数据以及分页信息，总条数、当前页码等
}
