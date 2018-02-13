package com.example.administrator.fivecrowdsourcing_merchant.presenter;

import android.content.Intent;

import com.example.administrator.fivecrowdsourcing_merchant.MainActivity;
import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;
import com.example.administrator.fivecrowdsourcing_merchant.view.LoginView;

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

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    private LoginView loginView;//loginView接口
    public void Login(String phone,String password,String url){
        servletIP=url+servletName;
      sendRequestWithOkHttp(servletIP,phone,password);
    }

    private void sendRequestWithOkHttp(final String servletIP, final String phone,final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RequestBody requestBody = new FormBody.Builder().
                            add("phone", phone).add("password", password).build();
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().
                            url(servletIP).
                            post(requestBody).
                            build();
                    Response response = client.newCall(request).execute();
                    result = response.body().string().toString();
                    if ("success".equals(result) ) {
                        loginView.onSuccess();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
