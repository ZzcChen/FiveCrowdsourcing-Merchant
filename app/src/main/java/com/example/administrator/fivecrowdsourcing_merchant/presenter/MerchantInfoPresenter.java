package com.example.administrator.fivecrowdsourcing_merchant.presenter;

import android.text.Editable;

import com.example.administrator.fivecrowdsourcing_merchant.model.GlobalParameter;
import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;
import com.example.administrator.fivecrowdsourcing_merchant.view.MerchantInfoView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/7.
 */

public class MerchantInfoPresenter {
    private Merchant merchant=new Merchant();
    public static String  URL=  GlobalParameter.URL;
    private String servletIP;
    private String servletName="Step1Servlet";
    private MerchantInfoView merchantInfoView;

    public MerchantInfoPresenter(MerchantInfoView merchantInfoView) {
        this.merchantInfoView = merchantInfoView;
    }

    public void sendMerchantInfo(String storename, String typeofgood, String phone, String address, double latitude, double longtitude, Merchant merchant) {
        this.merchant.setMerchantid(merchant.getMerchantid());
        this.merchant.setStorename(storename);
        this.merchant.setTofgid((long) 1);//模拟配送类型为1
        this.merchant.setPhone(phone);
        this.merchant.setAddress(address);
        this.merchant.setLatitude(latitude);
        this.merchant.setLongitude(longtitude);
        merchantInfoView.finishStep1(this.merchant);
    }
}
