package com.opensource.xyz.reader.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensource.xyz.reader.data.model.Article;
import com.opensource.xyz.reader.data.model.Ribot;
import com.opensource.xyz.reader.util.MyGsonTypeAdapterFactory;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

public interface ReaderService {

    String ENDPOINT = "https://dl.dropboxusercontent.com/u/231329/xyzreader_data/";

    @GET("data.json")
    Observable<List<Ribot>> getRibots();

    @GET("data.json")
    Observable<List<Article>> getArticles();

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static ReaderService newReaderService() {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ReaderService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(httpClient.build())
                    .build();
            return retrofit.create(ReaderService.class);
        }
    }
}
