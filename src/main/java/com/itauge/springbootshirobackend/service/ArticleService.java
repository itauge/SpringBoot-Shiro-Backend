package com.itauge.springbootshirobackend.service;

import com.alibaba.fastjson.JSONObject;
import com.itauge.springbootshirobackend.dao.ArticleDao;
import com.itauge.springbootshirobackend.entity.Article;
import com.itauge.springbootshirobackend.util.CommonUtil;
import com.itauge.springbootshirobackend.vo.ResultVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    ArticleDao articleDao;

    /**
     * add Article
     * @param article
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultVO addArticle(Article article){
        articleDao.addArticle(article);
        return CommonUtil.successJSON();
    }

    /**
     * Show all the article
     * @return
     */
    public ResultVO listArticle(JSONObject jsonObject){
        CommonUtil.fillPageParam(jsonObject);
        int countArticle = articleDao.countArticle();
        int pageStart = jsonObject.getIntValue("pageStart");
        int pageSize = jsonObject.getIntValue("pageSize");
        List<Article> articles = articleDao.listArticle(pageStart, pageSize);
        return CommonUtil.successPage(jsonObject,articles,countArticle);
    }

    /**
     * update article
     * @param article
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultVO updateArticle(Article article){
        articleDao.updateArticle(article);
        return CommonUtil.successJSON();
    }



}
