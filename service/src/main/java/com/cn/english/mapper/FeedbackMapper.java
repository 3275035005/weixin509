package com.cn.english.mapper;

import com.cn.english.entity.Feedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 意见反馈表 Mapper 接口
 * </p>
 */
public interface FeedbackMapper extends BaseMapper<Feedback> {

    List<Feedback> pageQuery(Feedback data);
}
