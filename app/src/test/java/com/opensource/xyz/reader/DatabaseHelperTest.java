package com.opensource.xyz.reader;

import static junit.framework.Assert.assertEquals;

import android.database.Cursor;

import com.opensource.xyz.reader.data.local.DatabaseHelper;
import com.opensource.xyz.reader.data.local.Db;
import com.opensource.xyz.reader.data.local.DbOpenHelper;
import com.opensource.xyz.reader.data.model.Article;
import com.opensource.xyz.reader.test.common.TestDataFactory;
import com.opensource.xyz.reader.util.DefaultConfig;
import com.opensource.xyz.reader.util.RxSchedulersOverrideRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import rx.observers.TestSubscriber;

/**
 * Unit tests integration with a SQLite Database using Robolectric
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
public class DatabaseHelperTest {

    private final DatabaseHelper mDatabaseHelper =
            new DatabaseHelper(new DbOpenHelper(RuntimeEnvironment.application));

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Test
    public void setArticles() {
        Article article1 = TestDataFactory.createArticle();
        Article article2 = TestDataFactory.createArticle();
        List<Article> articles = Arrays.asList(article1, article2);

        TestSubscriber<List<Article>> result = new TestSubscriber<>();
        mDatabaseHelper.setArticles(articles).subscribe(result);
        result.assertNoErrors();
        //result.assertReceivedOnNext(articles);

        Cursor cursor = mDatabaseHelper.getBriteDb()
                .query("SELECT * FROM " + Db.ArticleTable.TABLE_NAME);
        assertEquals(2, cursor.getCount());
        for (Article article : articles) {
            cursor.moveToNext();
            assertEquals(article, Db.ArticleTable.parseCursor(cursor));
        }
    }

    @Test
    public void getArticles() {
        List<Article> articles = TestDataFactory.makeListArticles(2);

        mDatabaseHelper.setArticles(articles).subscribe();

        TestSubscriber<List<Article>> result = new TestSubscriber<>();
        mDatabaseHelper.getArticles().subscribe(result);
        result.assertNoErrors();
        result.assertValue(articles);
    }

}