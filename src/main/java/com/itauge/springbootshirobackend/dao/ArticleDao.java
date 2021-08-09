package com.itauge.springbootshirobackend.dao;

import com.itauge.springbootshirobackend.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ArticleDao {
    /**
     * new article
     */
    public int addArticle(Article article);

    /**
     * cal the total number of article
     */
    public int countArticle();

    /**
     * article list
     */
    public List<Article> listArticle(@Param("pageStart")int pageStart,
                                     @Param("pageSize") int pageSize);

    /**
     * update Article
     */
    public Article updateArticle(Article article);
}
