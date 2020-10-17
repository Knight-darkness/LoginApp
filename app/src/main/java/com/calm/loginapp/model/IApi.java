package com.calm.loginapp.model;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @ProjectName: LoginApp
 * @ClassName: IApi
 * @Description: java类作用描述
 * @Author: DELL
 * @CreateDate: 2020/10/16
 * @UpdateUser: 王祎卓
 * @Version: 1.0
 */
public interface IApi {

    //登录
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("user/v1/login")
    Observable<ResponseBody> getLoginData(@Body RequestBody requestBody);

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("user/v1/register")
    Observable<ResponseBody> getRegisterData(@Body RequestBody requestBody);

    //照片上传
    @Multipart
    @POST("user/verify/v1/modifyHeadPic")
    Observable<ResponseBody> getPhotoPut(@Part MultipartBody.Part file);
}
