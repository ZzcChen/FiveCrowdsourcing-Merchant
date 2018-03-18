package com.example.administrator.fivecrowdsourcing_merchant.utils;

import com.example.administrator.fivecrowdsourcing_merchant.view.RegisterView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Fitz on 2018/2/7.
 */

public class RegisterThread extends Thread {
    String url;
    String phone;
    String password;
    private RegisterView registerView;

    public RegisterThread() {
    }

    public RegisterThread(String url, String phone, String password,RegisterView registerView) {
        this.url = url;
        this.phone = phone;
        this.password = password;
        this.registerView = registerView;
    }

    private String doGet() throws IOException {
        /*将username和password传给Tomcat服务器*/
        url=url+"?phone="+phone+"&password="+password;
        try {
            URL httpUrl = new URL(url);
            /*获取网络连接*/
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            /*设置请求方法为GET方法*/
            conn.setRequestMethod("GET");
            /*设置访问超时时间*/
            conn.setReadTimeout(5000);
            BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str;
            StringBuffer sb=new StringBuffer();
            //读取服务器返回的信息
            while((str=reader.readLine())!=null)
            {
                sb.append(str);
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        }

    }

    /*在run中调用doGet*/
    @Override
    public void run() {
        try {
          String result =doGet();
          if(result.equals("success")){
              registerView.onSuccess();
          }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
