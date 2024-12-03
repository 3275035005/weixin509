package com.cn.english.service;

import com.cn.english.entity.LexiconInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.english.utils.page.PageResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 词库单词信息 服务类
 * </p>
 */
public interface LexiconInfoService extends IService<LexiconInfo> {

    PageResult pageQuery(int page, int limit, LexiconInfo data);

    void batchImport(MultipartFile file, LexiconInfoService service);

    List<LexiconInfo> getLexiconInfoByLexiconId(String id, String userId);

}
