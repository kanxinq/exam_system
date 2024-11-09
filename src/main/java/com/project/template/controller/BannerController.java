package com.project.template.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.common.PageVO;
import com.project.template.common.Result;
import com.project.template.entity.Banner;
import com.project.template.service.BannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 轮播图 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/banner")
public class BannerController {
    @Resource
    private BannerService service;
//    定义一个轮播图服务接口的实例变量，用于调用服务层的方法
    /**
     * 列表
     */
    @ApiOperation(value = "列表", notes = "列表")
    @GetMapping("/list")
    public Result<List<Banner>> list() {
        return new Result<>().success(service.list());
    }
//    方法返回一个封装了轮播图列表的统一响应结果对象，通过调用服务层的list方法获取轮播图列表并封装到响应结果中

    /**
     * 分页查询
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public Result<PageVO<Banner>> findPage(@RequestParam Map<String, Object> query,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Banner> page = service.page(query, pageNum, pageSize);
        return new Result<>().success(new PageVO<>(page));
    }
//    方法接收查询参数、页码和每页条数作为请求参数，通过调用服务层的page方法进行分页查询，将查询结果封装到自定义的分页结果封装类PageVO中，并返回一个封装了分页结果的统一响应结果对象

    /**
     * 数据新增
     */
    @ApiOperation(value = "数据新增", notes = "数据新增")
    @PostMapping("/add")
    public Result add(@Validated @RequestBody Banner entity) {
        service.save(entity);
        return new Result<>().success();
    }
//方法接收一个轮播图实体对象作为请求体参数，通过调用服务层的save方法保存轮播图数据，并返回一个封装了成功信息的统一响应结果对象
    /**
     * 数据更新
     */
    @ApiOperation(value = "数据更新", notes = "数据更新")
    @PutMapping("/update")
    public Result updateById(@Validated @RequestBody Banner entity) {
        service.updateById(entity);
        return new Result<>().success();
    }
//方法接收一个轮播图实体对象作为请求体参数，通过调用服务层的updateById方法更新轮播图数据，并返回一个封装了成功信息的统一响应结果对象
    /**
     * 根据id删除
     */
    @ApiOperation(value = "数据根据id批量删除", notes = "数据根据id批量删除")
    @DeleteMapping("/delBatch")
    public Result delBatch(@RequestBody List<Integer> ids) {
        service.removeByIds(ids);
        return new Result<>().success();
    }
//方法接收一个包含轮播图 ID 的列表作为请求体参数，通过调用服务层的removeByIds方法批量删除轮播图数据，并返回一个封装了成功信息的统一响应结果对象
    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @GetMapping("/getById")
    public Result<Banner> getById(@RequestParam("id") Integer id) {
        Banner entity = service.getById(id);
        return new Result<>().success(entity);
    }
//    方法接收轮播图 ID 作为请求参数，通过调用服务层的getById方法根据 ID 查询轮播图数据，并返回一个封装了查询结果的统一响应结果对象
}
