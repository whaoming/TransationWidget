package com.whming.transationwidget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.whming.transationwidget.activity.ShopCarActivity;

public class MainActivity extends AppCompatActivity {

    private Button btn_car;

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
    }
}
