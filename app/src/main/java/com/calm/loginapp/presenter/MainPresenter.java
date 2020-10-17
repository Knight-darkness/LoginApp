package com.calm.loginapp.presenter;

import android.util.Log;

import com.calm.loginapp.contract.IMainConstance;
import com.calm.loginapp.model.RetrofitManager;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @ProjectName: LoginApp
 * @ClassName: IMainPresenter
 * @Description: java类作用描述
 * @Author: DELL
 * @CreateDate: 2020/10/16
 * @UpdateUser: 王祎卓
 * @Version: 1.0
 */
public class MainPresenter extends BasePresenter<IMainConstance.IMainView> implements IMainConstance.IMainPresenter {
    //登录
    @Override
    public void getLoginData(String phone, String pwd) {
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("pwd", pwd);
        String string = new JSONObject(map).toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type, application/json"), string);
        RetrofitManager.getInstance().create()
                .getLoginData(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody value) {
                        try {
                            String jsonData = value.string();
                            Log.i("zhanshiba", "onNext: " + jsonData);
                            mView.setData(jsonData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //注册
    @Override
    public void getRegisterData(String phone, String pwd) {
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("pwd", pwd);
        String string = new JSONObject(map).toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse("Content-Type, application/json"), string);
        RetrofitManager.getInstance().create()
                .getRegisterData(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody value) {
                        try {
                            String jsonData = value.string();
                            Log.i("zhanshiba", "onNext: " + jsonData);
                            mView.setData(jsonData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //上传头像
    @Override
    public void getHeadPicData(MultipartBody.Part body) {

        RetrofitManager.getInstance().create()
                .getPhotoPut(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody value) {
                        try {
                            String jsonData = value.string();
                            Log.i("zhanshiba", "onNext: " + jsonData);
                            mView.setData(jsonData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static File downloadWxImage(String url) {
        File file = null;

        URL urlfile;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            file = File.createTempFile("wx_image", ".png");
            //下载
            urlfile = new URL(url);
            inputStream = urlfile.openStream();
            outputStream = new FileOutputStream(file);

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {

                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != outputStream) {
                    outputStream.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
