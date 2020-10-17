package com.calm.loginapp.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.calm.loginapp.presenter.BasePresenter;

/**
 * @ProjectName: LoginApp
 * @ClassName: BaseActivity
 * @Description: java类作用描述
 * @Author: DELL
 * @CreateDate: 2020/10/16
 * @UpdateUser: 王祎卓
 * @Version: 1.0
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView {

    public P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
        //初始化控件
        initView();
        //初始化presenter
        presenter = initPresenter();
        //绑定控件
        presenter.attachView(this);
        //初始化数据
        initData();
    }

    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.deatchView();
        }
    }

    protected abstract void initData();

    protected abstract P initPresenter();

    protected abstract void initView();

    protected abstract int layoutId();
}
