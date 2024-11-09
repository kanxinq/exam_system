package com.project.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.template.entity.News;
import com.project.template.entity.Notice;
import com.project.template.entity.SysUser;
import com.project.template.exception.CustomException;
import com.project.template.mapper.NewsMapper;
import com.project.template.service.NewsService;
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
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {
//    它继承自 MyBatis-Plus 的ServiceImpl类，并实现了NewsService接口。这意味着它将提供NewsService接口中定义的方法的具体实现，并且使用NewsMapper来进行数据库操作

    @Override
    public boolean save(News entity) {
//        实现了父类ServiceImpl中的save方法，用于保存新闻实体到数据库中
        check(entity);
//        调用check方法对传入的新闻实体进行检查，可能是检查名称是否重复等
        entity.setViews(0);
//        设置新闻的浏览量为 0
        return super.save(entity);
//        调用父类的save方法将新闻实体保存到数据库中，并返回保存结果
    }

    @Override
    public boolean updateById(News entity) {
//        实现了父类ServiceImpl中的updateById方法，用于根据 ID 更新新闻实体到数据库中
        check(entity);
//        调用check方法对传入的新闻实体进行检查，可能是检查名称是否重复等
        return super.updateById(entity);
//        调用父类的updateById方法根据 ID 更新新闻实体到数据库中，并返回更新结果
    }

    private void check(News entity) {

    }


    @Override
    public boolean removeByIds(Collection<?> list) {
//        实现了父类ServiceImpl中的removeByIds方法，用于根据多个 ID 删除资讯实体
        List<Integer> integerList = (List<Integer>) list;
//        将传入的集合对象强制转换为整数列表，因为预期传入的是一组新闻的 ID 列表
        return super.removeByIds(integerList);
//        调用父类的removeByIds方法根据 ID 列表删除新闻实体，并返回删除结果
    }

    @Override
    public Page<News> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
//        实现了NewsService接口中的page方法，用于分页查询资讯数据
        LambdaQueryWrapper<News> wrapper = new LambdaQueryWrapper<>();
//        创建一个 Lambda 查询包装器
        wrapper.orderByDesc(News::getId);
//        设置查询条件为根据 ID 降序排列

        if (ObjectUtils.isNotEmpty(query.get("name"))) {
//            如果查询参数中的名称不为空，则设置查询条件为根据名称进行模糊查询
            wrapper.like(News::getName, query.get("name"));
        }
        return page(new Page<>(pageNum, pageSize), wrapper);
//        调用page方法进行分页查询，传入分页参数和查询包装器，返回分页查询结果
    }
}
