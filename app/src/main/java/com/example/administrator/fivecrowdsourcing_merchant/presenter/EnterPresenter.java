package com.example.administrator.fivecrowdsourcing_merchant.presenter;

import com.example.administrator.fivecrowdsourcing_merchant.fragment.EnterFragment;
import com.example.administrator.fivecrowdsourcing_merchant.model.GlobalParameter;
import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by WUN、 on 2018/3/24.
 */

public class EnterPresenter {
    private EnterFragment enterFragment;
    private String servletName="MerchantStatus";
    private String servletIP;
    private String jsonData;
    private String result;
    private Merchant merchant;

    public EnterPresenter(EnterFragment enterFragment) {
        this.enterFragment = enterFragment;
    }


    public void getMerchantStatus(Merchant merchant) throws IOException, JSONException {
        this.merchant = merchant;
        servletIP = GlobalParameter.URL + servletName;
        sendRequestWithOkHttp(servletIP, merchant);
    }

    private void sendRequestWithOkHttp(String servletIP, Merchant merchant) throws IOException, JSONException {
        RequestBody requestBody = new FormBody.Builder().add("merchantId",
                String.valueOf(merchant.getMerchantid())).build();
        Request request = new Request.Builder().
                url(servletIP).
                post(requestBody).
                build();
        /**超时设置*/
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)//设置写的超时时间
                .build();
        Response response = client.newCall(request).execute();
        jsonData = response.body().string().toString();
        parseJSONWithJONObject(jsonData);

    }

    private void parseJSONWithJONObject(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        result=jsonObject.getString("result");
        if (result.equals("success")) {
            merchant.setStatus(jsonObject.getString("status"));
            enterFragment.updateMerchantStatus(merchant);
        }
    }
}
