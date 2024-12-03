package com.cn.english.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.english.entity.*;
import com.cn.english.service.*;
import com.cn.english.utils.response.R;
import com.cn.english.utils.utils.AceUtils;
import com.cn.english.utils.utils.AuthV3Util;
import com.cn.english.utils.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * <p>
 *  对小程序提供后端接口
 * </p>
 */
@RestController
@RequestMapping("/wx")
public class WxController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private MagazineService magazineService;

    @Autowired
    private MagazineClassifiedService magazineClassifiedService;

    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private VocabularyService vocabularyService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private LexiconService lexiconService;

    @Autowired
    private LexiconInfoService lexiconInfoService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private StudyPlanService studyPlanService;


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
        if(!"2".equals(pUser.getUserType())){
            return R.error("请登录学生账号");
        }
        String status = pUser.getStatus();

        // 判断账号状态
        if(!"1".equals(status)){
            return R.error("账号已被禁用");
        }

        return R.ok().data("token",pUser.getId());
    }


    /**
     * 注册功能
     * @param data
     * @return
     */
    @PostMapping("register")
    public R register(@RequestBody SysUser data){
        // MD5单向加密
        data.setPassword(AceUtils.string2MD5(data.getPassword()));
        data.setStatus("1");
        data.setUserType("2");
        sysUserService.save(data);
        return R.ok();
    }

    /**
     * 个人信息查询
     */
    @GetMapping("getUserInfo")
    public R getUserInfo(String id){
        SysUser ttUser = sysUserService.getById(id);
        return R.ok().data("row", ttUser);
    }

    /**
     * 个人信息修改
     */
    @PostMapping("updateUserInfo")
    public R updateUserInfo(@RequestBody SysUser ttUser){
        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.eq("id", ttUser.getId());
        if(!StringUtils.isEmpty(ttUser.getPassword())){
            ttUser.setPassword(AceUtils.string2MD5(ttUser.getPassword()));
        }
        sysUserService.update(ttUser, qw);
        return R.ok().data("row", ttUser);
    }

    /**
     * 意见反馈
     */
    @PostMapping("sendFeedback")
    public R sendFeedback(@RequestBody Feedback feedback){
        feedback.setStatus("0");
        feedbackService.save(feedback);
        return R.ok();
    }

    /**
     * 查询我的意见反馈
     */
    @PostMapping("getFeedback")
    public R getFeedback(@RequestBody Feedback feedback){
        LambdaQueryWrapper<Feedback> qw =  new LambdaQueryWrapper<>();
        qw.eq(Feedback::getUserId,feedback.getUserId());
        qw.eq(Feedback::getStatus,feedback.getStatus());
        qw.orderByDesc(Feedback::getCreateTime);
        List<Feedback> list = feedbackService.list(qw);
        return R.ok().data("list", list);
    }

    /**
     * 查询反馈详情
     */
    @GetMapping("getFeedbackById/{id}")
    public R getFeedbackById(@PathVariable String id){
        Feedback feedback = feedbackService.getById(id);
        return R.ok().data("row", feedback);
    }


    /**
     * 查询外刊分类, 统计分类外刊数量
     */
    @GetMapping("getMagazineClassified")
    public R getMagazineClassifiedBySumNumber(){
        List<MagazineClassified> list =  magazineClassifiedService.getMagazineClassifiedBySumNumber();
        return R.ok().data("list", list);
    }

    /**
     * 查询外刊分类id 查询外刊列表
     * @param id 分类id
     * @return
     */
    @GetMapping("getMagazineListByClassifiedId/{id}")
    public R getMagazineListByClassifiedId(@PathVariable String id){
        List<Magazine> list = magazineService.list(new QueryWrapper<Magazine>().eq("classified_id", id).orderByDesc("create_time"));
        return R.ok().data("list", list);
    }

    /**
     * 查询外刊id 查询外刊详情
     * @param id 分类id
     * @return
     */
    @GetMapping("getMagazineById/{id}/{userId}")
    public R getMagazineById(@PathVariable String id, @PathVariable String userId){
        Magazine magazine = magazineService.getById(id);

        // 查询是否收藏了外刊
        Subscription subscription = subscriptionService.getOne(new QueryWrapper<Subscription>()
                .eq("magazine_id", id)
                .eq("user_id", userId));
        if(subscription != null){
            magazine.setSubscriptionId(subscription.getId());
        }
        return R.ok().data("list", magazine);
    }

    /**
     * 查询所有外刊分类
     * @param
     * @return
     */
    @GetMapping("getClassifiedAll")
    public R getClassifiedAll(){
        List<MagazineClassified> list = magazineClassifiedService.list(null);
        return R.ok().data("row", list);
    }
    /**
     * 订阅外刊
     * @param
     * @return
     */
    @PostMapping("subscriptionMagazine")
    public R subscriptionMagazine(@RequestBody Subscription subscription){
        subscriptionService.save(subscription);
        return R.ok();
    }

    /**
     * 取消订阅外刊
     * @param
     * @return
     */
    @GetMapping("cancelSubscriptionMagazine/{id}")
    public R cancelSubscriptionMagazine(@PathVariable String id){
        subscriptionService.removeById(id);
        return R.ok();
    }

    /**
     * 查询我的订阅外刊
     */
    @GetMapping("getSubscriptionMagazineByUserId")
    public R getSubscriptionMagazineByUserId(String userId,String classifiedId){
        List<Subscription> list = subscriptionService.getSubscriptionMagazineByUserId(userId, classifiedId);
        return R.ok().data("list", list);
    }

    /**
     * 加入生词本
     */
    @PostMapping("sendVocabulary")
    public R sendFeedback(@RequestBody Vocabulary vocabulary){
        vocabulary.setWordName(vocabulary.getWordName().toLowerCase());
        vocabularyService.save(vocabulary);
        return R.ok();
    }

    /**
     * 取消生词本
     */
    @GetMapping("cancelVocabulary/{id}")
    public R cancelVocabulary(@PathVariable String id){
        vocabularyService.removeById(id);
        return R.ok();
    }

    /**
     * 查询我的生词本
     * @return
     */
    @GetMapping("getVocabularyListByUserId/{userId}")
    public R getVocabularyListByUserId(@PathVariable String userId) {
        List<Vocabulary> list = vocabularyService.list(new QueryWrapper<Vocabulary>().eq("user_id", userId));
        return R.ok().data("list", list);
    }

    /**
     * 单词翻译查询
     */
    @GetMapping("translate")
    public R translate(String wordName, String userId){

        String newData = wordName.toLowerCase();
        if(AceUtils.isChinese(wordName)){
            LexiconInfo lexiconInfo = new LexiconInfo();
            lexiconInfo.setFlag(true);
            String wyTranslate = AceUtils.wyTranslate(newData);
            if(!StringUtils.isEmpty(wyTranslate)){
                JSONObject jsonObject = JSONObject.parseObject(wyTranslate);
                String tSpeakUrl = jsonObject.getString("tSpeakUrl");
                lexiconInfo.setVoice(tSpeakUrl);
                lexiconInfo.setTranslation(JSONObject.parseArray(jsonObject.getString("translation"), String.class));
            }

            return R.ok().data("data", lexiconInfo);
        }else{
            // 查询词库
            LexiconInfo lexiconInfo = lexiconInfoService.getOne(new QueryWrapper<LexiconInfo>().eq("word_name", newData).last("limit 0, 1"));
            if(lexiconInfo == null){
                return R.error("单词不存在词库中");
            }
            // 查询是否为生词本单词
            Vocabulary vocabulary = vocabularyService.getOne(new QueryWrapper<Vocabulary>().eq("word_name", newData).eq("user_id", userId));
            if(vocabulary != null){
                lexiconInfo.setVocabularyId(vocabulary.getId());
            }

            String voice = lexiconInfo.getVoice();
            if(StringUtils.isEmpty(voice)){
                String wyTranslate = AceUtils.wyTranslate(newData);
                if(!StringUtils.isEmpty(wyTranslate)){
                    JSONObject jsonObject = JSONObject.parseObject(wyTranslate);
                    String speakUrl = jsonObject.getString("speakUrl");
                    lexiconInfo.setVoice(speakUrl);
                    lexiconInfoService.updateById(lexiconInfo);
                }
            }
            return R.ok().data("data", lexiconInfo);
        }

    }



    /**
     * 查询词库列表
     */
    @GetMapping("getLexiconList")
    public R getLexiconList(){
        List<Lexicon> lexicon = lexiconService.list(new QueryWrapper<Lexicon>().orderByDesc("create_time"));
        for (Lexicon lexicon1 : lexicon) {
            lexicon1.setCount( lexiconInfoService.count(new QueryWrapper<LexiconInfo>().eq("lexicon_id", lexicon1.getId())));
        }
        return R.ok().data("list", lexicon);
    }

    /**
     * 查询词库单词数据
     */
    @GetMapping("getLexiconInfoByLexiconId")
    public R getLexiconInfoByLexiconId(String id, String userId){
        List<LexiconInfo> list = null;
        if(StringUtils.isEmpty(userId)){ // 未登录查询词库
             list = lexiconInfoService.list(new QueryWrapper<LexiconInfo>().eq("lexicon_id", id).orderByDesc("id"));
        }else{ // 登录后查询不认识单词
             list = lexiconInfoService.getLexiconInfoByLexiconId(id, userId);
        }

        return R.ok().data("list", list);
    }

    /**
     * 查询我的学习计划统计
     */
    @GetMapping("getCurve/{id}")
    public R getZzt(@PathVariable String id){
        Map<String, Object> list = reviewService.getZzt(id);
        return R.ok().data("row", list);
    }

    /**
     * 查询我的学习计划
     */
    @GetMapping("getStudyPlanByUserId/{id}")
    public R getStudyPlanByUserId(@PathVariable String id){
        List<StudyPlan> list = studyPlanService.getStudyPlanByUserId(id);
        return R.ok().data("list", list);
    }

    /**
     * 创建学习计划
     */
    @PostMapping("sendStudyPlan")
    public R sendStudyPlan(@RequestBody StudyPlan studyPlan){
        StudyPlan studyPlan1 =  studyPlanService.getThisDateIs(studyPlan);
        if(studyPlan1 != null){
            return R.error(studyPlan.getStartDate()+"日期中已创建题库计划了，无法重复创建");
        }
        studyPlan.setStatus("0");
        studyPlanService.save(studyPlan);
        return R.ok();
    }

    /**
     * 查询复习单词
     */

    @GetMapping("getReviewByUserId/{id}")
    public R getReviewByUserId(@PathVariable String id){
        List<Review> list = reviewService.getReviewByUserId(id);
        return R.ok().data("list", list);
    }

    /**
     * 认识单词
     */
    @Transactional
    @PostMapping("yesWord")
    public R yesWord(@RequestBody Review reviewNew){
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(reviewNew.getUserId())) {
            // 获取今天单词复习记录
            Review review = reviewService.getReviewByReview(reviewNew);
            if (review != null) {
                review.setStatus("1");
                review.setCreateTime(new Date());
                reviewService.updateById(review);
            } else {
                // 新增单词复习记录
                reviewNew.setStatus("1");
                reviewService.save(reviewNew);
            }
            // 查询单词是否工作计划中
            LexiconInfo lexiconInfo = lexiconInfoService.getById(reviewNew.getLexiconInfoId());
            if(lexiconInfo!= null){
                // 查询工作计划(日计划)
                StudyPlan studyPlan = studyPlanService.getTypeStudyPlanByUserIdAndLexiconId("2", reviewNew.getUserId(), lexiconInfo.getLexiconId());
                if(studyPlan != null){
                    StudyPlan studyPlan1 = studyPlanService.getTypeStudyPlanByUserIdAndLexiconId("1", reviewNew.getUserId(), lexiconInfo.getLexiconId());
                    if(studyPlan.getCompleteWord() <= (studyPlan.getCount()+1)){
                        if(studyPlan1 != null){
                            if(studyPlan1.getCompleteWord() <= (studyPlan1.getCount()+1)){
                                studyPlan1.setStatus("1");
                                studyPlanService.updateById(studyPlan1);
                            }
                        }
                        studyPlan.setStatus("1");
                        studyPlanService.updateById(studyPlan);
                        return R.error("计划："+studyPlan.getTitle()+"已完成");
                    }else{
                        if(studyPlan1 != null){
                            if(studyPlan1.getCompleteWord().equals(studyPlan1.getCount())){
                                studyPlan1.setStatus("1");
                                studyPlanService.updateById(studyPlan1);
                                return R.error("计划："+studyPlan1.getTitle()+"已完成");
                            }
                        }
                    }
                }

            }


        }

        return R.ok();
    }

    /**
     * 不认单词
     */
    @PostMapping("noWord")
    public R noWord(@RequestBody Review reviewNew){
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(reviewNew.getUserId())){
            // 获取今天单词复习记录
            Review review = reviewService.getReviewByReview(reviewNew);
            if(review != null){
                review.setStatus("0");
                review.setCreateTime(new Date());
                reviewService.updateById(review);
            }else{
                // 新增单词复习记录
                reviewNew.setStatus("0");
                reviewService.save(reviewNew);
            }
        }
        return R.ok();
    }

    /**
     * 查询所有题库
     */
    @GetMapping("getLexiconAll")
    public R getLexiconAll(){
        List<Lexicon> list = lexiconService.list(new QueryWrapper<Lexicon>().orderByDesc("create_time"));
        return R.ok().data("list", list);
    }



}
