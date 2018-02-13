package com.example.administrator.fivecrowdsourcing_merchant.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.fivecrowdsourcing_merchant.MainActivity;
import com.example.administrator.fivecrowdsourcing_merchant.R;
import com.example.administrator.fivecrowdsourcing_merchant.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginView {
    public static String  URL="http://192.168.2.102:8080/FiveCrowdsourcing-Server/";
    private EditText phone;
    private EditText pwd;
    private Button login;
    private LoginPresenter loginPresenter=new LoginPresenter(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        phone = findViewById(R.id.phone);
        pwd = findViewById(R.id.password);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPresenter.Login(phone.getText().toString(),pwd.getText().toString(),URL);
            }
        });
    }

    @Override
    public void onSuccess() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}