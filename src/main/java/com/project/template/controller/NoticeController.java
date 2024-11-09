package com.project.template.controller;



import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.common.PageVO;
import com.project.template.common.Result;
import com.project.template.entity.Notice;
import com.project.template.entity.SysUser;
import com.project.template.utils.Utils;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 通知 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/notice")
public class NoticeController extends BaseController {
//    定义一个名为NoticeController的控制器类，它继承自BaseController，可能用于获取一些通用的服务注入或共享一些通用的方法

    /**
     * 列表
     * @return
     */
    @ApiOperation(value = "用户列表", notes = "")
    @GetMapping("/list")
    public Result<List<Notice>> list() {
        return new Result<>().success(noticeService.list());
    }
//方法返回一个封装了通知列表的统一响应结果对象，通过调用通知服务的list方法获取通知列表并封装到响应结果中
    /**
     * 分页查询
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public Result<PageVO<Notice>> findPage(@RequestParam(defaultValue = "") String name,
                                           @RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        //查出的数据根据id降序排列
        wrapper.orderByDesc(Notice::getId);
        //且支持用户名称模糊查询
        wrapper.like(StringUtils.isNotBlank(name), Notice::getName, name);
        Page<Notice> page = noticeService.page(new Page<>(pageNum,pageSize),wrapper);
        return new Result<>().success(new PageVO<>(page));
    }
//方法接收通知名称（用于模糊查询）、页码和每页条数作为请求参数。首先创建一个 Lambda 查询包装器，设置查询条件为根据通知 ID 降序排列，并在通知名称不为空时进行模糊查询。
// 然后进行分页查询，将查询结果封装到自定义的分页结果封装类PageVO中，并返回一个封装了分页结果的统一响应结果对象
    /**
     * 数据新增
     * @param notice
     * @return
     */
    @ApiOperation(value = "数据新增", notes = "数据新增")
    @PostMapping("/add")
    public Result add(@Validated @RequestBody Notice notice) {
        notice.setUser(Utils.getUser().getUsername());
        notice.setCreateTime(new Date());
        noticeService.save(notice);
        return new Result<>().success();
    }
//方法接收一个通知实体对象作为请求体参数。设置通知的创建时间为当前时间，并从工具类中获取当前用户的用户名设置到通知实体的user属性中。
// 然后调用通知服务的save方法保存通知数据，并返回一个封装了成功信息的统一响应结果对象
    /**
     * 数据更新
     * @param notice
     * @return
     */
    @ApiOperation(value = "数据更新", notes = "数据更新")
    @PutMapping("/update")
    public Result updateById(@Validated @RequestBody Notice notice) {
        noticeService.updateById(notice);
        return new Result<>().success();
    }
//方法接收一个通知实体对象作为请求体参数，通过调用通知服务的updateById方法更新通知数据，并返回一个封装了成功信息的统一响应结果对象
    /**
     * 根据id删除
     * @param ids
     * @return
     */
    @ApiOperation(value = "数据根据id批量删除", notes = "数据根据id批量删除")
    @DeleteMapping("/delBatch")
    public Result delBatch(@RequestBody List<Integer> ids) {
        noticeService.removeByIds(ids);
        return new Result<>().success();
    }
//方法接收一个包含通知 ID 的列表作为请求体参数，通过调用通知服务的removeByIds方法批量删除通知数据，并返回一个封装了成功信息的统一响应结果对象
    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public Result<Notice> getById(@RequestParam("id") Integer id) {
        Notice notice = noticeService.getById(id);
        return new Result<>().success(notice);
    }
//    方法接收通知 ID 作为请求参数，通过调用通知服务的getById方法根据 ID 查询通知数据，并返回一个封装了查询结果的统一响应结果对象
}
