package com.project.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.template.dto.SysUserDTO;
import com.project.template.entity.ExamQuestion;
import com.project.template.entity.Score;
import com.project.template.entity.SysUser;
import com.project.template.enums.RoleType;
import com.project.template.exception.CustomException;
import com.project.template.mapper.ExamQuestionMapper;
import com.project.template.service.ExamQuestionService;
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
 * 试题表 服务实现类
 * </p>
 */
@Service
public class ExamQuestionServiceImpl extends ServiceImpl<ExamQuestionMapper, ExamQuestion> implements ExamQuestionService {
//它继承自 MyBatis-Plus 的ServiceImpl类，并实现了ExamQuestionService接口。
// 这意味着它将提供ExamQuestionService接口中定义的方法的具体实现，并且使用ExamQuestionMapper来进行数据库操作
    @Resource
    private SysUserService userService;
//定义一个私有变量，用于存储系统用户服务的实例，以便在当前类的方法中调用系统用户相关的业务逻辑方法
    @Override
    public boolean save(ExamQuestion entity) {
//        实现了父类ServiceImpl中的save方法，用于保存试题实体到数据库中
        check(entity);
//        调用check方法对传入的试题实体进行检查，检查名称是否重复等
        return super.save(entity);
//        调用父类的save方法将试题实体保存到数据库中，并返回保存结果
    }

    @Override
    public boolean updateById(ExamQuestion entity) {
//        实现了父类ServiceImpl中的updateById方法，用于根据 ID 更新试题实体到数据库中
        check(entity);
//        调用check方法对传入的试题实体进行检查，检查名称是否重复等
        return super.updateById(entity);
//        调用父类的updateById方法根据 ID 更新试题实体到数据库中，并返回更新结果
    }

    private void check(ExamQuestion entity) {

    }


    @Override
    public boolean removeByIds(Collection<?> list) {
//        实现了父类ServiceImpl中的removeByIds方法，用于根据多个 ID 删除试题实体
        List<Integer> integerList = (List<Integer>) list;
//        将传入的集合对象强制转换为整数列表，因为预期传入的是一组试题的 ID 列表
        return super.removeByIds(integerList);
//        调用父类的removeByIds方法根据 ID 列表删除试题实体，并返回删除结果
    }

    @Override
    public Page<ExamQuestion> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
//        实现了ExamQuestionService接口中的page方法，用于分页查询试题数据
        LambdaQueryWrapper<ExamQuestion> wrapper = new LambdaQueryWrapper<>();
//        创建一个 Lambda 查询包装器
        wrapper.orderByDesc(ExamQuestion::getId);
//        设置查询条件为根据 ID 降序排列

        if (ObjectUtils.isNotEmpty(query.get("name"))) {
//            如果查询参数中的名称不为空，则设置查询条件为根据名称进行模糊查询
            wrapper.like(ExamQuestion::getName, query.get("name"));
        }

        //老师只能看到自己的
        SysUserDTO currentUser = UserThreadLocal.getCurrentUser();
//        通过UserThreadLocal.getCurrentUser方法获取当前登录用户的信息，存储在SysUserDTO对象currentUser中
        if(RoleType.TEACHER.getKey().equals(currentUser.getRoleType())){
//            如果当前用户的角色是教师，则设置查询条件为只查询当前教师用户创建的试题
            wrapper.eq(ExamQuestion::getUserId,currentUser.getId());
        }
        Page<ExamQuestion> page = page(new Page<>(pageNum, pageSize), wrapper);
//        调用page方法进行分页查询，传入分页参数和查询包装器，得到分页查询结果


        page.getRecords().forEach(v->{
//            对分页查询结果中的每个试题进行遍历
            SysUser user = userService.getById(v.getUserId());
//            通过调用userService.getById方法，根据试题中的用户 ID 获取对应的用户信息
            if(user!=null){
//                如果用户信息不为null，则将试题中的用户 ID 替换为用户的用户名，以便在返回结果中显示创建试题的用户名称
                v.setUser(user.getUsername());
            }
        });
        return page;
//        返回分页查询结果
    }
}
