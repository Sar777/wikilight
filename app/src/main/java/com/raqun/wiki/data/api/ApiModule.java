package com.raqun.wiki.data.api;

import com.raqun.wiki.BuildConfig;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tyln on 14.08.16.
 */

@Module(
        complete = false,
        library = true,
        injects = {
                WikiServices.class
        }
)

public final class ApiModule {
    private static final String BASE_URL = BuildConfig.BASE_URL;

    @Provides
    @Singleton
    String provideBaseUrl() {
        return BASE_URL;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return createApiClient().build();
    }

    @Provides
    @Singleton
    WikiServices provideWikiServices(Retrofit retrofit) {
        return retrofit.create(WikiServices.class);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(String baseUrl, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    static OkHttpClient.Builder createApiClient() {
        final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(interceptor);
        }
        //okHttpClientBuilder.addInterceptor(new RequestInterceptor());
        okHttpClientBuilder.connectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        return okHttpClientBuilder;
    }
}
