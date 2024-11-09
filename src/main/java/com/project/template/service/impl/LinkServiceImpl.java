package com.project.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.template.entity.Exam;
import com.project.template.entity.Link;
import com.project.template.exception.CustomException;
import com.project.template.mapper.LinkMapper;
import com.project.template.service.LinkService;
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
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
//    它继承自 MyBatis-Plus 的ServiceImpl类，并实现了LinkService接口。这意味着它将提供LinkService接口中定义的方法的具体实现，并且使用LinkMapper来进行数据库操作

    @Override
    public boolean save(Link entity) {
//        实现了父类ServiceImpl中的save方法，用于保存链接实体到数据库中
        check(entity);
//        调用check方法对传入的链接实体进行检查，检查名称是否重复等
        return super.save(entity);
//        调用父类的save方法将链接实体保存到数据库中，并返回保存结果
    }

    @Override
    public boolean updateById(Link entity) {
//        实现了父类ServiceImpl中的updateById方法，用于根据 ID 更新链接实体到数据库中
        check(entity);
//        调用check方法对传入的链接实体进行检查，检查名称是否重复等
        return super.updateById(entity);
//        调用父类的updateById方法根据 ID 更新链接实体到数据库中
    }

    private void check(Link entity) {
    }


    @Override
    public boolean removeByIds(Collection<?> list) {
//        实现了父类ServiceImpl中的removeByIds方法，用于根据多个 ID 删除链接实体
        List<Integer> integerList = (List<Integer>) list;
//        将传入的集合对象强制转换为整数列表，因为预期传入的是一组链接的 ID 列表
        return super.removeByIds(integerList);
//        调用父类的removeByIds方法根据 ID 列表删除链接实体，并返回删除结果
    }

    @Override
    public Page<Link> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
//        实现了LinkService接口中的page方法，用于分页查询链接数据
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
//        创建一个 Lambda 查询包装器
        wrapper.orderByDesc(Link::getId);
//        设置查询条件为根据 ID 降序排列
        if (ObjectUtils.isNotEmpty(query.get("name"))) {
//            如果查询参数中的名称不为空，则设置查询条件为根据名称进行模糊查询
            wrapper.like(Link::getName, query.get("name"));
        }

        return page(new Page<>(pageNum, pageSize), wrapper);
//        调用page方法进行分页查询，传入分页参数和查询包装器，返回分页查询结果
    }
}
