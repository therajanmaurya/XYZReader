package com.opensource.xyz.reader.ui.article;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opensource.xyz.reader.R;
import com.opensource.xyz.reader.data.model.Article;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.RibotViewHolder> {

    private List<Article> mArticles;

    @Inject
    public ArticleAdapter() {
        mArticles = new ArrayList<>();
    }

    public void setArticles(List<Article> articles) {
        mArticles = articles;
    }

    @Override
    public RibotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ribot, parent, false);
        return new RibotViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RibotViewHolder holder, int position) {
        Article article = mArticles.get(position);
       // holder.hexColorView.setBackgroundColor(Color.parseColor(article.author()));
        holder.nameTextView.setText(String.format("%s %s",
                article.author(), article.thumb()));
        holder.emailTextView.setText(article.title());
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    class RibotViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.view_hex_color) View hexColorView;
        @BindView(R.id.text_name) TextView nameTextView;
        @BindView(R.id.text_email) TextView emailTextView;

        public RibotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
