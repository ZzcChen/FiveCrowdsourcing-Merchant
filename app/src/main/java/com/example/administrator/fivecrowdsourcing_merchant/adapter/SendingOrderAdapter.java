package com.example.administrator.fivecrowdsourcing_merchant.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.fragment.PendingOrderFragment;
import com.example.administrator.fivecrowdsourcing_merchant.fragment.SendingOrderFragment;
import com.example.administrator.fivecrowdsourcing_merchant.model.DeliveryOrder;

import java.util.List;

/**
 * Created by Administrator on 2018/3/17.
 */

public class SendingOrderAdapter  extends RecyclerView.Adapter<SendingOrderAdapter.ViewHolder> {
    private List<DeliveryOrder> orderList;
    public SendingOrderFragment sendingOrderFragment;

    public SendingOrderAdapter(List<DeliveryOrder> orderList, SendingOrderFragment sendingOrderFragment) {
        this.orderList = orderList;
        this.sendingOrderFragment = sendingOrderFragment;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView storeName;
        TextView storeAddress;
        TextView cusAddress;
        TextView estimatedPrice;
        TextView estimatedTime;
        Button pendingGood;

        public ViewHolder(View itemView) {
            super(itemView);
            storeName= itemView.findViewById(R.id.shopName);
            storeAddress = itemView.findViewById(R.id.storeAddress);
            cusAddress = itemView.findViewById(R.id.cusAddress);
            estimatedPrice = itemView.findViewById(R.id.estimatedPrice);
            estimatedTime = itemView.findViewById(R.id.estimatedTime);
            pendingGood = itemView.findViewById(R.id.item_get_order_orderpending);
        }
    }
    @Override
    public SendingOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_pending ,parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SendingOrderAdapter.ViewHolder holder, int position) {
        DeliveryOrder deliveryOrder = orderList.get(position);
        holder.storeName.setText(deliveryOrder.getStoreName());
        holder.storeAddress.setText(deliveryOrder.getStoreAddress());
        holder.cusAddress.setText(deliveryOrder.getCusAddress());
        holder.estimatedTime.setText(deliveryOrder.getEstimatedtime()+"分钟");
        holder.estimatedPrice.setText(deliveryOrder.getEstimatedtotalprice()+"元");
        holder.pendingGood.setText("实时监控");
        //跟踪跑腿人路径
        holder.pendingGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendingOrderFragment.showRunnerTrack(deliveryOrder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
