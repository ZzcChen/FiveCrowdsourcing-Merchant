package com.example.administrator.fivecrowdsourcing_merchant.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;
import com.example.administrator.fivecrowdsourcing_merchant.presenter.EnterPresenter;
import com.example.administrator.fivecrowdsourcing_merchant.view.MainActivity;
import com.example.administrator.fivecrowdsourcing_merchant.view.MerchantInfoActivity;
import com.example.administrator.fivecrowdsourcing_merchant.view.RegisterActivity;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;

/**
 * Created by WUN、 on 2018/3/24.
 */

@SuppressLint("ValidFragment")
public class EnterFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    TextView enter;//商户入驻
    private Merchant merchant;
    private SwipeRefreshLayout mSwipeLayout;
    private EnterPresenter enterPresenter=new EnterPresenter(this);
    @SuppressLint("ValidFragment")
    public EnterFragment(Merchant merchant) {
        this.merchant = merchant;
    }

    public EnterFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter, container, false);
        enter = view.findViewById(R.id.enter);
        mSwipeLayout = view.findViewById(R.id.swipeLayout_enter);
        mSwipeLayout.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.colorPrimary});
        mSwipeLayout.setOnRefreshListener(this);
        if(merchant.getStatus().equals("1"))
        { enter.setText("待审核中请稍候");
        enter.setEnabled(false);}
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MerchantInfoActivity.class);
                intent.putExtra("merchant", merchant);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            enterPresenter.getMerchantStatus(merchant);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                // 加载完数据设置为不刷新状态，将下拉进度收起来
                mSwipeLayout.setRefreshing(false);
            }
        }, 2000);

    }

    public void updateMerchantStatus(Merchant merchant) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("merchant", merchant);
        startActivity(intent);
    }
}
