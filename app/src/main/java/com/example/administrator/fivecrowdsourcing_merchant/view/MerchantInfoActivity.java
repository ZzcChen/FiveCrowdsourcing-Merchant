package com.example.administrator.fivecrowdsourcing_merchant.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.model.AddressInfo;
import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;
import com.example.administrator.fivecrowdsourcing_merchant.presenter.MerchantInfoPresenter;

public class MerchantInfoActivity extends AppCompatActivity implements MerchantInfoView{
    private TextView title;
    private EditText storename;
    private  EditText phone;;
    private  EditText typeofgood;
    private  TextView address;
    private String addressname;
    private Button firststep;
    private  AddressInfo addressInfo;
    private Merchant merchant = new Merchant();
    MerchantInfoPresenter merchantInfoPresenter=new MerchantInfoPresenter(this  );

    public MerchantInfoActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_info);
        initView();
    }

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
        firststep = findViewById(R.id.first_step);
        title = findViewById(R.id.title);
        title.setText("商家信息认证");
        ImageView iv = findViewById(R.id.right);
       iv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //选择地址界面
               gotomap();
           }
       });
       firststep.setOnClickListener(new View.OnClickListener() {
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
