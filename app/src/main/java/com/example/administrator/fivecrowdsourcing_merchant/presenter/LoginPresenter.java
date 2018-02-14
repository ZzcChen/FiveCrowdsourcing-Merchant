package com.example.administrator.fivecrowdsourcing_merchant.presenter;

import com.example.administrator.fivecrowdsourcing_merchant.model.Merchant;
import com.example.administrator.fivecrowdsourcing_merchant.view.LoginView;

import org.json.JSONException;
import org.json.JSONObject;

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
    private Merchant merchant=new Merchant();

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
                    jsonData= response.body().string().toString();
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
            merchant.setPhone(jsonObject .getString("phone"));
            merchant.setName(jsonObject .getString("name"));
            result=jsonObject.getString("result");
            if(result.equals("success")){
                loginView.onSuccess(merchant);
            }
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
