package com.forsyslab.talquest10.dagger.module;


        import android.app.Application;
        import android.content.SharedPreferences;
        import android.preference.PreferenceManager;
        import com.google.gson.FieldNamingPolicy;
        import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;


        import java.util.concurrent.TimeUnit;

        import javax.inject.Singleton;

        import dagger.Module;
        import dagger.Provides;
        import okhttp3.Cache;
        import okhttp3.OkHttpClient;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    String mBaseUrl;

    public NetModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    @Provides
    @Singleton
    // Application reference must come from AppModule.class
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache, final Application application) {
        OkHttpClient client = new OkHttpClient.Builder().cache(cache).readTimeout(4, TimeUnit.SECONDS).connectTimeout(4, TimeUnit.SECONDS).build();
        return client;
    }

    @Provides
   // @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
        return retrofit;
    }
}