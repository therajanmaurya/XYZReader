package com.opensource.xyz.reader;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.opensource.xyz.reader.data.DataManager;
import com.opensource.xyz.reader.data.local.DatabaseHelper;
import com.opensource.xyz.reader.data.local.PreferencesHelper;
import com.opensource.xyz.reader.data.model.Article;
import com.opensource.xyz.reader.data.remote.ReaderService;
import com.opensource.xyz.reader.test.common.TestDataFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

/**
 * This test class performs local unit tests without dependencies on the Android framework
 * For testing methods in the DataManager follow this approach:
 * 1. Stub mock helper classes that your method relies on. e.g. RetrofitServices or DatabaseHelper
 * 2. Test the Observable using TestSubscriber
 * 3. Optionally write a SEPARATE test that verifies that your method is calling the right helper
 * using Mockito.verify()
 */
@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    @Mock
    DatabaseHelper mMockDatabaseHelper;

    @Mock
    PreferencesHelper mMockPreferencesHelper;

    @Mock
    ReaderService mMockArticlesService;

    private DataManager mDataManager;

    @Before
    public void setUp() {
        mDataManager = new DataManager(mMockArticlesService, mMockPreferencesHelper,
                mMockDatabaseHelper);
    }

    @Test
    public void syncArticlesEmitsValues() {
        List<Article> articles = TestDataFactory.makeListArticles(2);
        stubSyncArticlesHelperCalls(articles);

        TestSubscriber<List<Article>> result = new TestSubscriber<>();
        mDataManager.syncArticles().subscribe(result);
        result.assertNoErrors();
        //result.assertReceivedOnNext(articles);
    }

    @Test
    public void syncArticlesCallsApiAndDatabase() {
        List<Article> articles = TestDataFactory.makeListArticles(2);
        stubSyncArticlesHelperCalls(articles);

        mDataManager.syncArticles().subscribe();
        // Verify right calls to helper methods
        verify(mMockArticlesService).getArticles();
        verify(mMockDatabaseHelper).setArticles(articles);
    }

    @Test
    public void syncArticlesDoesNotCallDatabaseWhenApiFails() {
        when(mMockArticlesService.getArticles())
                .thenReturn(Observable.<List<Article>>error(new RuntimeException()));

        mDataManager.syncArticles().subscribe(new TestSubscriber<List<Article>>());
        // Verify right calls to helper methods
        verify(mMockArticlesService).getArticles();
        verify(mMockDatabaseHelper, never()).setArticles(anyListOf(Article.class));
    }

    private void stubSyncArticlesHelperCalls(List<Article> articles) {
        // Stub calls to the Articles service and database helper.
        when(mMockArticlesService.getArticles())
                .thenReturn(Observable.just(articles));
        when(mMockDatabaseHelper.setArticles(articles))
                .thenReturn(Observable.just(articles));
    }

}
