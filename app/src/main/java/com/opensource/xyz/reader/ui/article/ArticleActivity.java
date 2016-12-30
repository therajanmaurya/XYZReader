package com.opensource.xyz.reader.ui.article;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.opensource.xyz.reader.R;
import com.opensource.xyz.reader.data.SyncService;
import com.opensource.xyz.reader.data.model.Article;
import com.opensource.xyz.reader.ui.base.BaseActivity;
import com.opensource.xyz.reader.util.DialogFactory;
import com.opensource.xyz.reader.util.ItemOffsetDecoration;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleActivity extends BaseActivity implements ArticleMvpView {

    private static final String EXTRA_TRIGGER_SYNC_FLAG = "ArticleActivity.EXTRA_TRIGGER_SYNC_FLAG";

    @Inject
    ArticlePresenter mArticlePresenter;

    @Inject
    ArticleAdapter mArticleAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    /**
     * Return an Intent to start this Activity.
     * triggerDataSyncOnCreate allows disabling the background sync service onCreate. Should
     * only be set to false during testing.
     */
    public static Intent getStartIntent(Context context, boolean triggerDataSyncOnCreate) {
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra(EXTRA_TRIGGER_SYNC_FLAG, triggerDataSyncOnCreate);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_article_list);
        ButterKnife.bind(this);

        int columnCount = getResources().getInteger(R.integer.list_column_count);
        StaggeredGridLayoutManager sglm =
                new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(sglm);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        mRecyclerView.addItemDecoration(itemDecoration);
        mArticleAdapter.setContext(this);
        mRecyclerView.setAdapter(mArticleAdapter);
        mArticlePresenter.attachView(this);
        mArticlePresenter.loadArticles();

        if (getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
            startService(SyncService.getStartIntent(this));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mArticlePresenter.detachView();
    }

    /***** MVP View methods implementation *****/

    @Override
    public void showArticles(List<Article> articles) {
        mArticleAdapter.setArticles(articles);
        mArticleAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_ribots))
                .show();
    }

    @Override
    public void showArticlesEmpty() {
        mArticleAdapter.setArticles(Collections.<Article>emptyList());
        mArticleAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.empty_ribots, Toast.LENGTH_LONG).show();
    }

}
