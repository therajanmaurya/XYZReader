package com.opensource.xyz.reader.data.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rajan Maurya on 30/12/16.
 */
@AutoValue
public abstract class Article implements Parcelable {

    @SerializedName("id")
    public abstract Integer id();

    @SerializedName("photo")
    public abstract String photo();

    @SerializedName("thumb")
    public abstract String thumb();

    @SerializedName("aspect_ratio")
    public abstract Double aspectRatio();

    @SerializedName("author")
    public abstract String author();

    @SerializedName("title")
    public abstract String title();

    @SerializedName("published_date")
    public abstract String publishedDate();

    @SerializedName("body")
    public abstract String body();

    public static Builder builder() {
        return new AutoValue_Article.Builder();
    }

    public static TypeAdapter<Article> typeAdapter(Gson gson) {
        return new AutoValue_Article.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(Integer id);
        public abstract Builder setPhoto(String photo);
        public abstract Builder setThumb(String thumb);
        public abstract Builder setAspectRatio(Double aspectRatio);
        public abstract Builder setAuthor(String author);
        public abstract Builder setTitle(String title);
        public abstract Builder setPublishedDate(String publishedDate);
        public abstract Builder setBody(String body);
        public abstract Article build();
    }
}
