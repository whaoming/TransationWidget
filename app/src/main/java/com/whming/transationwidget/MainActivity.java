package com.whming.transationwidget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.whming.transationwidget.detail.DetailActivity;
import com.whming.transationwidget.shopcart.ShopCarActivity;
import com.whming.transationwidget.shopcart.AnimShopCartActivity;
import com.whming.transationwidget.print.PriviewActivity;

/**
* author: whming
* github: https://github.com/whaoming
* date: 2017/10/12
* TODO: 主页面 入口页面
* remark: nothing
*/
public class MainActivity extends AppCompatActivity {

    private Button btn_car,btn_detail,btn_print,btn_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_car = (Button) findViewById(R.id.btn_car);
        btn_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShopCarActivity.class);
                startActivity(intent);
            }
        });
        btn_detail = (Button) findViewById(R.id.btn_detail);
        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });

        btn_print = (Button) findViewById(R.id.btn_print);
        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PriviewActivity.class);
                startActivity(intent);
            }
        });

        btn_key = (Button) findViewById(R.id.btn_key);
        btn_key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AnimShopCartActivity.class);
                startActivity(intent);
            }
        });
    }
}
