package com.cn.english.mapper;

import com.cn.english.entity.StudyPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学习计划 Mapper 接口
 * </p>
 */
public interface StudyPlanMapper extends BaseMapper<StudyPlan> {

    List<StudyPlan> getStudyPlanByUserId(String id);

    StudyPlan getThisDateIs(StudyPlan studyPlan);


    StudyPlan getTypeStudyPlanByUserIdAndLexiconId(@Param("type") String type,@Param("userId") String userId,@Param("lexiconId")  String lexiconId);

}
