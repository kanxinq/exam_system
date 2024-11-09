package com.project.template.mapper;

import com.project.template.entity.Score;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 学生成绩表 Mapper 接口
 * </p>
 */
@Mapper
public interface ScoreMapper extends BaseMapper<Score> {

}
