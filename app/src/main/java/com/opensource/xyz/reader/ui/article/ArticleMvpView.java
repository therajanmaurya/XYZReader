package com.opensource.xyz.reader.ui.article;

import com.opensource.xyz.reader.data.model.Article;
import com.opensource.xyz.reader.ui.base.MvpView;

import java.util.List;

public interface ArticleMvpView extends MvpView {

    void showArticles(List<Article> articles);

    void showArticlesEmpty();

    void showError();

}
