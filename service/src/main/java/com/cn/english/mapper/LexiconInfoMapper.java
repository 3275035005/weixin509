package com.cn.english.mapper;

import com.cn.english.entity.LexiconInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 词库单词信息 Mapper 接口
 * </p>
 */
public interface LexiconInfoMapper extends BaseMapper<LexiconInfo> {

    List<LexiconInfo> pageQuery(LexiconInfo data);

    List<LexiconInfo> getLexiconInfoByLexiconId(@Param("id") String id,@Param("userId")  String userId);


}
