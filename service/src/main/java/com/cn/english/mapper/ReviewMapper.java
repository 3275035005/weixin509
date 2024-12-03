package com.cn.english.mapper;

import com.cn.english.entity.Review;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 单词复习 Mapper 接口
 * </p>
 */
public interface ReviewMapper extends BaseMapper<Review> {

    List<Review> getReviewByUserId(String id);

    Review getReviewByReview(Review reviewNew);

    Map<String, Object> getSumZzt(@Param("userId") String userId, @Param("date") String date);

}
