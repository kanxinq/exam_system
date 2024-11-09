package com.project.template.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.entity.News;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;
/**
 * <p>
 * 轮播图 服务类
 * </p>
 */
public interface NewsService extends IService<News> {
        Page<News> page(Map<String, Object> query, Integer pageNum, Integer pageSize);
}
