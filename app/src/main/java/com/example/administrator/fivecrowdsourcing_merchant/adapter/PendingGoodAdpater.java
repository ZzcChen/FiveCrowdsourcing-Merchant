package com.example.administrator.fivecrowdsourcing_merchant.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.model.DeliveryOrder;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2018/3/13.
 */

public class PendingGoodAdpater extends RecyclerView.Adapter<PendingGoodAdpater.ViewHolder> {
    private List<DeliveryOrder> orderList;

    public PendingGoodAdpater(List<DeliveryOrder> orderList) {
        this.orderList = orderList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView startdistance;
        TextView startCity;
        TextView startAddress;
        TextView enddistance;
        TextView endCity;
        TextView endAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            startdistance = itemView.findViewById(R.id.order_start_distance);
            startCity = itemView.findViewById(R.id.order_start_city);
            startAddress = itemView.findViewById(R.id.order_start_point);
            enddistance = itemView.findViewById(R.id.order_end_distance);
            endCity = itemView.findViewById(R.id.order_end_city);
            endAddress = itemView.findViewById(R.id.order_end_point);
        }
    }


    public PendingGoodAdpater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_animation, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PendingGoodAdpater.ViewHolder holder, int position) {
        DeliveryOrder deliveryOrder = orderList.get(position);
        holder.startdistance.setText("0.2km");
        holder.startAddress.setText("垃圾街");
        holder.startCity.setText("杭州");
        holder.enddistance.setText("2km");
        holder.endAddress.setText("东十五");
        holder.startCity.setText("杭州");
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}

