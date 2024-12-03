package com.cn.english.service;

import com.cn.english.entity.StudyPlan;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学习计划 服务类
 * </p>
 */
public interface StudyPlanService extends IService<StudyPlan> {

    /**
     *  通过用户id查询学习计划
     * @param id 用户id
     * @return
     */
    List<StudyPlan> getStudyPlanByUserId(String id);

    /**
     * 查询学习计划和题库是否已被创建
     * @param studyPlan
     * @return
     */
    StudyPlan getThisDateIs(StudyPlan studyPlan);


    /**
     * 通过计划类型和用户id和词库id查询数据
     * @param type
     * @param userId
     * @param lexiconInfoId
     * @return
     */
    StudyPlan getTypeStudyPlanByUserIdAndLexiconId(String type, String userId, String lexiconInfoId);
}
