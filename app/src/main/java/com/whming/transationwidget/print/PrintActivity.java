package com.whming.transationwidget.print;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.whming.transationwidget.R;

public class PrintActivity extends AppCompatActivity {

    private Button btn_band;
    private Button btn_priview;
    private TextView tv_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        btn_band = (Button) findViewById(R.id.btn_band);
        btn_priview = (Button) findViewById(R.id.btn_priview);
        tv_state = (TextView) findViewById(R.id.tv_state);

        btn_band.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goChooseBuleTouth();
            }
        });

        btn_priview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goPriview();
            }
        });
    }

    /**
     * 去预览
     */
    private void goPriview() {
        Intent intent = new Intent(this,PriviewActivity.class);
        startActivity(intent);
    }

    /**
     * 去选择蓝牙界面
     */
    private void goChooseBuleTouth() {
    }
}
