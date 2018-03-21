package com.example.administrator.fivecrowdsourcing_merchant.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.model.AddressInfo;
import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;
import com.example.administrator.fivecrowdsourcing_merchant.presenter.MerchantInfoPresenter;

import java.util.ArrayList;
import java.util.List;

public class MerchantInfoActivity extends AppCompatActivity implements MerchantInfoView{
    private TextView title;
    private TextView nextstep;
    private EditText storename;
    private  EditText phone;;
    private  EditText typeofgood;
    private  TextView address;
    private String addressname;
    private  AddressInfo addressInfo;
    List<StepBean> stepsBeanList = new ArrayList<>();

    private Merchant merchant = new Merchant();
    MerchantInfoPresenter merchantInfoPresenter=new MerchantInfoPresenter(this  );

    public MerchantInfoActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_info);
        initData();
        initView();
    }

    private void initData() {
        StepBean stepBean0 = new StepBean("基本信息",0);
        StepBean stepBean1 = new StepBean("资质证书",-1);
        StepBean stepBean2 = new StepBean("身份信息",-1);
        StepBean stepBean3 = new StepBean("已完成",-1);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);
    }

    @SuppressLint("ResourceAsColor")
    private void initView() {
        //获得商家信息
        merchant= (Merchant) getIntent().getSerializableExtra("merchant");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        address = findViewById(R.id.address);
        storename = findViewById(R.id.storename);
        phone = findViewById(R.id.phone);
        phone.setText(merchant.getPhone());
        typeofgood = findViewById(R.id.type_good);
        title = findViewById(R.id.title);
        title.setText("商家信息认证");
        nextstep=findViewById(R.id.next_step);
        nextstep.setText("下一步");
        HorizontalStepView setpview = (HorizontalStepView) findViewById(R.id.step_view1);
        setpview
                .setStepViewTexts(stepsBeanList)//总步骤
                .setTextSize(12)//set textSize
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(this, R.color.colorPrimary))//设置 StepsViewIndicator 完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(this, R.color.colorAccent))//设置 StepsViewIndicator 未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(this, R.color.darkorange))//设置 StepsView text 完成线的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.colorPrimary))//设置 StepsView text 未完成线的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.drawable.complted))//设置 StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, R.drawable.default_icon))//设置 StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.attention));//设置 StepsViewIndicator

        ImageView iv = findViewById(R.id.right);
       iv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //选择地址界面
               gotomap();
           }
       });
        nextstep.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //发送商户基本信息
             merchantInfoPresenter.sendMerchantInfo(String.valueOf(storename.getText()), String.valueOf(typeofgood.getText()),
                     String.valueOf(phone.getText()), String.valueOf(address.getText()),addressInfo.getLatitude(),addressInfo.getLongtitude(),merchant);
           }
       });
    }

    @Override
    public void gotomap() {
        Intent intent = new Intent(MerchantInfoActivity.this, MapsActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode==RESULT_OK) {
                    addressInfo = (AddressInfo) data.getSerializableExtra("addressInfo");
                    addressname = addressInfo.getCity() + "-" + addressInfo.getDistrict() + "-" + addressInfo.getStreet();
                    address.setText(addressname);
                }
        }
    }

    @Override
    public void finishStep1(Merchant merchant) {
        Intent intent = new Intent(MerchantInfoActivity.this, Step2Activity.class);
        intent.putExtra("merchant",merchant);
        startActivity(intent);
    }
}
