package com.cn.english.controller;


import com.cn.english.entity.Feedback;
import com.cn.english.service.FeedbackService;
import com.cn.english.utils.page.PageResult;
import com.cn.english.utils.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 意见反馈表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService service;

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
            @RequestBody Feedback data){
        PageResult pageResult = service.pageQuery(page, limit, data);
        return R.ok().data("rows",pageResult);
    }
    /**
     * 反馈回复
     * @param feedback
     * @return
     */
    @PostMapping("sendFeedback")
    public R sendFeedback(@RequestBody Feedback feedback){
        feedback.setStatus("1");
        service.updateById(feedback);
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

