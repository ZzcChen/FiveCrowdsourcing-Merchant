package com.example.administrator.fivecrowdsourcing_merchant.presenter;

import com.example.administrator.fivecrowdsourcing_merchant.fragment.PendingOrderFragment;
import com.example.administrator.fivecrowdsourcing_merchant.model.DeliveryOrder;
import com.example.administrator.fivecrowdsourcing_merchant.model.GlobalParameter;
import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/16.
 */

public class PendingOrderPresenter {
    private DeliveryOrder deliveryOrder=new DeliveryOrder();
    public static String  URL=  GlobalParameter.URL;
    private String servletName="AddDelOrderServlet";
    private String initservletName = "DisplayDelOrderServlet";
    private String servletIP;
    private List<DeliveryOrder> list = new ArrayList<DeliveryOrder>();
    public PendingOrderFragment pendingOrderFragment;
    public PendingOrderPresenter(PendingOrderFragment pendingOrderFragment) {
        this.pendingOrderFragment = pendingOrderFragment;
    }

    //创建订单(客户订单)
    public void CreaterOrder(DeliveryOrder deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
        servletIP=URL+servletName;
        sendRequestWithOkHttp(this.deliveryOrder,servletIP);
    }

    private void sendRequestWithOkHttp(DeliveryOrder deliveryOrder, String servletIP) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //转换成JSON格式
                    Gson gson = new Gson();
                    String Orderdata = gson.toJson(deliveryOrder);

                    RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                            , Orderdata);
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

    private void parseJSONWithJONObject(String jsonData) throws JSONException {
        Gson gson = new Gson();
        list= gson.fromJson( jsonData, new TypeToken<List<DeliveryOrder>>(){}.getType());
        pendingOrderFragment.dispalyOrder(list);
//            JSONObject jsonObject = new JSONObject(jsonData);
//            String result=jsonObject.getString("result");
//            if(result.equals("success")){
//                double estimatedtotalprice = jsonObject.getDouble("estimatedtotalprice");//预估价格
//                Long estimatedtime = jsonObject.getLong("estimatedtime");//预估时间
//                deliveryOrder.setEstimatedtime(estimatedtime);
//                deliveryOrder.setEstimatedtotalprice(estimatedtotalprice);
//                pendingOrderFragment.dispalyOrder(deliveryOrder);
//            }
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
}
