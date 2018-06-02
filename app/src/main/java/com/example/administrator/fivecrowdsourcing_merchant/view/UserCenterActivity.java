package com.example.administrator.fivecrowdsourcing_merchant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;


/**
 * Created by WUN、 on 2018/3/25.
 */

public class UserCenterActivity extends AppCompatActivity {
    private TextView title;
    private TextView backStep;
    private TextView nextStep;
    private Merchant merchant = new Merchant();
    private SuperTextView userInfo;
    private SuperTextView userBalance;
    private CenterDialog centerDialog;
    private SuperTextView charge;
    private int type;
    private static final int CHOOSE_IDCARDOPPO = 1;
    private static final int CHOOSE_IDCARDINHAND =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case 0:
                setContentView(R.layout.user_center);
                break;
            case 1:
                setContentView(R.layout.wallet_layout);
                break;
        }
        merchant = (Merchant) getIntent().getSerializableExtra("merchant");
        initView(type);
        initData(type);

    }

    private void initView(int type) {
        switch(type){
            case 0:
                title=findViewById(R.id.title);
                title.setText("个人中心");
                userInfo = (SuperTextView) findViewById(R.id.user_info);
                backStep=findViewById(R.id.back_step);
                backStep.setText("返回");
                backStep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

                ((SuperTextView)findViewById(R.id.my_wallet)).setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
                    @Override
                    public void onClickListener(SuperTextView superTextView) {
                        String string = "前往我的钱包";
                        Toast.makeText(UserCenterActivity.this, string, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserCenterActivity.this,UserCenterActivity.class);
                        intent.putExtra("type",1);
                        intent.putExtra("merchant", merchant);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                        finish();
                        startActivity(intent);
                    }
                });
                break;
            case 1:
                title=findViewById(R.id.title);
                title.setText("我的钱包");
                userBalance = (SuperTextView) findViewById(R.id.user_balance);
                nextStep=findViewById(R.id.next_step);
                nextStep.setText("明细");
                charge=findViewById(R.id.charge);

                centerDialog = new CenterDialog(UserCenterActivity.this, R.layout.dialog_layout,   new int[]{R.id.dialog_cancel, R.id.dialog_sure},"是否缴纳保证金");


//                nextStep.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        finish();
//                    }
//                });

        }


    }

    private void initData(int type) {
        switch (type) {
            case 0:
                userInfo.setLeftString(merchant.getName());
                userInfo.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
                    @Override
                    public void onClickListener(SuperTextView superTextView) {
                        String string = "整个item的点击事件";
                        Toast.makeText(UserCenterActivity.this, string, Toast.LENGTH_SHORT).show();
                    }
                }).setRightTvClickListener(new SuperTextView.OnRightTvClickListener() {
                    @Override
                    public void onClickListener() {
                        String string = "前往认证";
                        Toast.makeText(UserCenterActivity.this, string, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserCenterActivity.this, MerchantInfoActivity.class);
                        intent.putExtra("merchant", merchant);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }
                });
                break;
            case 1:
                charge.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
                    @Override
                    public void onClickListener(SuperTextView superTextView) {
                        centerDialog.show();
                        centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
                            @Override
                            public void OnCenterItemClick(CenterDialog dialog, View view) {
                                switch (view.getId()) {
                                    case R.id.dialog_sure:
                                        Toast.makeText(UserCenterActivity.this, "缴纳成功", Toast.LENGTH_SHORT).show();
                                        userBalance.setLeftBottomString("200.00");
                                        merchant.setMargin(Long.valueOf(1));
                                        break;
                                    default:
                                        Toast.makeText(UserCenterActivity.this, "下次再缴纳", Toast.LENGTH_SHORT).show();
                                        //onResume();

                                        break;
                                }
                            }
                        });
                    }
                });
                //userBalance.setLeftBottomString(merchant.getBalance().toString());
                break;
        }
    }
}
