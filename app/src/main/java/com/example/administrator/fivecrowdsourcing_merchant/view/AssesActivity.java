package com.example.administrator.fivecrowdsourcing_merchant.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.administrator.fivecrowdsourcing_merchant.R;

/**
 * Created by WUN、 on 2018/3/25.
 */

public class AssesActivity extends AppCompatActivity{
    private RatingBar ratingBar = null;
    private TextView title;

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
        ratingBar = (RatingBar) findViewById(R.id.ratingbar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating(rating);

                Log.i("RatingBar", "当前评分: " + rating + "fromUser: " + fromUser);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title=findViewById(R.id.title);
        title.setText("评分");
    }
}

