package com.cn.english.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.english.entity.SysUser;
import com.cn.english.service.SysUserService;
import com.cn.english.utils.page.PageResult;
import com.cn.english.utils.response.R;
import com.cn.english.utils.utils.AceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/sys-user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 分页条件查询
     * @param page   当前页码
     * @param limit 每页的大小
     * @param data 封装查询条件数据
     * @return
     */
    @PostMapping("pageQuery/{page}/{limit}")
    public R getPageData(
            @PathVariable int page,
            @PathVariable int limit,
            @RequestBody SysUser data){
        PageResult pageResult = sysUserService.pageQuery(page, limit, data);
        return R.ok().data("rows",pageResult);
    }
    /**
     * 修改操作
     * @param data
     * @return
     */
    @PutMapping("update")
    public R update(@RequestBody SysUser data){
        sysUserService.updateById(data);
        return R.ok();
    }

    /**
     * 新增操作
     * @param data
     * @return
     */
    @PostMapping("insert")
    public R insert(@RequestBody SysUser data){
        data.setUserType("2");
        data.setPassword( AceUtils.string2MD5(data.getPassword()));
        sysUserService.save(data);
        return R.ok();
    }

    /**
     * 删除操作
     * @param id
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    public R deleteById(@PathVariable String id){
        sysUserService.removeById(id);
        return R.ok();
    }


    /**
     * 查询所有用户
     * @return
     */
    @GetMapping("getUserAll")
    public R getUserAll(){
        List<SysUser> list = sysUserService.list(null);
        return R.ok().data("row", list);
    }


    /**
     * 重置密码 默认密码666666
     * @param id
     * @return
     */
    @PostMapping("resetPassword/{id}")
    public R resetPassword(@PathVariable String id){
        SysUser pUser = sysUserService.getById(id);
        // MD5单向加密
        pUser.setPassword(AceUtils.string2MD5("666666"));
        sysUserService.updateById(pUser);
        return R.ok();
    }


    /**
     * 修改密码
     */
    @PostMapping("updatePassword")
    public R updatePassword(@RequestBody SysUser data){
        SysUser pUser = sysUserService.getById(data.getId());

        // 加密旧密码
        String oldPassword = AceUtils.string2MD5(data.getOldPassword());

        if(!oldPassword.equals(pUser.getPassword())){
            return R.error("旧密码不正确");
        }
        // 更新密码
        pUser.setPassword(AceUtils.string2MD5(data.getPassword()));
        sysUserService.updateById(pUser);
        return R.ok();
    }

    /**
     * 通过用户id获取用户信息
     */
    @GetMapping("getUserInfo")
    public R getUserInfo(String userId){
        SysUser pUser = sysUserService.getById(userId);
        return R.ok().data("data", pUser);
    }

    /**
     * 登录功能
     */
    @PostMapping("login")
    public R login(@RequestBody SysUser data){
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        LambdaQueryWrapper<SysUser> lambda = qw.lambda();
        lambda.eq(SysUser::getUsername, data.getUsername());
        SysUser pUser = sysUserService.getOne(qw);
        if(pUser == null){
            return R.error("账号不存在");
        }
        String string2MD5Password = AceUtils.string2MD5(data.getPassword());
        // 判断密码
        if(!string2MD5Password.equals(pUser.getPassword())){
            return R.error("密码不正确");
        }

        String status = pUser.getStatus();

        // 判断账号状态
        if(!"1".equals(status)){
            return R.error("账号已被禁用");
        }


        return R.ok().data("token",pUser.getId());
    }


    /**
     * 通过用户id获取信息
     * @return
     */
    @GetMapping("info")
    public R info(String token){
        SysUser eUser = sysUserService.getById(token);
        if("1".equals(eUser.getUserType())){
            eUser.setRoles(new String[]{"admin"});
        }
        return R.ok().data("data", eUser);
    }
}

