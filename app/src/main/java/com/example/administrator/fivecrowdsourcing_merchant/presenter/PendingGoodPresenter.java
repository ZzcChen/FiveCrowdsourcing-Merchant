package com.example.administrator.fivecrowdsourcing_merchant.presenter;

import com.example.administrator.fivecrowdsourcing_merchant.fragment.PendingGoodFragment;
import com.example.administrator.fivecrowdsourcing_merchant.model.DeliveryOrder;
import com.example.administrator.fivecrowdsourcing_merchant.model.GlobalParameter;
import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/18.
 */

public class PendingGoodPresenter {
    private PendingGoodFragment pendingGoodFragment;
    public static String  URL=  GlobalParameter.URL;
    private String initservletName="DisplayGoodOrder";
    private String servletIP;
    private List<DeliveryOrder> list = new ArrayList<DeliveryOrder>();
    public PendingGoodPresenter(PendingGoodFragment pendingGoodFragment) {
        this.pendingGoodFragment = pendingGoodFragment;
    }

    public void dispalyInitOrder(Merchant merchant) {
        servletIP=URL+initservletName;
        sendRequestWithOkHttpInit(servletIP,merchant);
    }

    private void sendRequestWithOkHttpInit(String servletIP, Merchant merchant) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //转换成JSON格式
                    Gson gson = new Gson();
                    String merchantdata = gson.toJson(merchant);

                    RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                            , merchantdata);
                    Request request = new Request.Builder().
                            url(servletIP).
                            post(requestBody).
                            build();

                    OkHttpClient client = new OkHttpClient();
                    Response response = client.newCall(request).execute();

                    String jsonData= response.body().string().toString();
                    parseJSONWithJONObject(jsonData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJONObject(String jsonData) {
        Gson gson = new Gson();
        list= gson.fromJson( jsonData, new TypeToken<List<DeliveryOrder>>(){}.getType());
        pendingGoodFragment.dispalyOrder(list);
    }
}
