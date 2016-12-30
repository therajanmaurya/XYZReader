package com.opensource.xyz.reader.ui.detailarticle;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import com.opensource.xyz.reader.R;
import com.opensource.xyz.reader.data.model.Article;
import com.opensource.xyz.reader.ui.article.ArticleActivity;
import com.opensource.xyz.reader.util.ImageLoaderHelper;
import com.opensource.xyz.reader.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A fragment representing a single Article detail screen. This fragment is
 * either contained in a {@link ArticleActivity} in two-pane mode (on
 * tablets) or a {@link ArticleDetailActivity} on handsets.
 */
public class ArticleDetailFragment extends Fragment implements UpdateArticle {
    private static final String TAG = "ArticleDetailFragment";

    public static final String ARG_ITEM_ID = "item_id";
    private static final float PARALLAX_FACTOR = 1.25f;

    @BindView(R.id.photo)
    ImageView ivPhoto;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.article_title)
    TextView tvArticleTitle;

    @BindView(R.id.article_byline)
    TextView tvArticleByLine;

    @BindView(R.id.article_body)
    TextView tvArticleBody;


    private Article mArticle;
    private View mRootView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticleDetailFragment() {
    }

    public static ArticleDetailFragment newInstance(Article article) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_ITEM_ID, article);
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mArticle = getArguments().getParcelable(ARG_ITEM_ID);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_article_detail, container, false);
        ButterKnife.bind(this, mRootView);
        bindViews();
        return mRootView;
    }

    @OnClick(R.id.share_fab)
    public void onClickShareButton() {
        startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                .setType("text/plain")
                .setText("Some sample text")
                .getIntent(), getString(R.string.action_share)));
    }


    private void bindViews() {
        tvArticleTitle.setText(mArticle.title());
        tvArticleByLine.setText(Html.fromHtml(
                DateUtils.getRelativeTimeSpanString(
                        Utils.getTimeInMilliSeconds(mArticle.publishedDate()),
                        System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                        DateUtils.FORMAT_ABBREV_ALL).toString()
                        + " by <font color='#ffffff'>"
                        + mArticle.author()
                        + "</font>"));
        tvArticleBody.setText(Html.fromHtml(mArticle.body()));
        ImageLoaderHelper.getInstance(getActivity()).getImageLoader()
                .get(mArticle.photo(), new ImageLoader.ImageListener() {
                            @Override
                            public void onResponse(ImageLoader.ImageContainer imageContainer,
                                    boolean b) {
                                Bitmap bitmap = imageContainer.getBitmap();
                                if (bitmap != null) {
                                    ivPhoto.setImageBitmap(imageContainer.getBitmap());
                                }
                            }

                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                            }
                        });
    }

    @Override
    public void updateArticle(Article article) {
        mArticle = article;
        bindViews();
    }
}
