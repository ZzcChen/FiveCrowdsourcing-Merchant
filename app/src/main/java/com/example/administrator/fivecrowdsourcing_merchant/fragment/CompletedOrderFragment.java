package com.example.administrator.fivecrowdsourcing_merchant.fragment;

import android.annotation.SuppressLint;
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
import android.widget.Button;

import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.adapter.CompletedOrderAdapter;
import com.example.administrator.fivecrowdsourcing_merchant.model.DeliveryOrder;
import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;
import com.example.administrator.fivecrowdsourcing_merchant.presenter.CompletedOrderPresenter;
import com.example.administrator.fivecrowdsourcing_merchant.view.AssesActivity;
import com.example.administrator.fivecrowdsourcing_merchant.view.TrackActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/13.
 */

@SuppressLint("ValidFragment")
public class CompletedOrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeLayout;
    public CompletedOrderPresenter completedOrderPresenter = new CompletedOrderPresenter(this);
    private ArrayList<DeliveryOrder> orderList;
    private Merchant merchant;
    private Button pendingGood;

    @SuppressLint("ValidFragment")
    public CompletedOrderFragment(Merchant merchant) {
        this.merchant = merchant;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completed_order, container, false);
        mSwipeLayout = view.findViewById(R.id.pending_swipeLayout_completed);
        mSwipeLayout.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.colorPrimary});
        mSwipeLayout.setOnRefreshListener(this);
        mRecyclerView = view.findViewById(R.id.pending_rv_list_completed);
        completedOrderPresenter.dispalyInitOrder(merchant);

        return view;
    }
    public void showRunnerAsses(DeliveryOrder  deliveryOrder) {
        Intent intent = new Intent(getActivity(), AssesActivity.class);
        intent.putExtra("deliveryOrder",deliveryOrder);
        startActivity(intent);
    }
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                completedOrderPresenter.dispalyInitOrder(merchant);
                // 加载完数据设置为不刷新状态，将下拉进度收起来
                mSwipeLayout.setRefreshing(false);
            }
        }, 2000);
    }

    public  void dispalyOrder(List<DeliveryOrder> deliveryOrderList) {
        getActivity().runOnUiThread(() -> {
            for(int i=0;i<deliveryOrderList.size();i++) {
                deliveryOrderList.get(i).setStoreAddress(merchant.getAddress());
                deliveryOrderList.get(i).setStoreName(merchant.getStorename());
            }
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(layoutManager);
            CompletedOrderAdapter adpater = new  CompletedOrderAdapter(deliveryOrderList);
            mRecyclerView.setAdapter(adpater);
        });
    }
}
