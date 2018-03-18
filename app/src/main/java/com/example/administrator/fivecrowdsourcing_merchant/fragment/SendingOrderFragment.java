package com.example.administrator.fivecrowdsourcing_merchant.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.adapter.PendingGoodAdpater;
import com.example.administrator.fivecrowdsourcing_merchant.adapter.PullToRefreshAdapter;
import com.example.administrator.fivecrowdsourcing_merchant.adapter.SendingOrderAdapter;
import com.example.administrator.fivecrowdsourcing_merchant.model.DeliveryOrder;
import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;
import com.example.administrator.fivecrowdsourcing_merchant.presenter.SendingOrderPresenter;
import com.example.administrator.fivecrowdsourcing_merchant.view.LoginActivity;
import com.example.administrator.fivecrowdsourcing_merchant.view.MainActivity;
import com.example.administrator.fivecrowdsourcing_merchant.view.TrackActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/3/13.
 */

@SuppressLint("ValidFragment")
public class SendingOrderFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeLayout;
    private Merchant merchant;

    private PullToRefreshAdapter mPendingAdapter;//刷新适配器
    private SendingOrderPresenter sendingOrderPresenter=new SendingOrderPresenter(this);

    private String mUserToken;
    private String mUserId;
    private Integer mPosition;
    private boolean mBroFlag = false;
    private List<DeliveryOrder> orderList;
    private DeliveryOrder mMyOrders;

    @SuppressLint("ValidFragment")
    public SendingOrderFragment(Merchant merchant) {
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
        View view = inflater.inflate(R.layout.fragment_sending_order, container, false);
        mRecyclerView = view.findViewById(R.id.pending_rv_list_sending);
        sendingOrderPresenter.dispalyInitOrder(merchant);
//        mSwipeLayout = view.findViewById(R.id.pending_swipeLayout);
//        initData();
//        initAdapter();
        return view;
    }

    private void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        SendingOrderAdapter adpater = new SendingOrderAdapter(orderList,this);
        mRecyclerView.setAdapter(adpater);
    }

    private void initData() {
        orderList = new ArrayList<DeliveryOrder>();
        for (int i = 0; i < 2; i++) {
            DeliveryOrder order = new DeliveryOrder();
            order.setCuslat(30.225809);
            order.setCuslog(120.03688);
            orderList.add(order);
        }

    }

    public void showRunnerTrack(DeliveryOrder deliveryOrder) {
        Intent intent = new Intent(getActivity(), TrackActivity.class);
        intent.putExtra("deliveryOrder",deliveryOrder);
        startActivity(intent);
    }

    public void dispalyOrder(List<DeliveryOrder> deliveryOrderList) {
        getActivity().runOnUiThread(() -> {
            for(int i=0;i<deliveryOrderList.size();i++) {
                deliveryOrderList.get(i).setStoreAddress(merchant.getAddress());
                deliveryOrderList.get(i).setStoreName(merchant.getStorename());
                deliveryOrderList.get(i).setStoreLat(merchant.getLatitude());
                deliveryOrderList.get(i).setStoreLog(merchant.getLongitude());
            }
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(layoutManager);
            SendingOrderAdapter adpater = new SendingOrderAdapter(deliveryOrderList,this);
            mRecyclerView.setAdapter(adpater);
        });
    }
}
