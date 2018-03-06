package com.example.administrator.fivecrowdsourcing_merchant.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;
import com.example.administrator.fivecrowdsourcing_merchant.presenter.AddressPresenter;

import java.util.ArrayList;
import java.util.List;

public class MerchantInfoActivity extends AppCompatActivity implements MerchantInfoView{
    private TextView title;
    private EditText storename;
    private  EditText phone;;
    private  EditText typeofgood;
    private  EditText address;
    private AddressPresenter addressPresenter = new AddressPresenter(this);

    public MerchantInfoActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_info);
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title = findViewById(R.id.title);
        title.setText("商家信息认证");
        ImageView iv = findViewById(R.id.right);
       iv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               // 权限判断
               addressPresenter.isPermissions();
               //选择地址界面
               gotomap();
           }
       });

    }


    @Override
    public void gotomap() {
        Intent intent = new Intent(MerchantInfoActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    @Override
    public void finishStep1() {

    }
}
