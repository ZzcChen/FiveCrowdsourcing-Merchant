package com.example.administrator.fivecrowdsourcing_merchant.listener;

import android.view.View;

import com.example.administrator.fivecrowdsourcing_merchant.adapter.BaseQuickAdapter;

/**
 * Created by Administrator on 2018/3/10.
 * A convenience class to extend when you only want to OnItemChildClickListener for a subset
 * of all the SimpleClickListener. This implements all methods in the
 */

public abstract class OnItemChildClickListener extends SimpleClickListener{
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        onSimpleItemChildClick(adapter, view, position);
    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    public  abstract void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position);
}
