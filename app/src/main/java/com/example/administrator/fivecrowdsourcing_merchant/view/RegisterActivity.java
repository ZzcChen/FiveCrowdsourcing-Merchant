package com.example.administrator.fivecrowdsourcing_merchant.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.model.GlobalParameter;
import com.example.administrator.fivecrowdsourcing_merchant.utils.RegisterThread;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  商户注册：接收用户的手机号和密码，并传递给服务器
 */
public class RegisterActivity extends AppCompatActivity implements RegisterView{
    //ServletIP
    private String servletIP= GlobalParameter.URL+"merchantRegister.do";

    private EditText phone;
    private EditText pwd;
    private EditText repwd;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        phone = findViewById(R.id.input_phone);
        pwd = findViewById(R.id.input_password);
        repwd = findViewById(R.id.input_repassword);
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断输入有效性
                boolean result = validate(phone.getText().toString(), pwd.getText().toString(), repwd.getText().toString());
                if (result) {
                    new RegisterThread(servletIP, phone.getText().toString(), pwd.getText().toString(),RegisterActivity.this).start();
                }
            }
        });

    }

    private boolean validate(String s, String s1, String s2) {
        /**
         * 移动号段正则表达式
         */
        String pat1 = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern pattern1 = Pattern.compile(pat1);
        Matcher match1 = pattern1.matcher(s);
        boolean isMatch1 = match1.matches();
        if(s.length()!= 11||!isMatch1)
        {
            phone.setError("请输入有效的手机号码!");
            return false;
        }
        if (s1.equals("")) {
            pwd.setError("密码不能为空!");
            return false;
        }
        if (s2.equals("")) {
            repwd.setError("重复密码不能为空!");
            return false;
        }
        if (!s1.equals(s2)) {
            repwd.setError("两次密码不一致!");
            return false;
        }
        return true;
    }

    @Override
    public void onSuccess() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
