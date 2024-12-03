package com.cn.english.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.english.entity.Lexicon;
import com.cn.english.service.LexiconService;
import com.cn.english.utils.page.PageResult;
import com.cn.english.utils.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 词库信息 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/lexicon")
public class LexiconController {

    @Autowired
    private LexiconService service;

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
            @RequestBody Lexicon data){
        PageResult pageResult = service.pageQuery(page, limit, data);
        return R.ok().data("rows",pageResult);
    }
    /**
     * 修改数据
     * @param data
     * @return
     */
    @PutMapping("update")
    public R update(@RequestBody Lexicon data){
        service.updateById(data);
        return R.ok();
    }

    /**
     * 新增数据
     * @param data
     * @return
     */
    @PostMapping("insert")
    public R insert(@RequestBody Lexicon data){
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
     * 查询所有词库
     */
    @GetMapping("getLexiconAll")
    public R getLexiconAll(){
        List<Lexicon> list = service.list(null);

        return R.ok().data("list", list);
    }
}

