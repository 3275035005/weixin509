package com.cn.english.service;

import com.cn.english.entity.Review;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 单词复习 服务类
 * </p>
 */
public interface ReviewService extends IService<Review> {

    List<Review> getReviewByUserId(String id);

    Review getReviewByReview(Review reviewNew);

    Map<String, Object> getZzt(String id);
}
