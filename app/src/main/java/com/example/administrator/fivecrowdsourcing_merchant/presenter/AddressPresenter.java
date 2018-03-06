package com.example.administrator.fivecrowdsourcing_merchant.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.administrator.fivecrowdsourcing_merchant.model.Address;
import com.example.administrator.fivecrowdsourcing_merchant.view.MerchantInfoActivity;
import com.example.administrator.fivecrowdsourcing_merchant.view.MerchantInfoView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/6.
 */

public class AddressPresenter {
    private Address address=new Address();
    private MerchantInfoView merchantInfoView;//商户入驻接口

    public AddressPresenter(MerchantInfoView merchantInfoView) {
        this.merchantInfoView = merchantInfoView;
    }

    //判断权限是否开启
   public void isPermissions() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission((Context) merchantInfoView,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission((Context) merchantInfoView,
                Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission((Context) merchantInfoView,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions((Activity) merchantInfoView, permissions, 1);
        } else {
            //requestLocation();
        }
    }
    //选择地址
    void chooseAddress() {

    }
}
