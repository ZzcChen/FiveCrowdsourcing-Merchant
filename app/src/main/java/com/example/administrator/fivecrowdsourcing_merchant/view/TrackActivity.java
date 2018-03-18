package com.example.administrator.fivecrowdsourcing_merchant.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.model.DeliveryOrder;

import java.util.ArrayList;
import java.util.List;

public class TrackActivity extends AppCompatActivity {
    private BaiduMap baiduMap;
    public LocationClient mLocationClient;
    private com.baidu.mapapi.map.MapView mapView;
    private DeliveryOrder deliveryOrder;
    private boolean isFirstLocate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new TrackActivity.MyLocationListener());
        //初始化地图
        SDKInitializer.initialize(getApplicationContext());
        SDKInitializer.setCoordType(CoordType.GCJ02);
        setContentView(R.layout.activity_track);
        intView();
    }

    private void intView() {
        deliveryOrder = (DeliveryOrder) getIntent().getSerializableExtra("deliveryOrder");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        mapView = findViewById(R.id.mapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        //权限申请
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(TrackActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(TrackActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(TrackActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(TrackActivity.this, permissions, 1);
        } else {
            requestLocation();
        }
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        //option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);//使用GPS定位
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须统一所有权限才能使用本程序", Toast.LENGTH_SHORT).
                                    show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation
                    || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(bdLocation);
            }
        }
    }

    //移动到跑腿人的位置
    private void navigateTo(BDLocation bdLocation) {
        if (isFirstLocate) {
            com.baidu.mapapi.model.LatLng ll = new com.baidu.mapapi.model.LatLng(deliveryOrder.getCuslat(), deliveryOrder.getCuslog());
            MapStatusUpdate update = MapStatusUpdateFactory.zoomTo(15f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
                    baiduMap.animateMapStatus(update);
                }
            }, 400);    //延时1s执行
        }
        //创建OverlayOptions的集合
        List<OverlayOptions> options = new ArrayList<OverlayOptions>();
        //设置坐标点
        LatLng point2 = new LatLng(deliveryOrder.getCuslat(), deliveryOrder.getCuslog());
        LatLng point1 = new LatLng(deliveryOrder.getStoreLat(), deliveryOrder.getCuslog());
       //创建OverlayOptions属性
        //构建Marker图标
        BitmapDescriptor startbitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_start_point);
        BitmapDescriptor endbitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_end_point);
        OverlayOptions option1 = new MarkerOptions()
                .position(point1)
                .icon(startbitmap);
        OverlayOptions option2 = new MarkerOptions()
                .position(point2)
                .icon(endbitmap);
       //将OverlayOptions添加到list
        options.add(option1);
        options.add(option2);
        //在地图上批量添加
        baiduMap.addOverlays(options);
        //显示跑腿人位置
        MyLocationData.Builder locationbuilder = new MyLocationData.Builder();
        locationbuilder.latitude(deliveryOrder.getCuslat());
        locationbuilder.longitude(deliveryOrder.getCuslog());
        MyLocationData locationdata = locationbuilder.build();
        baiduMap.setMyLocationData(locationdata);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }
}
