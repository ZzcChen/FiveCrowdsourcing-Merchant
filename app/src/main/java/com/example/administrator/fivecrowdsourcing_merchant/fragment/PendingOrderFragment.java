package com.example.administrator.fivecrowdsourcing_merchant.fragment;

/**
 * Created by Administrator on 2018/3/9.
 */

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

import com.baidu.mapapi.map.Text;
import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.adapter.BaseQuickAdapter;
import com.example.administrator.fivecrowdsourcing_merchant.adapter.PullToRefreshAdapter;
import com.example.administrator.fivecrowdsourcing_merchant.model.DeliveryOrder;

/**
 * 待接单界面
 */

public class PendingOrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeLayout;

    private PullToRefreshAdapter mPendingAdapter;//刷新适配器

    private String mUserToken;
    private String mUserId;
    private Integer mPosition;
    private boolean mBroFlag = false;
    private DeliveryOrder mMyOrders;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_order, container, false);
        TextView textView = view.findViewById(R.id.textView);
        textView.setText("好");
//        mRecyclerView = view.findViewById(R.id.pending_rv_list);
//        mSwipeLayout = view.findViewById(R.id.pending_swipeLayout);
//        initAdapter();
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mUserToken = mBuProcessor.getUserToken();
//        mUserId = mBuProcessor.getUserId();
//        mPresenter.requestPendingOrder(mUserToken, mUserId);
    }
    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPendingAdapter = new PullToRefreshAdapter(getActivity(), null);
        mPendingAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mRecyclerView.setAdapter(mPendingAdapter);

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
//        mPresenter.requestPendingOrder(mUserToken, mUserId);
    }
}
