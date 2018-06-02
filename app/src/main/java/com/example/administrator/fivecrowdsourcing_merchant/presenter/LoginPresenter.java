package com.example.administrator.fivecrowdsourcing_merchant.presenter;

import android.app.Activity;
import android.widget.Toast;

import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;
import com.example.administrator.fivecrowdsourcing_merchant.view.LoginActivity;
import com.example.administrator.fivecrowdsourcing_merchant.view.LoginView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/2/13.
 */

public class LoginPresenter {
    private String servletName="LoginServlet";
    private String servletIP;
    private String result;
    private String jsonData;
    private LoginView loginView;//loginView接口
    private Merchant merchant=new Merchant();

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }


    public void Login(String phone,String password,String url) throws Exception{

//        //特殊通道，当服务器不行时直接登陆
//        merchant.setName("zzc");
//        merchant.setStatus("2");
//        loginView.onSuccess(merchant);
//        servletIP=url+servletName;
        try {
            sendRequestWithOkHttp(servletIP,phone,password);
        } catch ( SocketTimeoutException e) {
            e.printStackTrace();
             throw new SocketTimeoutException();
        }

    }

    private void sendRequestWithOkHttp(final String servletIP, final String phone,final String password) throws Exception {
        try {
            RequestBody requestBody = new FormBody.Builder().
                    add("phone", phone).add("password", password).build();

            /**超时设置*/
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)//设置写的超时时间
                    .build();


           /* //请求超时设置
            client.newBuilder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(10,TimeUnit.SECONDS)//设置写的超时时间
             .build();*/


            Request request = new Request.Builder().
                    url(servletIP).
                    post(requestBody).
                    build();
            Response response = client.newCall(request).execute();

            jsonData = response.body().string().toString();
            parseJSONWithJONObject(jsonData);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            throw new SocketTimeoutException();



        }
    }


    private void parseJSONWithJONObject(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            result=jsonObject.getString("result");
            if(result.equals("success")){
                if (jsonObject .getString("status").equals("0")) {
                    merchant.setMerchantid(jsonObject.getLong("merchantid"));
                    merchant.setPhone(jsonObject .getString("phone"));
                    merchant.setStatus(jsonObject.getString("status"));
                } else{
                    merchant.setPhone(jsonObject .getString("phone"));
                    merchant.setName(jsonObject .getString("name"));
                    merchant.setMerchantid(jsonObject.getLong("merchantid"));
                    merchant.setStorename(jsonObject.getString("storename"));
                    merchant.setAddress(jsonObject.getString("address"));
                    merchant.setMargin(jsonObject.getLong("margin"));
                    merchant.setLongitude(jsonObject.getDouble("longitude"));
                    merchant.setLatitude(jsonObject.getDouble("latitude"));
                    merchant.setStatus(jsonObject.getString("status"));
                }
                loginView.onSuccess(merchant);
            }else
                loginView.onFailed();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
