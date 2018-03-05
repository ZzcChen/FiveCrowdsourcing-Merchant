package com.example.administrator.fivecrowdsourcing_merchant.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.fivecrowdsourcing_merchant.R;

import java.util.ArrayList;
import java.util.List;

public class MerchantInfoActivity extends AppCompatActivity {
    private TextView title;
    private EditText storename;
    private  EditText phone;;
    private  EditText typeofgood;
    private  EditText address;
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
             //选择地址

           }
       });

    }
}
