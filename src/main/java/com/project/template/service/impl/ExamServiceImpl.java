package com.project.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.template.dto.SysUserDTO;
import com.project.template.entity.Banner;
import com.project.template.entity.Exam;
import com.project.template.entity.Score;
import com.project.template.entity.SysUser;
import com.project.template.exception.CustomException;
import com.project.template.mapper.ExamMapper;
import com.project.template.service.ExamService;
import com.project.template.service.ScoreService;
import com.project.template.service.SysUserService;
import com.project.template.utils.UserThreadLocal;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 考试表 服务实现类
 * </p>
 */
@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements ExamService {
//    它继承自 MyBatis-Plus 的ServiceImpl类，并实现了ExamService接口。
//    这意味着它将提供ExamService接口中定义的方法的具体实现，并且使用ExamMapper来进行数据库操作

    @Resource
    private ScoreService scoreService;
//    定义一个私有变量，用于存储成绩服务的实例，以便在当前类的方法中调用成绩相关的业务逻辑方法

    @Resource
    private SysUserService userService;
//    定义一个私有变量，用于存储系统用户服务的实例，以便在当前类的方法中调用系统用户相关的业务逻辑方法

    @Override
    public boolean save(Exam entity) {
//        实现了父类ServiceImpl中的save方法，用于保存考试实体到数据库中
        check(entity);
//        调用check方法对传入的考试实体进行检查，检查名称是否重复等
        return super.save(entity);
//        调用父类的save方法将考试实体保存到数据库中，并返回保存结果
    }

    @Override
    public boolean updateById(Exam entity) {
//        实现了父类ServiceImpl中的updateById方法，用于根据 ID 更新考试实体到数据库中
        check(entity);
//        调用check方法对传入的考试实体进行检查，检查名称是否重复等
        return super.updateById(entity);
//        调用父类的updateById方法根据 ID 更新考试实体到数据库中，并返回更新结果
    }

    private void check(Exam entity) {

    }


    @Override
    public boolean removeByIds(Collection<?> list) {
//        实现了父类ServiceImpl中的removeByIds方法，用于根据多个 ID 删除考试实体
        List<Integer> integerList = (List<Integer>) list;
//        将传入的集合对象强制转换为整数列表，因为预期传入的是一组考试的 ID 列表
        return super.removeByIds(integerList);
//        调用父类的removeByIds方法根据 ID 列表删除考试实体，并返回删除结果
    }

    @Override
    public Page<Exam> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
//        实现了ExamService接口中的page方法，用于分页查询考试数据
        LambdaQueryWrapper<Exam> wrapper = new LambdaQueryWrapper<>();
//        创建一个 Lambda 查询包装器
        wrapper.orderByDesc(Exam::getId);
//        设置查询条件为根据 ID 降序排列

        if (ObjectUtils.isNotEmpty(query.get("name"))) {
//            如果查询参数中的名称不为空，则设置查询条件为根据名称进行模糊查询
            wrapper.like(Exam::getName, query.get("name"));
        }

        SysUserDTO currentUser = UserThreadLocal.getCurrentUser();
//        通过UserThreadLocal.getCurrentUser方法获取当前登录用户的信息，存储在SysUserDTO对象currentUser中
        if ("TEACHER".equals(currentUser.getRoleType())) {
            wrapper.eq(Exam::getUserId, currentUser.getId());
        }
//        如果当前用户的角色是教师，则设置查询条件为只查询当前教师用户创建的考试
        Page<Exam> page = page(new Page<>(pageNum, pageSize), wrapper);
//        调用page方法进行分页查询，传入分页参数和查询包装器，得到分页查询结果。
        List<Exam> list = page.getRecords();
//        获取分页查询结果中的考试列表

        //查找用户id 和作业id相同的数据
        list.forEach(item->{
//            对考试列表中的每个考试进行遍历。
            SysUser user = userService.getById(item.getUserId());
//            通过调用userService.getById方法，根据考试中的用户 ID 获取对应的用户信息
            if(user!=null){
//                如果用户信息不为null，则将考试中的用户 ID 替换为用户的用户名，以便在返回结果中显示创建考试的用户名称
                item.setUser(user.getUsername());
            }
            LambdaQueryWrapper<Score> scoreLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            创建一个用于查询成绩的 Lambda 查询包装器
            scoreLambdaQueryWrapper.eq(Score::getUserId,currentUser.getId())
                    .eq(Score::getExamId,item.getId())
                    .last("limit 1");
//            设置查询条件为根据当前用户 ID 和考试 ID 查询成绩，并且限制查询结果为一条记录
            Score one = scoreService.getOne(scoreLambdaQueryWrapper);
//            通过调用scoreService.getOne方法查询是否存在当前用户对应这个考试的成绩
            if(one!=null){
//                如果存在成绩，则将考试的状态设置为 “已考试”
                item.setIsExam("已考试");
            }else{
//                如果不存在成绩，则将考试的状态设置为 “立即考试”
                item.setIsExam("立即考试");
            }
        });
        page.setRecords(list);
//        将处理后的考试列表设置回分页对象中
        return page;
    }
}
