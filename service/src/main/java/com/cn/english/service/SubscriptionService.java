package com.cn.english.service;

import com.cn.english.entity.Subscription;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 外刊订阅表 服务类
 * </p>
 */
public interface SubscriptionService extends IService<Subscription> {

    /**
     * 查询我的订阅外刊
     * @param userId 用户id
     * @param classifiedId 外刊分类id
     * @return
     */
    List<Subscription> getSubscriptionMagazineByUserId(String userId, String classifiedId);

}
