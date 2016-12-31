package com.opensource.xyz.reader.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.opensource.xyz.reader.data.model.Article;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@Singleton
public class DatabaseHelper {

    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        SqlBrite.Builder briteBuilder = new SqlBrite.Builder();
        mDb = briteBuilder.build().wrapDatabaseHelper(dbOpenHelper, Schedulers.immediate());
    }

    public BriteDatabase getBriteDb() {
        return mDb;
    }

    public Observable<List<Article>> setArticles(final List<Article> articles) {
        return Observable.create(new Observable.OnSubscribe<List<Article>>() {
            @Override
            public void call(Subscriber<? super List<Article>> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    mDb.delete(Db.ArticleTable.TABLE_NAME, null);
                    for (Article article : articles) {
                        long result = mDb.insert(Db.ArticleTable.TABLE_NAME,
                                Db.ArticleTable.toContentValues(article),
                                SQLiteDatabase.CONFLICT_REPLACE);
                    }
                    transaction.markSuccessful();
                    subscriber.onNext(articles);
                    subscriber.onCompleted();
                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<List<Article>> getArticles() {
        return mDb.createQuery(Db.ArticleTable.TABLE_NAME,
                "SELECT * FROM " + Db.ArticleTable.TABLE_NAME)
                .mapToList(new Func1<Cursor, Article>() {
                    @Override
                    public Article call(Cursor cursor) {
                        return Article.create(Db.ArticleTable.parseCursor(cursor));
                    }
                });
    }

}
