package com.cn.english.mapper;

import com.cn.english.entity.Lexicon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 词库信息 Mapper 接口
 * </p>
 */
public interface LexiconMapper extends BaseMapper<Lexicon> {

    List<Lexicon> pageQuery(Lexicon data);


    long getCount(Lexicon data);

}
