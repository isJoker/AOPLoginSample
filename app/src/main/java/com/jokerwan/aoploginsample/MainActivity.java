package com.jokerwan.aoploginsample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jokerwan.aoploginsample.annotation.ClickBehavior;
import com.jokerwan.aoploginsample.annotation.LoginCheck;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "JokerWan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogin = findViewById(R.id.btn_login);
        Button btnShoppingCart = findViewById(R.id.btn_shopping_cart);
        Button btnOrderDetail = findViewById(R.id.btn_order_detail);
        Button btnUserCenter = findViewById(R.id.btn_user_center);

        btnLogin.setOnClickListener(this);
        btnShoppingCart.setOnClickListener(this);
        btnOrderDetail.setOnClickListener(this);
        btnUserCenter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_shopping_cart:
                goShoppingCart();
                break;
            case R.id.btn_order_detail:
                goOrderDetail();
                break;
            case R.id.btn_user_center:
                goUserCenter();
                break;
        }
    }

    @LoginCheck
    @ClickBehavior("跳转用户中心")
    private void goUserCenter() {
        Log.e(TAG, "开始跳转到 -> 用户中心 Activity");
        startActivity(new Intent(this, OtherActivity.class));
    }

    @LoginCheck
    @ClickBehavior("跳转订单详情")
    private void goOrderDetail() {
        Log.e(TAG, "开始跳转到 -> 订单详情 Activity");
        startActivity(new Intent(this, OtherActivity.class));
    }

    @LoginCheck
    @ClickBehavior("跳转购物车")
    private void goShoppingCart() {
        Log.e(TAG, "开始跳转到 -> 购物车 Activity");
        startActivity(new Intent(this, OtherActivity.class));
    }

    @ClickBehavior("去登录")
    private void login() {
        Log.e(TAG, "开始跳转到 -> 登录 Activity");
        startActivity(new Intent(this, LoginActivity.class));
    }
}
