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
 * 外刊订阅表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Subscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 订阅用户id
     */
    private String userId;

    /**
     * 订阅外刊id
     */
    private String magazineId;

    /**
     * 订阅时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 外刊标题
     */
    @TableField(exist = false)
    private String magazineTitle;

    /**
     * 外刊封面
     */
    @TableField(exist = false)
    private String image;

}
