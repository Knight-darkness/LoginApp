package com.calm.loginapp.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.calm.loginapp.model.realmbean.UserRealm;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @ProjectName: LoginApp
 * @ClassName: RetrofitManager
 * @Description: java类作用描述
 * @Author: DELL
 * @CreateDate: 2020/10/16
 * @UpdateUser: 王祎卓
 * @Version: 1.0
 */
public class RetrofitManager {

    public Retrofit mRetrofit;


    private static final class RetrofitHolder {
        private static final RetrofitManager INSTANT = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return RetrofitHolder.INSTANT;
    }



    //构建OkHttpClient
    private static OkHttpClient mOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .addHeader("userId", mUserId + "")
                                .addHeader("sessionId", mSessionId + "")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();
    }

    //构建retrofit对象
    private RetrofitManager() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(mOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static int mUserId;
    private static String mSessionId;


    //创建IApi 接口
    public IApi create() {
        Realm realm = Realm.getDefaultInstance();
        UserRealm first = realm.where(UserRealm.class).findFirst();
        if (first != null) {
            mUserId = first.userId;
            mSessionId = first.sessionId;
        }
        return mRetrofit.create(IApi.class);
    }

    //网络判断
    public boolean isNet(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return activeNetworkInfo.isAvailable();
        }
        return false;

    }

}
