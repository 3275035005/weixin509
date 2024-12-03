package com.cn.english.service;

import com.cn.english.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.english.utils.page.PageResult;

/**
 * <p>
 * 用户表 服务类
 * </p>
 */
public interface SysUserService extends IService<SysUser> {

    PageResult pageQuery(int page, int limit, SysUser data);
}
