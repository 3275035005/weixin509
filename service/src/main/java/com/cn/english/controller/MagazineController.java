package com.cn.english.controller;


import com.cn.english.entity.Magazine;
import com.cn.english.service.MagazineService;
import com.cn.english.utils.page.PageResult;
import com.cn.english.utils.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 外刊信息表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/magazine")
public class MagazineController {


    @Autowired
    private MagazineService service;

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
            @RequestBody Magazine data){
        PageResult pageResult = service.pageQuery(page, limit, data);
        return R.ok().data("rows",pageResult);
    }
    /**
     * 修改数据
     * @param data
     * @return
     */
    @PutMapping("update")
    public R update(@RequestBody Magazine data){
        service.updateById(data);
        return R.ok();
    }

    /**
     * 新增数据
     * @param data
     * @return
     */
    @PostMapping("insert")
    public R insert(@RequestBody Magazine data){
        service.save(data);
        return R.ok();
    }

    /**
     * 删除操作
     * @param id
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    public R deleteById(@PathVariable String id){
        service.removeById(id);
        return R.ok();
    }
}

