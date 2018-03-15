package com.example.administrator.fivecrowdsourcing_merchant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.adapter.PullToRefreshAdapter;


/**
 * Created by Administrator on 2018/3/13.
 */

public class SendingOrderFragment extends Fragment {


    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeLayout;

    private PullToRefreshAdapter mSendingAdapter;
    private Integer mPosition;
    private String mUserToken;
    private String mUserId;
    private boolean mBroFlag = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    private void initialize() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sending_order, container, false);
        TextView textView = view.findViewById(R.id.textView2);
        textView.setText("订单空空");
        return view;
    }

}
