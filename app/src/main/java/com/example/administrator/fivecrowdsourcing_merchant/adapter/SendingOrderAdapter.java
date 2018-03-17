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
        TextView startdistance;
        TextView startCity;
        TextView startAddress;
        TextView enddistance;
        TextView endCity;
        TextView endAddress;
        Button trackRunner;

        public ViewHolder(View itemView) {
            super(itemView);
            startdistance = itemView.findViewById(R.id.order_start_distance);
            startCity = itemView.findViewById(R.id.order_start_city);
            startAddress = itemView.findViewById(R.id.order_start_point);
            enddistance = itemView.findViewById(R.id.order_end_distance);
            endCity = itemView.findViewById(R.id.order_end_city);
            endAddress = itemView.findViewById(R.id.order_end_point);
            trackRunner = itemView.findViewById(R.id.item_get_order);
        }
    }
    @Override
    public SendingOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_animation, parent, false);
        SendingOrderAdapter.ViewHolder holder = new SendingOrderAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SendingOrderAdapter.ViewHolder holder, int position) {
        DeliveryOrder deliveryOrder = orderList.get(position);
        holder.startdistance.setText("0.2km");
        holder.startAddress.setText("垃圾街");
        holder.startCity.setText("杭州");
        holder.enddistance.setText("2km");
        holder.endAddress.setText("东十五");
        holder.startCity.setText("杭州");
        //跟踪跑腿人路径
        holder.trackRunner.setOnClickListener(new View.OnClickListener() {
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
