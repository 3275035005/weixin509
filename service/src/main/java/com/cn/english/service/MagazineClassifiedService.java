package com.cn.english.service;

import com.cn.english.entity.MagazineClassified;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.english.utils.page.PageResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface MagazineClassifiedService extends IService<MagazineClassified> {

    PageResult pageQuery(int page, int limit, MagazineClassified data);

    /**
     * 统计外刊分类的外刊数量
     * @return
     */
    List<MagazineClassified> getMagazineClassifiedBySumNumber();

}
