package com.project.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.template.entity.Banner;
import com.project.template.entity.Notice;
import com.project.template.exception.CustomException;
import com.project.template.mapper.BannerMapper;
import com.project.template.service.BannerService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 轮播图 服务实现类
 * </p>
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {
//它继承自 MyBatis-Plus 的ServiceImpl类，并实现了BannerService接口。这意味着它将提供BannerService接口中定义的方法的具体实现，并且使用BannerMapper来进行数据库操作
    @Override
    public boolean save(Banner entity) {
//        实现了父类ServiceImpl中的save方法，用于保存轮播图实体到数据库中
        check(entity);
//        调用check方法对传入的轮播图实体进行检查，可能是检查名称是否重复等
        return super.save(entity);
//        调用父类的save方法将轮播图实体保存到数据库中，并返回保存结果
    }

    @Override
    public boolean updateById(Banner entity) {
//        实现了父类ServiceImpl中的updateById方法，用于根据 ID 更新轮播图实体到数据库中
        check(entity);
//        调用check方法对传入的轮播图实体进行检查，可能是检查名称是否重复等
        return super.updateById(entity);
//        调用父类的updateById方法根据 ID 更新轮播图实体到数据库中，并返回更新结果
    }

    private void check(Banner entity) {

    }


    @Override
    public boolean removeByIds(Collection<?> list) {
//        实现了父类ServiceImpl中的removeByIds方法，用于根据多个 ID 删除轮播图实体
        List<Integer> integerList = (List<Integer>) list;
//        将传入的集合对象强制转换为整数列表，因为预期传入的是一组轮播图的 ID 列表
        return super.removeByIds(integerList);
//        调用父类的removeByIds方法，传入转换后的整数列表，执行批量删除操作，并返回删除结果
    }

    @Override
    public Page<Banner> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
//        实现了BannerService接口中的page方法，用于分页查询轮播图数据
        LambdaQueryWrapper<Banner> wrapper = new LambdaQueryWrapper<>();
//        创建一个 Lambda 查询包装器
        wrapper.orderByDesc(Banner::getId);
//        设置查询条件为根据 ID 降序排列
        if (ObjectUtils.isNotEmpty(query.get("name"))) {
//            如果查询参数中的名称不为空，则设置查询条件为根据名称进行模糊查询
            wrapper.like(Banner::getName, query.get("name"));
        }
        return page(new Page<>(pageNum, pageSize), wrapper);
//        调用page方法进行分页查询，传入分页参数和查询包装器，返回分页查询结果
    }
}
