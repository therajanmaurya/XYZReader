package com.opensource.xyz.reader.ui.article;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opensource.xyz.reader.R;
import com.opensource.xyz.reader.data.model.Article;
import com.opensource.xyz.reader.util.DynamicHeightNetworkImageView;
import com.opensource.xyz.reader.util.ImageLoaderHelper;
import com.opensource.xyz.reader.util.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<Article> mArticles;
    private Context mContext;

    @Inject
    public ArticleAdapter() {
        mArticles = new ArrayList<>();
    }

    public void setArticles(List<Article> articles) {
        mArticles = articles;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_article, parent, false);
        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ArticleViewHolder holder, int position) {
        Article article = mArticles.get(position);
        holder.tvTitle.setText(article.title());
        holder.tvSubTitle.setText(DateUtils.getRelativeTimeSpanString(
                Utils.getTimeInMilliSeconds(article.publishedDate()),
                System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_ALL).toString()
                + " by "
                + article.author()
                + "</font>");
        holder.thumbnailView.setImageUrl(article.thumb(),
                ImageLoaderHelper.getInstance(mContext).getImageLoader());
        holder.thumbnailView.setAspectRatio(article.aspectRatio());
    }

    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.thumbnail)
        DynamicHeightNetworkImageView thumbnailView;

        @BindView(R.id.article_title)
        TextView tvTitle;

        @BindView(R.id.article_subtitle)
        TextView tvSubTitle;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
