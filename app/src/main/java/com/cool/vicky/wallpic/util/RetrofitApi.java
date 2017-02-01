package com.cool.vicky.wallpic.util;

import com.cool.vicky.wallpic.pojo.Wallpaper;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class RetrofitApi {

    public static final String baseUrl = "https://pixabay.com/";

    public static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static ApiInterface getApiInterfaceInstance() {
        return getRetrofitInstance().create(ApiInterface.class);
    }

    public interface ApiInterface {
        @GET("api/")
        Call<Wallpaper> allWallpapers(
                @Query("key") String key,
                @Query("page") String page,
                @Query("response_group") String responseGroup,
                @Query("category") String category,
                @Query("safesearch") String safesearch
        );

        @GET("api/")
        Call<Wallpaper> wallpaper(
                @Query("key") String key,
                @Query("response_group") String responseGroup,
                @Query("id") String id
        );

        @GET("api/")
        Call<Wallpaper> editorChoiceWallpapers(
                @Query("key") String key,
                @Query("page") String page,
                @Query("response_group") String responseGroup,
                @Query("editors_choice") String editorsChoice,
                @Query("safesearch") String safesearch
        );
    }
}
