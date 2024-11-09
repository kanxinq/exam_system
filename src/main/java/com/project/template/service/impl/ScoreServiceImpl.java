package com.project.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.template.entity.Exam;
import com.project.template.entity.Link;
import com.project.template.entity.Score;
import com.project.template.entity.SysUser;
import com.project.template.enums.RoleType;
import com.project.template.exception.CustomException;
import com.project.template.mapper.ScoreMapper;
import com.project.template.service.ScoreService;
import com.project.template.service.SysUserService;
import com.project.template.utils.Utils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学生成绩表 服务实现类
 * </p>
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {
//    它继承自 MyBatis-Plus 的ServiceImpl类，并实现了ScoreService接口。这意味着它将提供ScoreService接口中定义的方法的具体实现，并且使用ScoreMapper来进行数据库操作

    @Resource
    private SysUserService userService;
//    定义一个私有变量，用于存储系统用户服务的实例，以便在当前类的方法中调用系统用户相关的业务逻辑方法

    @Override
    public boolean save(Score entity) {
//        实现了父类ServiceImpl中的save方法，用于保存成绩实体到数据库中
        check(entity);
//        调用check方法对传入的成绩实体进行检查，可能是检查名称是否重复等。
        return super.save(entity);
//        调用父类的save方法将成绩实体保存到数据库中，并返回保存结果
    }

    @Override
    public boolean updateById(Score entity) {
//        实现了父类ServiceImpl中的updateById方法，用于根据 ID 更新成绩实体到数据库中
        check(entity);
//        调用check方法对传入的成绩实体进行检查，检查名称是否重复等
        return super.updateById(entity);
//        调用父类的updateById方法根据 ID 更新成绩实体到数据库中，并返回更新结果
    }

    private void check(Score entity) {

    }


    @Override
    public boolean removeByIds(Collection<?> list) {
//        实现了父类ServiceImpl中的removeByIds方法，用于根据多个 ID 删除成绩实体
        List<Integer> integerList = (List<Integer>) list;
//将传入的集合对象强制转换为整数列表，因为预期传入的是一组成绩的 ID 列表
        return super.removeByIds(integerList);
//        调用父类的removeByIds方法根据 ID 列表删除成绩实体，并返回删除结果
    }

    @Override
    public Page<Score> page(Map<String, Object> query, Integer pageNum, Integer pageSize) {
//        实现了ScoreService接口中的page方法，用于分页查询成绩数据
        LambdaQueryWrapper<Score> wrapper = new LambdaQueryWrapper<>();
//        创建一个 Lambda 查询包装器
        if (ObjectUtils.isNotEmpty(query.get("name"))) {
//            如果查询参数中的名称不为空，则设置查询条件为根据名称进行模糊查询
            wrapper.like(Score::getName, query.get("name"));
        }

        wrapper.orderByDesc(Score::getId);
//        设置查询条件为根据 ID 降序排列
        if (RoleType.TEACHER.getKey().equals(Utils.getUser().getRoleType())) {
//            如果当前用户的角色是教师，则设置查询条件为只查询当前教师用户相关的成绩
            wrapper.eq(Score::getTeacherId, Utils.getUser().getId());
        }
        if (RoleType.USER.getKey().equals(Utils.getUser().getRoleType())) {
//            如果当前用户的角色是学生，则设置查询条件为只查询当前学生用户相关的成绩
            wrapper.eq(Score::getUserId, Utils.getUser().getId());
        }
        Page<Score> page = page(new Page<>(pageNum, pageSize), wrapper);
//        调用page方法进行分页查询，传入分页参数和查询包装器，得到分页查询结果
        page.getRecords().forEach(item->{
//            对分页查询结果中的每个成绩进行遍历
            SysUser user = userService.getById(item.getUserId());
//            通过调用userService.getById方法，根据成绩中的用户 ID 获取对应的用户信息
            if(user!=null){
//                如果用户信息不为null，则将成绩中的用户 ID 替换为用户的用户名，以便在返回结果中显示学生的用户名
                item.setUser(user.getUsername());
            }
        });
        return page;
//        返回分页查询结果
    }
}
