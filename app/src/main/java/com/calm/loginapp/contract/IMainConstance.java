package com.calm.loginapp.contract;

import com.calm.loginapp.view.IBaseView;

import okhttp3.MultipartBody;

/**
 * @ProjectName: LoginApp
 * @ClassName: IMainConstance
 * @Description: java类作用描述
 * @Author: DELL
 * @CreateDate: 2020/10/16
 * @UpdateUser: 王祎卓
 * @Version: 1.0
 */

public interface IMainConstance {
    interface IMainView extends IBaseView {
        void setData(String json);
    }

    interface IMainPresenter {
        void getLoginData(String phone, String pwd);

        void getRegisterData(String phone, String pwd);

        void getHeadPicData(MultipartBody.Part body);
    }
}
