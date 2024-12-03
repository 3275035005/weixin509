package com.cn.english.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 词库单词信息
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LexiconInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 单词名称
     */
    private String wordName;

    /**
     * 词库id
     */
    private String lexiconId;

    /**
     * 音标
     */
    private String soundmark;


    /**
     * 释义1
     */
    private String paraphrase1;

    /**
     * 释义2
     */
    private String paraphrase2;

    /**
     * 短语
     */
    private String phrase;

    /**
     * 语音路径
     */
    private String voice;


    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 词库名称
     */
    @TableField(exist = false)
    private String title;

    /**
     * 生词ID
     */
    @TableField(exist = false)
    private String vocabularyId;

    /**
     * 查询是否为文字
     */
    @TableField(exist = false)
    private boolean flag;

    /**
     * 文字翻译结果
     */
    @TableField(exist = false)
    private List<String> translation;

}
