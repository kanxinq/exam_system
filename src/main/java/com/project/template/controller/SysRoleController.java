package com.project.template.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.common.PageVO;
import com.project.template.common.Result;
import com.project.template.entity.SysRole;
import com.project.template.utils.Utils;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@Controller
@RequestMapping("/sysRole")
public class SysRoleController extends BaseController{
//它继承自BaseController，可能用于获取一些通用的服务注入或共享一些通用的方法
    /**
     * 列表
     * @return
     */
    @ApiOperation(value = "用户列表", notes = "")
    @GetMapping("/list")
    public Result<List<SysRole>> list() {
        return new Result<>().success(sysRoleService.list());
    }
//方法返回一个封装了系统角色列表的统一响应结果对象，通过调用系统角色服务的list方法获取系统角色列表并封装到响应结果中
    /**
     * 分页查询
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public Result<PageVO<SysRole>> findPage(@RequestParam(defaultValue = "") String name,
                                           @RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        //查出的数据根据id降序排列
        wrapper.orderByDesc(SysRole::getId);
        //且支持用户名称模糊查询
        wrapper.like(StringUtils.isNotBlank(name), SysRole::getName, name);
        Page<SysRole> page = sysRoleService.page(new Page<>(pageNum,pageSize),wrapper);
        return new Result<>().success(new PageVO<>(page));
    }
//方法接收角色名称（用于模糊查询）、页码和每页条数作为请求参数。首先创建一个 Lambda 查询包装器，设置查询条件为根据角色 ID 降序排列，并在角色名称不为空时进行模糊查询。
// 然后进行分页查询，将查询结果封装到自定义的分页结果封装类PageVO中，并返回一个封装了分页结果的统一响应结果对象
    /**
     * 数据新增
     * @param notice
     * @return
     */
    @ApiOperation(value = "数据新增", notes = "数据新增")
    @PostMapping("/add")
    public Result add(@Validated @RequestBody SysRole notice) {
        sysRoleService.save(notice);
        return new Result<>().success();
    }
//方法接收一个系统角色实体对象作为请求体参数，通过调用系统角色服务的save方法保存系统角色数据，并返回一个封装了成功信息的统一响应结果对象
    /**
     * 数据更新
     * @param notice
     * @return
     */
    @ApiOperation(value = "数据更新", notes = "数据更新")
    @PutMapping("/update")
    public Result updateById(@Validated @RequestBody SysRole notice) {
        sysRoleService.updateById(notice);
        return new Result<>().success();
    }
//方法接收一个系统角色实体对象作为请求体参数，通过调用系统角色服务的updateById方法更新系统角色数据，并返回一个封装了成功信息的统一响应结果对象
    /**
     * 根据id删除
     * @param ids
     * @return
     */
    @ApiOperation(value = "数据根据id批量删除", notes = "数据根据id批量删除")
    @DeleteMapping("/delBatch")
    public Result delBatch(@RequestBody List<Integer> ids) {
        sysRoleService.removeByIds(ids);
        return new Result<>().success();
    }
//方法接收一个包含系统角色 ID 的列表作为请求体参数，通过调用系统角色服务的removeByIds方法批量删除系统角色数据，并返回一个封装了成功信息的统一响应结果对象
    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public Result<SysRole> getById(@RequestParam("id") Integer id) {
        SysRole notice = sysRoleService.getById(id);
        return new Result<>().success(notice);
    }
//方法接收系统角色 ID 作为请求参数，通过调用系统角色服务的getById方法根据 ID 查询系统角色数据，并返回一个封装了查询结果的统一响应结果对象
}
