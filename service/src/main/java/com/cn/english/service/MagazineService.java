package com.cn.english.service;

import com.cn.english.entity.Magazine;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.english.utils.page.PageResult;

/**
 * <p>
 * 外刊信息表 服务类
 * </p>
 */
public interface MagazineService extends IService<Magazine> {

    PageResult pageQuery(int page, int limit, Magazine data);
}
