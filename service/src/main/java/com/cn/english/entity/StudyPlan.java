package com.cn.english.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学习计划
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StudyPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 计划名称
     */
    private String title;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 计划完成单词
     */
    private Integer completeWord;

    /**
     * 计划类型 1周计划 2日计划
     */
    private String type;

    /**
     * 状态 0未完成 1已完成
     */
    private String status;

    /**
     * 词库id
     */
    private String lexiconId;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 已完成学习
     */
    @TableField(exist = false)
    private Integer count;
}
