package com.example.administrator.fivecrowdsourcing_merchant.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.administrator.fivecrowdsourcing_merchant.adapter.PendingOrderAdpater;
import com.example.administrator.fivecrowdsourcing_merchant.adapter.PullToRefreshAdapter;
import com.example.administrator.fivecrowdsourcing_merchant.model.DeliveryOrder;
import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;
import com.example.administrator.fivecrowdsourcing_merchant.presenter.PendingGoodPresenter;
import com.example.administrator.fivecrowdsourcing_merchant.presenter.PendingOrderPresenter;
import com.example.administrator.fivecrowdsourcing_merchant.view.FaceDetectorActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/10.
 */


@SuppressLint("ValidFragment")
public class PendingGoodFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeLayout;
    private Merchant merchant;
    private PendingGoodPresenter pendingGoodPresenter = new PendingGoodPresenter(this);
    private PullToRefreshAdapter mPendingAdapter;//刷新适配器

    private String mUserToken;
    private String mUserId;
    private Integer mPosition;
    private boolean mBroFlag = false;
    private DeliveryOrder mMyOrders;
    private ArrayList<DeliveryOrder> orderList;

    @SuppressLint("ValidFragment")
    public PendingGoodFragment(Merchant merchant) {
        this.merchant = merchant;
    }

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
        pendingGoodPresenter.dispalyInitOrder(merchant);
        mSwipeLayout = view.findViewById(R.id.pending_swipeLayout);
        mSwipeLayout.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.colorPrimary});
        mSwipeLayout.setOnRefreshListener(this);
//        mSwipeLayout = view.findViewById(R.id.pending_swipeLayout);
//        initData();
//        initAdapter();
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pendingGoodPresenter.dispalyInitOrder(merchant);
                // 加载完数据设置为不刷新状态，将下拉进度收起来
                mSwipeLayout.setRefreshing(false);
            }
        }, 2000);
    }


    public void dispalyOrder(List<DeliveryOrder> deliveryOrderList) {
        getActivity().runOnUiThread(() -> {
            for(int i=0;i<deliveryOrderList.size();i++) {
                deliveryOrderList.get(i).setStoreAddress(merchant.getAddress());
                deliveryOrderList.get(i).setStoreName(merchant.getStorename());
            }
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(layoutManager);
           // PendingGoodAdpater adpater = new PendingGoodAdpater(deliveryOrderList);
            PendingGoodAdpater adpater = new PendingGoodAdpater(deliveryOrderList,this);
            mRecyclerView.setAdapter(adpater);
        });
    }

    public void showViseFace() {
        Intent intent = new Intent(getActivity(), FaceDetectorActivity.class);
        startActivity(intent);
        //答辩演示专用代码
        List<DeliveryOrder> deliveryOrders = new ArrayList<>();

        dispalyOrder(deliveryOrders);
    }
}
