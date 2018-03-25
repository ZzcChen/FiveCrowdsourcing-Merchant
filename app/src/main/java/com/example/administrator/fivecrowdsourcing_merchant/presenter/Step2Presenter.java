package com.example.administrator.fivecrowdsourcing_merchant.presenter;

import com.example.administrator.fivecrowdsourcing_merchant.model.GlobalParameter;
import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;
import com.example.administrator.fivecrowdsourcing_merchant.view.Step2View;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/9.
 */

public class Step2Presenter {
    private Step2View step2View;
    private Merchant merchant = new Merchant();
    public static String  URL=  GlobalParameter.URL;
    private String servletName="Step2Servlet";
    private String servletIP;

    public Step2Presenter(Step2View step2View) {
        this.step2View = step2View;
    }

    public void sendImage(String buslicensefilepath, String foodbuslicense, Merchant merchant) {
        this.merchant = merchant;
        this.merchant.setBuslicensephoto(".."+"\\"+"merchantImages" + "\\" + merchant.getMerchantid() + "\\" + buslicensefilepath);
        this.merchant.setFoodbuslicensephoto(".."+"\\"+"merchantImages" + "\\" + merchant.getMerchantid()+"\\"+foodbuslicense);
        step2View.finishStep2(merchant);
    }
}
