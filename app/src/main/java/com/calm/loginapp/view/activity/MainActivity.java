package com.calm.loginapp.view.activity;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bawei.loginapp.R;
import com.calm.loginapp.contract.IMainConstance;
import com.calm.loginapp.model.bean.LoginBean;
import com.calm.loginapp.model.realmbean.UserRealm;
import com.calm.loginapp.presenter.MainPresenter;
import com.calm.loginapp.view.BaseActivity;
import com.google.gson.Gson;

import io.realm.Realm;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainConstance.IMainView {


    private EditText main_phone;
    private EditText main_pwd;
    private ImageView show_pwd;
    private Button main_login;
    private Button main_register;
    private boolean isOpenEye = false;

    @Override
    protected void initData() {

        show_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpenEye) {
                    isOpenEye = true;
                    main_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //密码不可见
                    isOpenEye = false;
                    main_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        //登录
        main_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = main_phone.getText().toString();
                String pwd = main_pwd.getText().toString();
                presenter.getLoginData(phone, pwd);

            }
        });
        //注册
        main_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initView() {
        main_phone = (EditText) findViewById(R.id.main_phone);
        main_pwd = (EditText) findViewById(R.id.main_pwd);
        show_pwd = (ImageView) findViewById(R.id.show_pwd);
        main_login = (Button) findViewById(R.id.main_login);
        main_register = (Button) findViewById(R.id.main_register);
    }

    @Override
    public void setData(String json) {
        LoginBean loginBean = new Gson().fromJson(json, LoginBean.class);
        String message = loginBean.getMessage();
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
        int userId = loginBean.getResult().getUserId();
        String sessionId = loginBean.getResult().getSessionId();
        //进行数据缓存
        Realm realm = Realm.getDefaultInstance();
        UserRealm userRealm = new UserRealm();
        userRealm.sessionId = sessionId;
        userRealm.userId = userId;
        //开启事务,并提交
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(userRealm);
        realm.commitTransaction();
        if (loginBean.getMessage().equals("登录成功")) {
            startActivity(new Intent(MainActivity.this, HeadPicActivity.class));
        }

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.deatchView();
        }
    }
}