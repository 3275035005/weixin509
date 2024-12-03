package com.cn.english.mapper;

import com.cn.english.entity.Feedback;
import com.cn.english.entity.MagazineClassified;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 */
public interface MagazineClassifiedMapper extends BaseMapper<MagazineClassified> {

    List<MagazineClassified> pageQuery(MagazineClassified data);

    List<MagazineClassified> getMagazineClassifiedBySumNumber();

}
