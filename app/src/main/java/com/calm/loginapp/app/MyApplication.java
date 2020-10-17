package com.calm.loginapp.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import io.realm.Realm;

/**
 * @ProjectName: LoginApp
 * @ClassName: MyApplication
 * @Description: java类作用描述
 * @Author: DELL
 * @CreateDate: 2020/10/16
 * @UpdateUser: 王祎卓
 * @Version: 1.0
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化realm和fresco
        Fresco.initialize(this);
        Realm.init(this);
    }
}
