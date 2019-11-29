package com.jokerwan.aoploginsample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jokerwan.aoploginsample.utils.SharedPreferencesUtil;

public class LoginActivity extends AppCompatActivity {

    public static String LOGIN_FLAG = "login_flag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.btn_login);
        Button btnClear = findViewById(R.id.btn_clear);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                SharedPreferencesUtil.putBoolean(LoginActivity.this,LOGIN_FLAG,true);
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "清除登录状态成功！", Toast.LENGTH_SHORT).show();
                SharedPreferencesUtil.putBoolean(LoginActivity.this,LOGIN_FLAG,false);
            }
        });
    }
}
