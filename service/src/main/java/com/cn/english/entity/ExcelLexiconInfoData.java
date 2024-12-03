package com.cn.english.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelLexiconInfoData {

    /**
     * 词库Id
     */
    @ExcelProperty(index = 0)
    private String lexiconId;


    /**
     * 单词名称
     */
    @ExcelProperty(index = 1)
    private String wordName;


    /**
     * 音标
     */
    @ExcelProperty(index = 2)
    private String soundmark;


    /**
     * 释义1
     */
    @ExcelProperty(index = 3)
    private String paraphrase1;

    /**
     * 释义2
     */
    @ExcelProperty(index = 4)
    private String paraphrase2;

    /**
     * 短语
     */
    @ExcelProperty(index = 5)
    private String phrase;


}
