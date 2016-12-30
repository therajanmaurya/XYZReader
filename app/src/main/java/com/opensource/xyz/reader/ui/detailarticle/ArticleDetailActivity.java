package com.opensource.xyz.reader.ui.detailarticle;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;

import com.opensource.xyz.reader.R;
import com.opensource.xyz.reader.data.model.Article;
import com.opensource.xyz.reader.util.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * An activity representing a single Article detail screen, letting you swipe between articles.
 */
public class ArticleDetailActivity extends AppCompatActivity {


    @BindView(R.id.pager)
    ViewPager mPager;

    private int position;
    private List<Article> mArticleList;
    private ArticleDetailAdapter mArticleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);
        mArticleList = getIntent().getParcelableArrayListExtra(Constants.ARTICLES);
        position = getIntent().getIntExtra(Constants.POSITION, 0);

        mArticleAdapter = new ArticleDetailAdapter(getSupportFragmentManager());

        for (int i=0; i<mArticleList.size(); ++i) {
            mArticleAdapter.addFragment(ArticleDetailFragment.newInstance(mArticleList.get(i)),
                    mArticleList.size());
        }
        mPager.setCurrentItem(position);
        mPager.setAdapter(mArticleAdapter);

        mPager.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
                        getResources().getDisplayMetrics()));
        mPager.setPageMarginDrawable(new ColorDrawable(0x22000000));

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((UpdateArticle) getSupportFragmentManager()
                        .findFragmentByTag(getFragmentTag(position)))
                        .updateArticle(mArticleList.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private String getFragmentTag(int position) {
        return "android:switcher:" + R.id.pager + ":" + position;
    }

}
