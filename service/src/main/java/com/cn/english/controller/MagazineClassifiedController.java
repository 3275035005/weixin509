package com.cn.english.controller;


import com.cn.english.entity.MagazineClassified;
import com.cn.english.service.MagazineClassifiedService;
import com.cn.english.utils.page.PageResult;
import com.cn.english.utils.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  外刊分类
 * </p>
 */
@RestController
@RequestMapping("/magazine-classified")
public class MagazineClassifiedController {

    @Autowired
    private MagazineClassifiedService service;

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
            @RequestBody MagazineClassified data){
        PageResult pageResult = service.pageQuery(page, limit, data);
        return R.ok().data("rows",pageResult);
    }
    /**
     * 修改数据
     * @param data
     * @return
     */
    @PutMapping("update")
    public R update(@RequestBody MagazineClassified data){
        service.updateById(data);
        return R.ok();
    }

    /**
     * 新增数据
     * @param data
     * @return
     */
    @PostMapping("insert")
    public R insert(@RequestBody MagazineClassified data){
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


    /**
     * 查询所有分类
     * @param
     * @return
     */
    @GetMapping("getClassifiedAll")
    public R getClassifiedAll(){
        List<MagazineClassified> list = service.list(null);
        return R.ok().data("row", list);
    }

}

