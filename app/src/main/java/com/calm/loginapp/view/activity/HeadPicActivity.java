package com.calm.loginapp.view.activity;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bawei.loginapp.R;
import com.calm.loginapp.contract.IMainConstance;
import com.calm.loginapp.model.bean.HeadPicBean;
import com.calm.loginapp.presenter.MainPresenter;
import com.calm.loginapp.view.BaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class HeadPicActivity extends BaseActivity<MainPresenter> implements IMainConstance.IMainView {

    private static final String TAG = "HeadPicActivity";

    private Button btn_upimage;
    private SimpleDraweeView up_Image;

    @Override
    public void setData(String json) {
        HeadPicBean headPicBean = new Gson().fromJson(json, HeadPicBean.class);
        String headPath = headPicBean.getHeadPath();
        Toast.makeText(this, "" + headPath, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "setData: " + headPath);
    }

    @Override
    protected void initData() {
        btn_upimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_PICK);
                it.setType("image/*");
                startActivityForResult(it, 1000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Intent it = new Intent("com.android.camera.action.CROP");
            it.setDataAndType(uri, "image/*");
            it.putExtra("CROP", true);
            it.putExtra("return-data", true);
            startActivityForResult(it, 2000);
        }
        if (requestCode == 2000 && resultCode == RESULT_OK) {
            Bitmap mBitMap = data.getParcelableExtra("data");
            up_Image.setImageBitmap(mBitMap);
            File file = getFile(mBitMap);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
            presenter.getHeadPicData(part);
        }
    }

    private File getFile(Bitmap mBitMap) {
        String defaultGoodInfo = getApplicationContext().getFilesDir().getAbsolutePath() + "defaultGoodInfo";
        File file = new File(defaultGoodInfo);
        if (!file.exists()) {
            file.mkdirs();
        }
        String defaultGoodPath = defaultGoodInfo + "/messageImage.jpg";
        file = new File(defaultGoodPath);
        try {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            mBitMap.compress(Bitmap.CompressFormat.PNG, 20, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    @Override
    protected void initView() {
        btn_upimage = (Button) findViewById(R.id.btn_upimage);
        up_Image = (SimpleDraweeView) findViewById(R.id.up_Image);
    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_head_pic;
    }
}
