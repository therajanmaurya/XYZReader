package com.opensource.xyz.reader.data;

import com.opensource.xyz.reader.data.local.DatabaseHelper;
import com.opensource.xyz.reader.data.local.PreferencesHelper;
import com.opensource.xyz.reader.data.model.Article;
import com.opensource.xyz.reader.data.model.Ribot;
import com.opensource.xyz.reader.data.remote.ReaderService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

@Singleton
public class DataManager {

    private final ReaderService mReaderService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(ReaderService readerService, PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper) {
        mReaderService = readerService;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<Ribot> syncRibots() {
        return mReaderService.getRibots()
                .concatMap(new Func1<List<Ribot>, Observable<Ribot>>() {
                    @Override
                    public Observable<Ribot> call(List<Ribot> ribots) {
                        return Observable.just(ribots.get(0));
                    }
                });
    }

    public Observable<List<Article>> syncArticles() {
        return mReaderService.getArticles()
                .concatMap(new Func1<List<Article>, Observable<? extends List<Article>>>() {
                    @Override
                    public Observable<? extends List<Article>> call(List<Article> articles) {
                        return mDatabaseHelper.setArticles(articles);
                    }
                });
    }

    public Observable<List<Article>> getArticles() {
        return mDatabaseHelper.getArticles().distinct();
    }

    public Observable<List<Ribot>> getRibots() {
        List<Ribot> ribots = new ArrayList<>();
        return Observable.just(ribots);
    }

}
