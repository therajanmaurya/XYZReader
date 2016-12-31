package com.opensource.xyz.reader;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.opensource.xyz.reader.data.DataManager;
import com.opensource.xyz.reader.data.model.Article;
import com.opensource.xyz.reader.test.common.TestDataFactory;
import com.opensource.xyz.reader.ui.article.ArticleMvpView;
import com.opensource.xyz.reader.ui.article.ArticlePresenter;
import com.opensource.xyz.reader.util.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import rx.Observable;

@RunWith(MockitoJUnitRunner.class)
public class ArticlePresenterTest {

    @Mock
    ArticleMvpView mMockMainMvpView;
    @Mock DataManager mMockDataManager;

    private ArticlePresenter mMainPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mMainPresenter = new ArticlePresenter(mMockDataManager);
        mMainPresenter.attachView(mMockMainMvpView);
    }

    @After
    public void tearDown() {
        mMainPresenter.detachView();
    }

    @Test
    public void loadArticleReturnsArticles() {
        List<Article> articles = TestDataFactory.makeListArticles(10);
        when(mMockDataManager.getArticles())
                .thenReturn(Observable.just(articles));

        mMainPresenter.loadArticles();
        verify(mMockMainMvpView).showArticles(articles);
        verify(mMockMainMvpView, never()).showArticlesEmpty();
        verify(mMockMainMvpView, never()).showError();
    }

    @Test
    public void loadArticleReturnsEmptyList() {
        when(mMockDataManager.getArticles())
                .thenReturn(Observable.just(Collections.<Article>emptyList()));

        mMainPresenter.loadArticles();
        verify(mMockMainMvpView).showArticlesEmpty();
        verify(mMockMainMvpView, never()).showArticles(anyListOf(Article.class));
        verify(mMockMainMvpView, never()).showError();
    }

    @Test
    public void loadArticlesFails() {
        when(mMockDataManager.getArticles())
                .thenReturn(Observable.<List<Article>>error(new RuntimeException()));

        mMainPresenter.loadArticles();
        verify(mMockMainMvpView).showError();
        verify(mMockMainMvpView, never()).showArticlesEmpty();
        verify(mMockMainMvpView, never()).showArticles(anyListOf(Article.class));
    }
}