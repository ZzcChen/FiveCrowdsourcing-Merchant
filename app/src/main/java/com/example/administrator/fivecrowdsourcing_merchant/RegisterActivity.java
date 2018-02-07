package com.example.administrator.fivecrowdsourcing_merchant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 *  商户注册：接收用户的手机号和密码，并传递给服务器
 */
public class RegisterActivity extends AppCompatActivity {
    //ServletIP
    private String servletIP="http://192.168.2.105:8080/FiveCrowdsourcing-Server/RegisterServlet";

    private EditText phone;
    private EditText pwd;
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        phone = findViewById(R.id.phone);
        pwd = findViewById(R.id.password);
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RegisterThread(servletIP, phone.getText().toString(), pwd.getText().toString()).start();
            }
        });

    }
}
