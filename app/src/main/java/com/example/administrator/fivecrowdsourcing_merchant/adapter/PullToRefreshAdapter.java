package com.example.administrator.fivecrowdsourcing_merchant.adapter;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.model.DeliveryOrder;
import com.example.administrator.fivecrowdsourcing_merchant.utils.ValueUtil;
import com.example.administrator.fivecrowdsourcing_merchant.view.MapsActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/3/10.
 */

public class PullToRefreshAdapter extends BaseQuickAdapter<DeliveryOrder, BaseViewHolder>{
    private final Context mContext;
    private final LocationClient mLocationClient;



    public PullToRefreshAdapter(Context context, List<DeliveryOrder> list) {
        super(R.layout.layout_animation, list);
        mContext = context;
        mLocationClient=new LocationClient(mContext.getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
    }

    @Override
    protected void convert(BaseViewHolder helper, DeliveryOrder item) {
        helper.addOnClickListener(R.id.item_get_order);
        helper.setText(R.id.order_start_point, "商户地址");
        helper.setText(R.id.order_end_point, "顾客地址");
        if (true) {
            helper.setVisible(R.id.layout_remark, true);
            helper.setText(R.id.order_remark, "备注备注");
        }
        String changeChannel = null;
//        if (item.channel != null) {
//            switch (item.channel) {
//                case "bdwm":
//                    helper.setImageBitmap(R.id.order_channel, BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.channl_baidu));
//                    changeChannel = "美团外卖";
//                    break;
//                case "eleme":
//                    helper.setImageBitmap(R.id.order_channel, BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.channl_elme));
//                    changeChannel = "饿了么";
//                    break;
//                case "fmwd":
//                    helper.setImageBitmap(R.id.order_channel, BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.channl_laoxiangji));
//                    changeChannel = "微店";
//                    break;
//                case "mtwm":
//                    helper.setImageBitmap(R.id.order_channel, BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.channl_meituan));
//                    changeChannel = "美团外卖";
//                    break;
//                default:
//                    helper.setVisible(R.id.order_channel_layout, false);
//                    break;
//            }
//            helper.setText(R.id.order_channel_id, changeChannel + " # " + item.flowid);
//        }
//
//        helper.setVisible(R.id.item_get_order, true);
//        switch (item.deliveryStatus) {
//            case 1:
//                helper.setText(R.id.item_get_order, mContext.getResources().getString(R.string.text_take_order));
//                break;
//            case 3:
//                helper.setText(R.id.item_get_order, mContext.getResources().getString(R.string.text_complete_order));
//                break;
//            case 4:
//                helper.setVisible(R.id.item_get_order, false);
//                break;
//        }
        double shopLongitude = 30.0001;//商店经纬度
        double shopLatitude = 120.0002;
        double customerLongitude = 30.01;//客户经纬度
        double customerLatitude = 120.123;
        if (true) {
            helper.setVisible(R.id.order_end_city, true);
            helper.setVisible(R.id.order_start_city, true);
            helper.setFakeBoldText(R.id.order_start_city, true);
            helper.setFakeBoldText(R.id.order_end_city, true);
            String city = "杭州";//城市信息
            //String district = mAMapLocation.getDistrict();//城区信息
            //String cityDistrict = city ;
            helper.setText(R.id.order_end_city, city);
            helper.setText(R.id.order_start_city, city);
//            double latitude = mAMapLocation.getLatitude();//获取纬度
//            double longitude = mAMapLocation.getLongitude();//获取经度
//            LatLng latLngSelf = new LatLng(latitude, longitude);
//            LatLng latLngShop = new LatLng(shopLatitude, shopLongitude);
//            LatLng latLngCustomer = new LatLng(customerLatitude, customerLongitude);
            float distance1 = (float) 0.1;
            float distance2 = (float) 1;
            helper.setText(R.id.order_start_distance, ValueUtil.formatDistance(distance1));
            helper.setText(R.id.order_end_distance, ValueUtil.formatDistance(distance2));
            helper.setFakeBoldText(R.id.order_end_distance, true);
            helper.setFakeBoldText(R.id.order_start_distance, true);
        }

    }
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //得到位置信息
            StringBuilder currtentPostion = new StringBuilder();
            currtentPostion.append(bdLocation.getCity()).append(bdLocation.getDistrict()).append(bdLocation.getStreet());
//            address.setText(currtentPostion);
//
//            addressInfo.setCity(bdLocation.getCity());
//            addressInfo.setDistrict(bdLocation.getDistrict());
//            addressInfo.setStreet(bdLocation.getStreet());
//            addressInfo.setLatitude(bdLocation.getLatitude());
//            addressInfo.setLongtitude(bdLocation.getLongitude());

//            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation
//                    || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
//                navigateTo(bdLocation);
//            }
        }


}



}
