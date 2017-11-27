package com.whming.transationwidget.print;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;

import com.whming.print.PriviewFragment;
import com.whming.print.bean.Column;
import com.whming.print.util.Builder;
import com.whming.print.util.ConcreteBuilder;
import com.whming.print.util.PrintHelper;
import com.whming.transationwidget.R;

import java.util.List;

public class PriviewActivity extends FragmentActivity {

//    private FrameLayout fl_content;
    private Button btn_print;
    private List<Column> columns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priview);
        btn_print = (Button) findViewById(R.id.btn_print);
        initData();
        initFragment();
    }

    private void initData() {
        try {
            PrintHelper.getInstance().init(null,this);
            Builder builder = new ConcreteBuilder();
            PrintHelper.getInstance()
                    .addColumn(builder.newText("发货单", Column.AGLIGNMENT_CENTRE,22))
                    .addColumn(builder.newEmptyLine(1))
                    .addColumn(builder.newText("收货人：简约至上"))
                    .addColumn(builder.newText("电话：13835824781"))
                    .addColumn(builder.newText("收货地址：广东省佛山市顺德区龙江镇什么什么街道某某大厦12楼1205"))
                    .addColumn(builder.newEmptyLine(1))
                    .addColumn(builder.newText("货物清单", Column.AGLIGNMENT_LEFT,22))
                    .addColumn(builder.newDashLine())
                    .addColumn(builder.newText("藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅"))
                    .addColumn(builder.newEmptyLine(1))
                    .addColumn(builder.newText("颜色：红色"))
                    .addColumn(builder.newText("尺寸：180*2000mm"))
                    .addColumn(builder.newTwoText("单价:$899", "x1"))
                    .addColumn(builder.newEmptyLine(1))
                    .addColumn(builder.newText("颜色：红色"))
                    .addColumn(builder.newText("尺寸：180*2000mm"))
                    .addColumn(builder.newTwoText("单价:$899", "x1"))
                    .addColumn(builder.newEmptyLine(1))
                    .addColumn(builder.newText("颜色：红色"))
                    .addColumn(builder.newText("尺寸：180*2000mm"))
                    .addColumn(builder.newTwoText("单价:$899", "x1"))
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
                    .addColumn(builder.newText("藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅藤椅"))
                    .addColumn(builder.newEmptyLine(1))
                    .addColumn(builder.newText("颜色：红色"))
                    .addColumn(builder.newText("尺寸：180*2000mm"))
                    .addColumn(builder.newTwoText("单价:$899", "x1"))
                    .addColumn(builder.newDashLine())
                    .addColumn(builder.newText("订单编号：ashjdkjashdjkahsjkdhasjkhd"))
                    .addColumn(builder.newText("创建时间：2017-5-5"))
                    .addColumn(builder.newEmptyLine(3))
                    .addColumn(builder.newText("哼哈匠APP", Column.AGLIGNMENT_CENTRE,22))
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
