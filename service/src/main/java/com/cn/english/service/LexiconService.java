package com.cn.english.service;

import com.cn.english.entity.Lexicon;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.english.utils.page.PageResult;

import java.util.List;

/**
 * <p>
 * 词库信息 服务类
 * </p>
 */
public interface LexiconService extends IService<Lexicon> {

    PageResult pageQuery(int page, int limit, Lexicon data);


}
