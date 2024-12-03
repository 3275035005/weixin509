package com.cn.english.mapper;

import com.cn.english.entity.Subscription;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 外刊订阅表 Mapper 接口
 * </p>
 */
public interface SubscriptionMapper extends BaseMapper<Subscription> {

    List<Subscription> getSubscriptionMagazineByUserId(@Param("userId") String userId, @Param("classifiedId") String classifiedId);
}
