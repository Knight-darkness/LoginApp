package com.calm.loginapp.view.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bawei.loginapp.R;
import com.calm.loginapp.contract.IMainConstance;
import com.calm.loginapp.model.bean.LoginBean;
import com.calm.loginapp.presenter.MainPresenter;
import com.calm.loginapp.view.BaseActivity;
import com.google.gson.Gson;

public class RegisterActivity extends BaseActivity<MainPresenter> implements IMainConstance.IMainView {

    private EditText register_phone;
    private EditText register_pwd;
    private ImageView show_pwd;
    private Button register_ok;

    @Override
    public void setData(String json) {
        LoginBean loginBean = new Gson().fromJson(json, LoginBean.class);
        Toast.makeText(this, "" + loginBean.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initData() {
        register_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
                String phone = register_phone.getText().toString();
                String pwd = register_pwd.getText().toString();
                presenter.getRegisterData(phone, pwd);
            }
        });
    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    public void initView() {

        register_phone = (EditText) findViewById(R.id.register_phone);
        register_pwd = (EditText) findViewById(R.id.register_pwd);
        show_pwd = (ImageView) findViewById(R.id.show_pwd);
        register_ok = (Button) findViewById(R.id.register_ok);

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_register;
    }

    private void submit() {
        // validate
        String phone = register_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请注册账号", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd = register_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "pwd不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
    }
}
