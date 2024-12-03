package com.cn.english.mapper;

import com.cn.english.entity.Magazine;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 外刊信息表 Mapper 接口
 * </p>
 */
public interface MagazineMapper extends BaseMapper<Magazine> {

    List<Magazine> pageQuery(Magazine data);
}
