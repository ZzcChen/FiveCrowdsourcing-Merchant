package com.example.administrator.fivecrowdsourcing_merchant.presenter;

import com.example.administrator.fivecrowdsourcing_merchant.model.GlobalParameter;
import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;

import com.example.administrator.fivecrowdsourcing_merchant.view.Step3View;
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

public class Step3Presenter {

    private Step3View step3View;
    private Merchant merchant = new Merchant();
    public static String  URL=  GlobalParameter.URL;
    private String servletName="MerchantInfoServlet";
    private String servletIP;

    public Step3Presenter(Step3View step3View) {
        this.step3View = step3View;
    }

    public void sendImage(String name, String idcardnumber, Merchant merchant,String filename) {
        this.merchant = merchant;
        merchant.setName(name);
        merchant.setIdcardnumber(idcardnumber);
        merchant.setIdcardphoto("images" + "\\" + merchant.getMerchantid() + "\\" + filename);
        servletIP=URL+servletName;
        sendRequestWithOkHttp(this.merchant,servletIP);
    }

    private void sendRequestWithOkHttp(final Merchant merchant, String servletIP) {
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
                            url(Step3Presenter.this.servletIP).
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
                step3View.finishStep3(merchant);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
