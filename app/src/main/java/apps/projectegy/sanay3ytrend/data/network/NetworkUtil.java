package apps.projectegy.sanay3ytrend.data.network;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import apps.projectegy.sanay3ytrend.utils.Constant;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

public class NetworkUtil {

    public static RetrofitInterface getRetrofitNoHeader()
    {
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        /*OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ChuckInterceptor(MyApp.getAppContext()))
                .build();*/

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30 , TimeUnit.SECONDS);
        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL_HTTP)
                .client(httpClient.build())
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(RetrofitInterface.class);

    }


    public static RetrofitInterface getRetrofitForStores() {
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        /*OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ChuckInterceptor(MyApp.getAppContext()))
                .build();*/

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        return new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .client(httpClient.build())
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(RetrofitInterface.class);

    }

    public static RetrofitInterface getRetrofitByToken(String token, Context context) {

        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        String newToken = "Bearer " + token;
        httpClient.addInterceptor(chain -> {

            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .addHeader("Authorization", newToken)
                    .addHeader("latitude", sharedPreferences.getString(Constant.LatSH, "31.13253"))
                    .addHeader("longitude", sharedPreferences.getString(Constant.LngSH, "30.64508"))
                    .addHeader("AreaId", sharedPreferences.getString(Constant.areaId, "1"))
                    .method(original.method(), original.body());
            return chain.proceed(builder.build());

        });

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL_HTTP)
                .client(httpClient.build())
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(RetrofitInterface.class);
    }

    public static RetrofitInterface getRetrofitByLatLng(Context context) {

        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(Constant.SHARED_PREFERENCE, Context.MODE_PRIVATE);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {

            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .addHeader("AreaId", sharedPreferences.getString(Constant.areaId, "1"))
                    .addHeader("latitude", sharedPreferences.getString(Constant.LatSH, "31.13253"))
                    .addHeader("longitude", sharedPreferences.getString(Constant.LngSH, "30.64508"))
                    .method(original.method(), original.body());
            return chain.proceed(builder.build());

        });

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL_HTTP)
                .client(httpClient.build())
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(RetrofitInterface.class);
    }

}