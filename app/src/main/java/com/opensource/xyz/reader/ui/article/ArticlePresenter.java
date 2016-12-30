package com.opensource.xyz.reader.ui.article;

import com.opensource.xyz.reader.data.DataManager;
import com.opensource.xyz.reader.data.model.Article;
import com.opensource.xyz.reader.injection.ConfigPersistent;
import com.opensource.xyz.reader.ui.base.BasePresenter;
import com.opensource.xyz.reader.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@ConfigPersistent
public class ArticlePresenter extends BasePresenter<ArticleMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public ArticlePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(ArticleMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadArticles() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.syncArticles()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Article>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the articles.");
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<Article> articles) {
                        if (articles.isEmpty()) {
                            getMvpView().showArticlesEmpty();
                        } else {
                            getMvpView().showArticles(articles);
                        }
                    }
                });
    }

}
