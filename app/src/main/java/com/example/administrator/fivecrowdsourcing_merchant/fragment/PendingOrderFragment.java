package com.example.administrator.fivecrowdsourcing_merchant.fragment;

/**
 * Created by Administrator on 2018/3/9.
 */

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.adapter.PendingGoodAdpater;
import com.example.administrator.fivecrowdsourcing_merchant.adapter.PendingOrderAdpater;
import com.example.administrator.fivecrowdsourcing_merchant.adapter.PullToRefreshAdapter;
import com.example.administrator.fivecrowdsourcing_merchant.model.DeliveryOrder;
import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;
import com.example.administrator.fivecrowdsourcing_merchant.presenter.PendingOrderPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 待接单界面
 */

@SuppressLint("ValidFragment")
public class PendingOrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeLayout;
    private FloatingActionButton floatingActionButton;
    private PendingOrderPresenter pendingOrderPresenter = new PendingOrderPresenter(this);
    private Merchant merchant;

    private PullToRefreshAdapter mPendingAdapter;//刷新适配器

    private String mUserToken;
    private String mUserId;
    private Integer mPosition;
    private boolean mBroFlag = false;
    private DeliveryOrder mMyOrder;
    private ArrayList<DeliveryOrder> orderList;

    @SuppressLint("ValidFragment")
    public PendingOrderFragment(Merchant merchant) {
        this.merchant = merchant;
    }

    public PendingOrderFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_order, container, false);
        mRecyclerView = view.findViewById(R.id.pending_oreder_list);
        mSwipeLayout = view.findViewById(R.id.pending_order_swipeLayout);
        mSwipeLayout.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.colorPrimary});
        mSwipeLayout.setOnRefreshListener(this);
        floatingActionButton = view.findViewById(R.id.create_order);
        pendingOrderPresenter.dispalyInitOrder(merchant);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMyOrder = getmMyOrder();
                pendingOrderPresenter.CreaterOrder(mMyOrder);
//                initData();
//                initAdapter();
            }
        });
        return view;
    }

    //获取订单信息
    private DeliveryOrder getmMyOrder() {
        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setMerchantid(merchant.getMerchantid());
        deliveryOrder.setCusName("张同学");
        deliveryOrder.setCusAddress("杭州市西湖区浙工大");
        deliveryOrder.setCusPhone("159XXXX6907");
        deliveryOrder.setCuslat(30.230949);
        deliveryOrder.setCuslog(120.043334);
        deliveryOrder.setThings("apple * 2, orange * 3");
        deliveryOrder.setStoreName(merchant.getStorename());
        deliveryOrder.setStoreAddress(merchant.getAddress());
        return deliveryOrder;
    }

    private void initData() {
        orderList = new ArrayList<DeliveryOrder>();
        for (int i = 0; i < 2; i++) {
            DeliveryOrder order = new DeliveryOrder();
            orderList.add(order);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mUserToken = mBuProcessor.getUserToken();
//        mUserId = mBuProcessor.getUserId();
//        mPresenter.requestPendingOrder(mUserToken, mUserId);
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

//        mRecyclerView.addOnItemTouchListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
//                MyOrders orders = (MyOrders) adapter.getItem(position);
//                mPresenter.requestUpOrder(orders.businessId);
//                startActivityForResult(OrderDetailActivity.getOrderDetailIntent(getActivity(),
//                        mPendingAdapter.getItem(position), IntentConstant.ORDER_POSITION_ONE),
//                        IntentConstant.ORDER_POSITION_ONE);
//            }
//        });
//
//        mPendingAdapter.setOnItemChildClickListener((adapter, view, position) -> {
//                    mPosition = position;
//                    mMyOrders = (MyOrders) adapter.getItem(position);
//                    showPasswordDialog(mMyOrders.businessId);
//                }
//        );

        mSwipeLayout.setOnRefreshListener(this);
    }

    private void initialize() {
//        this.getComponent(Main ActivityComponent.class).inject(this);
//        mPresenter.setView(this);
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pendingOrderPresenter.dispalyInitOrder(merchant);
                // 加载完数据设置为不刷新状态，将下拉进度收起来
                mSwipeLayout.setRefreshing(false);
            }
        }, 2000);
    }

    public void dispalyOrder(List<DeliveryOrder> deliveryOrderList) {
        getActivity().runOnUiThread(() -> {
            for (int i = 0; i < deliveryOrderList.size(); i++) {
                deliveryOrderList.get(i).setStoreAddress(merchant.getAddress());
                deliveryOrderList.get(i).setStoreName(merchant.getStorename());
            }
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(layoutManager);
            PendingOrderAdpater adpater = new PendingOrderAdpater(deliveryOrderList);
            mRecyclerView.setAdapter(adpater);
        });
    }

}
