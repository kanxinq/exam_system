package com.project.template.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.template.common.PageVO;
import com.project.template.common.Result;
import com.project.template.entity.ExamQuestion;
import com.project.template.service.ExamQuestionService;
import com.project.template.utils.UserThreadLocal;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 试题表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/examQuestion")
public class ExamQuestionController {
    @Resource
    private ExamQuestionService service;
    /**
     * 列表
     */
    @ApiOperation(value = "列表", notes = "列表")
    @GetMapping("/list")
    public Result<List<ExamQuestion>> list() {
        return new Result<>().success(service.list());
    }
//方法返回一个封装了试题表列表的统一响应结果对象，通过调用服务层的list方法获取试题表列表并封装到响应结果中
    /**
     * 分页查询
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page")
    public Result<PageVO<ExamQuestion>> findPage(@RequestParam Map<String, Object> query,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10") Integer pageSize) {
        Page<ExamQuestion> page = service.page(query, pageNum, pageSize);
        return new Result<>().success(new PageVO<>(page));
    }
//方法接收查询参数、页码和每页条数作为请求参数，通过调用服务层的page方法进行分页查询，将查询结果封装到自定义的分页结果封装类PageVO中，并返回一个封装了分页结果的统一响应结果对象
    /**
     * 数据新增
     */
    @ApiOperation(value = "数据新增", notes = "数据新增")
    @PostMapping("/add")
    public Result add(@Validated @RequestBody ExamQuestion entity) {
        entity.setUserId(UserThreadLocal.getCurrentUser().getId());
        service.save(entity);
        return new Result<>().success();
    }
//方法接收一个试题表实体对象作为请求体参数，首先通过调用工具类UserThreadLocal的getCurrentUser()方法获取当前用户信息，
// 并设置到试题表实体的userId属性中，然后调用服务层的save方法保存试题表数据，并返回一个封装了成功信息的统一响应结果对象
    /**
     * 数据更新
     */
    @ApiOperation(value = "数据更新", notes = "数据更新")
    @PutMapping("/update")
    public Result updateById(@Validated @RequestBody ExamQuestion entity) {
        service.updateById(entity);
        return new Result<>().success();
    }
//方法接收一个试题表实体对象作为请求体参数，通过调用服务层的updateById方法更新试题表数据，并返回一个封装了成功信息的统一响应结果对象
    /**
     * 根据id删除
     */
    @ApiOperation(value = "数据根据id批量删除", notes = "数据根据id批量删除")
    @DeleteMapping("/delBatch")
    public Result delBatch(@RequestBody List<Integer> ids) {
        service.removeByIds(ids);
        return new Result<>().success();
    }
//方法接收一个包含试题表 ID 的列表作为请求体参数，通过调用服务层的removeByIds方法批量删除试题表数据，并返回一个封装了成功信息的统一响应结果对象
    /**
     * 根据id查询
     */
    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @GetMapping("/getById")
    public Result<ExamQuestion> getById(@RequestParam("id") Integer id) {
        ExamQuestion entity = service.getById(id);
        return new Result<>().success(entity);
    }
//    方法接收试题表 ID 作为请求参数，通过调用服务层的getById方法根据 ID 查询试题表数据，并返回一个封装了查询结果的统一响应结果对象
    @GetMapping("/getQuestion")
    public Result getQuestion(@RequestParam("ids") Integer[] ids) {
        List<ExamQuestion> list = new ArrayList<>();
        for (Integer id : ids) {
            ExamQuestion byId = service.getById(id);
            list.add(byId);
        }
        return new Result<>().success(list);
    }
//方法接收一组试题表 ID 作为请求参数，遍历这些 ID，通过调用服务层的getById方法逐个获取试题表数据，并将结果存储在一个列表中，最后返回一个封装了查询结果列表的统一响应结果对象
}
