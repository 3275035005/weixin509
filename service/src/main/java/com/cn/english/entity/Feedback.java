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
 * 意见反馈表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 反馈用户id
     */
    private String userId;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 回复状态 0未回复 1已回复
     */
    private String status;

    /**
     * 回复内容
     */
    private String replyContent;

    /**
     * 反馈时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    /**
     * 反馈用户姓名
     */
    @TableField(exist = false)
    private String userName;

}
