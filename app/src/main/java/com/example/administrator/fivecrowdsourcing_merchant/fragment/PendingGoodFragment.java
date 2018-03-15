package com.example.administrator.fivecrowdsourcing_merchant.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.adapter.BaseQuickAdapter;
import com.example.administrator.fivecrowdsourcing_merchant.adapter.PendingGoodAdpater;
import com.example.administrator.fivecrowdsourcing_merchant.adapter.PullToRefreshAdapter;
import com.example.administrator.fivecrowdsourcing_merchant.model.DeliveryOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/10.
 */


public class PendingGoodFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeLayout;

    private PullToRefreshAdapter mPendingAdapter;//刷新适配器

    private String mUserToken;
    private String mUserId;
    private Integer mPosition;
    private boolean mBroFlag = false;
    private List<DeliveryOrder> orderList;
    private DeliveryOrder mMyOrders;

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
        View view = inflater.inflate(R.layout.fragment_pending_good, container, false);
        mRecyclerView = view.findViewById(R.id.pending_rv_list);
//        mSwipeLayout = view.findViewById(R.id.pending_swipeLayout);
        initData();
        initAdapter();
        return view;
    }

    private void initData() {
        orderList = new ArrayList<DeliveryOrder>();
        for (int i = 0; i < 2; i++) {
            DeliveryOrder order = new DeliveryOrder();
            orderList.add(order);
        }
    }

    private void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        PendingGoodAdpater adpater = new PendingGoodAdpater(orderList);
        mRecyclerView.setAdapter(adpater);

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mPendingAdapter = new PullToRefreshAdapter(getActivity(), null);
//        mPendingAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
//        mRecyclerView.setAdapter(mPendingAdapter);
//        mPendingAdapter.setOnItemChildClickListener((adapter, view, position) -> {
//                    mPosition = position;
//                    mMyOrders = (DeliveryOrder) adapter.getItem(position);
////                    showPasswordDialog(mMyOrders.businessId);
//                }
//        );
    }


    @Override
    public void onRefresh() {

    }
}
