package com.example.administrator.fivecrowdsourcing_merchant.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.vise.face.DetectorData;

/**
 * Created by WUN、 on 2018/3/25.
 */

public class AssesActivity extends AppCompatActivity{
    private RatingBar ratingBar = null;
    private TextView title;
    private TextView backStep;
    private TextView nextStep;
    private Button button;
    private DetectorData mDetectorData;
    private CenterDialog centerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asses_layout);

        initView();
        initData();

    }

    private void initData() {

    }

    private void initView() {
        centerDialog = new CenterDialog(this, R.layout.dialog_layout,
                new int[]{R.id.dialog_cancel, R.id.dialog_sure},"确认支付");
        centerDialog.setOnCenterItemClickListener(new CenterDialog.OnCenterItemClickListener() {
            @Override
            public void OnCenterItemClick(CenterDialog dialog, View view) {
                switch (view.getId()){
                    case R.id.dialog_sure:
                        Toast.makeText(AssesActivity.this,"支付成功！",Toast.LENGTH_SHORT).show();
                        button.setText("已支付");
//                        finish();
                        break;
                    default:

                        //onResume();

                        break;
                }
            }
        });
        ratingBar = (RatingBar) findViewById(R.id.ratingbar);
        button = findViewById(R.id.pay);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        title=findViewById(R.id.title);
        title.setText("支付及评价");
        backStep=findViewById(R.id.back_step);
        backStep.setText("返回");
        nextStep = findViewById(R.id.next_step);
        nextStep.setText("提交");
        backStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //支付对话框
                centerDialog.show();

            }
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating(rating);


            }
        });


    }
}

