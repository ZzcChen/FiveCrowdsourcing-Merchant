package com.example.administrator.fivecrowdsourcing_merchant.presenter;

import com.example.administrator.fivecrowdsourcing_merchant.fragment.PendingOrderFragment;
import com.example.administrator.fivecrowdsourcing_merchant.model.DeliveryOrder;
import com.example.administrator.fivecrowdsourcing_merchant.model.GlobalParameter;
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
 * Created by Administrator on 2018/3/16.
 */

public class CreateOrderPresenter {
    private DeliveryOrder deliveryOrder=new DeliveryOrder();
    public static String  URL=  GlobalParameter.URL;
    private String servletName="AddDelOrderServlet";
    private String servletIP;
    public PendingOrderFragment pendingOrderFragment;
    public CreateOrderPresenter(PendingOrderFragment pendingOrderFragment) {
        this.pendingOrderFragment = pendingOrderFragment;
    }
//    public PendingOrderFragment pendingOrderFragment = new PendingOrderFragment();

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

    private void parseJSONWithJONObject(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            String result=jsonObject.getString("result");
            if(result.equals("success")){
                double estimatedtotalprice = jsonObject.getDouble("estimatedtotalprice");//预估价格
                Long estimatedtime = jsonObject.getLong("estimatedtime");//预估时间
                deliveryOrder.setEstimatedtime(estimatedtime);
                deliveryOrder.setEstimatedtotalprice(estimatedtotalprice);
                pendingOrderFragment.dispalyOrder(deliveryOrder);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
