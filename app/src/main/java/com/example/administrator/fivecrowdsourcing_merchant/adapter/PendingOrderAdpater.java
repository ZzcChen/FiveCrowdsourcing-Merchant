package com.example.administrator.fivecrowdsourcing_merchant.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.model.DeliveryOrder;

import java.util.List;

/**
 * Created by Administrator on 2018/3/16.
 */

public class PendingOrderAdpater extends RecyclerView.Adapter<PendingOrderAdpater.ViewHolder>{

    private List<DeliveryOrder> orderList;
    public PendingOrderAdpater(List<DeliveryOrder> orderList) {
        this.orderList = orderList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView storeName;
        TextView storeAddress;
        TextView cusAddress;
        TextView estimatedPrice;
        TextView estimatedTime;

        public ViewHolder(View itemView) {
            super(itemView);
            storeName= itemView.findViewById(R.id.shopName);
            storeAddress = itemView.findViewById(R.id.storeAddress);
            cusAddress = itemView.findViewById(R.id.cusAddress);
            estimatedPrice = itemView.findViewById(R.id.estimatedPrice);
            estimatedTime = itemView.findViewById(R.id.estimatedTime);
        }
    }
    @Override
    public PendingOrderAdpater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_pending, parent, false);
        PendingOrderAdpater.ViewHolder holder = new PendingOrderAdpater.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PendingOrderAdpater.ViewHolder holder, int position) {
        DeliveryOrder deliveryOrder = orderList.get(position);
        holder.storeName.setText(deliveryOrder.getStoreName());
        holder.storeAddress.setText(deliveryOrder.getStoreAddress());
        holder.cusAddress.setText(deliveryOrder.getCusAddress());
        holder.estimatedTime.setText(deliveryOrder.getEstimatedtime()+"分钟");
        holder.estimatedPrice.setText(deliveryOrder.getEstimatedtotalprice()+"元");
    }

    @Override
    public int getItemCount(){return orderList.size();
    }
}
