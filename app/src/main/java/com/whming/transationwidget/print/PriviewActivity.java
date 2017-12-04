package com.whming.transationwidget.print;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.whming.print.BasePrintActivity;
import com.whming.print.PriviewFragment;
import com.whming.print.bean.Column;
import com.whming.print.util.Builder;
import com.whming.print.util.ConcreteBuilder;
import com.whming.print.util.PrintHelper;
import com.whming.transationwidget.R;

public class PriviewActivity extends BasePrintActivity {

    private Button btn_print;
    private TextView tv_state;
    private boolean isConnect = false;
    private BluetoothDevice currentDevices;
    private NestedScrollView scrollView;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            Bundle bundle = data.getBundleExtra("value");
            currentDevices = bundle.getParcelable("device");
            if(currentDevices!=null){
                isConnect = true;
            }
            updateUi();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        PrintHelper.getInstance().closeConnect();
        isConnect = false;
    }

    @Override
    public void onConnected(BluetoothSocket socket, int taskType) {
        PrintHelper.getInstance().initBuleTouth(socket);
        PrintHelper.getInstance().print();
        isConnect = true;
    }

    public void updateUi(){
        if(isConnect){
            tv_state.setText("当前已经连接打印机");
            btn_print.setText("打印");
        }else{
            tv_state.setText("当前未连接打印机");
            btn_print.setText("去连接");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priview);
        btn_print = (Button) findViewById(R.id.btn_print);
        tv_state = (TextView) findViewById(R.id.tv_state);
        tv_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoChoose();
            }
        });
        initData();
        initFragment();
        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isConnect){
                    DeviceListActivity.goDeviceListActivity(PriviewActivity.this);
                }else {
                    PriviewActivity.this.connectDevice(currentDevices, 2);
                }
            }
        });
        scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        scrollView.scrollTo(0,0);
    }

    private void gotoChoose() {
        if(!isConnect){
            DeviceListActivity.goDeviceListActivity(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUi();
    }

    private void initData() {
        try {
            PrintHelper.getInstance().init(this);
            Builder builder = new ConcreteBuilder();
            PrintHelper.getInstance()
                    .addColumn(builder.newText("发货单", Column.AGLIGNMENT_CENTRE,22))
                    .addColumn(builder.newEmptyLine(1))
                    .addColumn(builder.newText("收货人：简约至上"))
                    .addColumn(builder.newText("电话：13835824781"))
                    .addColumn(builder.newText("收货地址：**省XXXXXXXX镇什么什么街道某某大厦12楼1205"))
                    .addColumn(builder.newEmptyLine(1))
                    .addColumn(builder.newText("货物清单", Column.AGLIGNMENT_LEFT,22))
                    .addColumn(builder.newDashLine())
                    .addColumn(builder.newText("藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅"))
                    .addColumn(builder.newEmptyLine(2))
                    .addColumn(builder.newText("颜色：红色"))
                    .addColumn(builder.newText("尺寸：180*2000mm"))
                    .addColumn(builder.newTwoText("单价:$899", "x1"))
                    .addColumn(builder.newEmptyLine(2))
                    .addColumn(builder.newText("颜色：红色"))
                    .addColumn(builder.newText("尺寸：180*2000mm"))
                    .addColumn(builder.newTwoText("单价:$899", "x1"))
                    .addColumn(builder.newEmptyLine(2))
                    .addColumn(builder.newText("颜色：红色"))
                    .addColumn(builder.newText("尺寸：180*2000mm"))
                    .addColumn(builder.newTwoText("单价:$899", "x1"))
                    .addColumn(builder.newEmptyLine(2))
                    .addColumn(builder.newText("颜色：红色"))
                    .addColumn(builder.newText("尺寸：180*2000mm"))
                    .addColumn(builder.newTwoText("单价:$899", "x1"))
                    .addColumn(builder.newDashLine())
                    .addColumn(builder.newText("藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅"))
                    .addColumn(builder.newEmptyLine(1))
                    .addColumn(builder.newText("颜色：红色"))
                    .addColumn(builder.newText("尺寸：180*2000mm"))
                    .addColumn(builder.newTwoText("单价:$899", "x1"))
                    .addColumn(builder.newDashLine())
                    .addColumn(builder.newText("藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅"))
                    .addColumn(builder.newEmptyLine(1))
                    .addColumn(builder.newText("颜色：红色"))
                    .addColumn(builder.newText("尺寸：180*2000mm"))
                    .addColumn(builder.newTwoText("单价:$899", "x1"))
                    .addColumn(builder.newDashLine())
                    .addColumn(builder.newText("订单编号：ashjdkjashdjkahsjkdhasjkhd"))
                    .addColumn(builder.newText("创建时间：2017-5-5"))
                    .addColumn(builder.newEmptyLine(1))
                    .addColumn(builder.newText("***APP", Column.AGLIGNMENT_CENTRE,22))
                    .addColumn(builder.newText("请于48小时内发货", Column.AGLIGNMENT_CENTRE,14))
                    .addColumn(builder.newImage(R.mipmap.hengha_code))
                    .addColumn(builder.newEmptyLine(3));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initFragment() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Fragment f = PriviewFragment.newInstance(PrintHelper.getInstance().getColumns());
                transaction.replace(R.id.fl_content, f);
                transaction.commit();
            }
        });

    }
}
