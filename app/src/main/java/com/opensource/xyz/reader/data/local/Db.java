package com.opensource.xyz.reader.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.opensource.xyz.reader.data.model.Article;

public class Db {

    public Db() {
    }

    public abstract static class ArticleTable {
        public static final String TABLE_NAME = "articles";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_PHOTO = "photo";
        public static final String COLUMN_THUMB = "thumb";
        public static final String COLUMN_ASPECT_RATIO = "aspect_ratio";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PUBLISHED_DATE = "published_date";
        public static final String COLUMN_BODY = "body";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_PHOTO + " TEXT NOT NULL, " +
                        COLUMN_THUMB + " TEXT NOT NULL, " +
                        COLUMN_ASPECT_RATIO + " FLOAT, " +
                        COLUMN_AUTHOR + " TEXT NOT NULL, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_BODY + " TEXT, " +
                        COLUMN_PUBLISHED_DATE + " TEXT" +
                        " ); ";

        public static ContentValues toContentValues(Article article) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, article.id());
            values.put(COLUMN_PHOTO, article.photo());
            values.put(COLUMN_THUMB, article.thumb());
            values.put(COLUMN_ASPECT_RATIO, article.aspectRatio());
            values.put(COLUMN_AUTHOR, article.author());
            values.put(COLUMN_TITLE, article.title());
            values.put(COLUMN_BODY, article.body());
            values.put(COLUMN_PUBLISHED_DATE, article.publishedDate());
            return values;
        }

        public static Article parseCursor(Cursor cursor) {
            return Article.builder()
                    .setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)))
                    .setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHOTO)))
                    .setThumb(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THUMB)))
                    .setAspectRatio(cursor.getFloat(
                            cursor.getColumnIndexOrThrow(COLUMN_ASPECT_RATIO)))
                    .setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR)))
                    .setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)))
                    .setBody(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BODY)))
                    .setPublishedDate(cursor.getString(
                            cursor.getColumnIndexOrThrow(COLUMN_PUBLISHED_DATE)))
                    .build();
        }
    }
}
