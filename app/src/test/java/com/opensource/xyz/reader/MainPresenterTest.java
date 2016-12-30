package com.opensource.xyz.reader;

import com.opensource.xyz.reader.data.DataManager;
import com.opensource.xyz.reader.ui.article.ArticleMvpView;
import com.opensource.xyz.reader.ui.article.ArticlePresenter;
import com.opensource.xyz.reader.util.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

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

   /* @Test
    public void loadRibotsReturnsRibots() {
        List<Ribot> ribots = TestDataFactory.makeListRibots(10);
        when(mMockDataManager.getRibots())
                .thenReturn(Observable.just(ribots));

        mMainPresenter.loadArticles();
        verify(mMockMainMvpView).showArticles(ribots);
        verify(mMockMainMvpView, never()).showArticlesEmpty();
        verify(mMockMainMvpView, never()).showError();
    }

    @Test
    public void loadRibotsReturnsEmptyList() {
        when(mMockDataManager.getRibots())
                .thenReturn(Observable.just(Collections.<Ribot>emptyList()));

        mMainPresenter.loadArticles();
        verify(mMockMainMvpView).showArticlesEmpty();
        verify(mMockMainMvpView, never()).showArticles(anyListOf(Ribot.class));
        verify(mMockMainMvpView, never()).showError();
    }

    @Test
    public void loadRibotsFails() {
        when(mMockDataManager.getRibots())
                .thenReturn(Observable.<List<Ribot>>error(new RuntimeException()));

        mMainPresenter.loadArticles();
        verify(mMockMainMvpView).showError();
        verify(mMockMainMvpView, never()).showArticlesEmpty();
        verify(mMockMainMvpView, never()).showArticles(anyListOf(Ribot.class));
    }
*/
}