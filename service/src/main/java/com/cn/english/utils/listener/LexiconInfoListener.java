package com.cn.english.utils.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.cn.english.entity.ExcelLexiconInfoData;
import com.cn.english.entity.LexiconInfo;
import com.cn.english.service.LexiconInfoService;
import org.springframework.beans.BeanUtils;

/**
 * 监听器
 * 导入excel数据到数据库  一行一行读取
 */
public class LexiconInfoListener extends AnalysisEventListener<ExcelLexiconInfoData> {
    // 因为SubjectExcelListener不能交给spring进行管理, 需要自己new, 不能注入其它对象
    // 不能实现数据库操作
    private LexiconInfoService lexiconInfoService;

    public LexiconInfoListener() {
    }

    public LexiconInfoListener(LexiconInfoService lexiconInfoService) {
        this.lexiconInfoService = lexiconInfoService;
    }


    /**
     *  这个方法是有多少条数据,执行多少次 : 一行一行读取
     * @param excelEInfoData  一行数据
     * @param analysisContext
     */
    @Override
    public void invoke(ExcelLexiconInfoData excelEInfoData, AnalysisContext analysisContext) {
        LexiconInfo lexiconInfo = new LexiconInfo();
        BeanUtils.copyProperties(excelEInfoData, lexiconInfo);
        lexiconInfoService.save(lexiconInfo);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
