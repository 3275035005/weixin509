package com.cn.english.service;

import com.cn.english.entity.Feedback;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.english.utils.page.PageResult;

/**
 * <p>
 * 意见反馈表 服务类
 * </p>
 */
public interface FeedbackService extends IService<Feedback> {

    PageResult pageQuery(int page, int limit, Feedback data);
}
