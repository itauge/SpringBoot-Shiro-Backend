package com.itauge.springbootshirobackend.controller;

import com.itauge.springbootshirobackend.config.annotation.RequiresPermissions;
import com.itauge.springbootshirobackend.entity.Article;
import com.itauge.springbootshirobackend.service.ArticleService;
import com.itauge.springbootshirobackend.util.CommonUtil;
import com.itauge.springbootshirobackend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ArticleController {

    @Autowired
    ArticleService articleService;

    /**
     * 查詢文章列表
     * @param request
     * @return
     */
    @RequiresPermissions("article:list")
    @GetMapping("/listArticle")
    public ResultVO listArticle(HttpServletRequest request){
       return articleService.listArticle(CommonUtil.request2Json(request));
    }

    /**
     * add Article
     * @param article
     * @return
     */
    @RequiresPermissions("article:add")
    @PostMapping("/addArticle")
    public ResultVO addArticle(@RequestBody Article article){
        return articleService.addArticle(article);
    }

    /**
     * update Article
     * @param article
     * @return
     */
    @RequiresPermissions("article:update")
    @PostMapping("/updateArticle")
    public ResultVO updateArticle(@RequestBody Article article){
        return articleService.updateArticle(article);
    }

}
