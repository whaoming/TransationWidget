package com.whming.transationwidget.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.whming.transationwidget.R;
import com.whming.transationwidget.extra.MyTreeItem;
import com.whming.transationwidget.extra.bean.Dao;
import com.whming.transationwidget.extra.bean.Level1;
import com.whming.transationwidget.extra.bean.Level2;
import com.whming.transationwidget.extra.bean.Level3;
import com.whming.transationwidget.widget.adapter.ShopCarAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
* author: whming
* github: https://github.com/whaoming
* date: 2017/10/12
* TODO: 购物车页面
* remark: nothing
*/
public class ShopCarActivity extends AppCompatActivity {

    private RecyclerView listview;
    private List<Level1> source;
    private List<MyTreeItem<Dao>> mDatas;
    private ShopCarAdapter mAdapter;
    private CheckBox cb;
    private Button edit;
    private boolean isEdit = false;
    private TextView tv_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_car);
        initWidget();
        initData();
    }

    private void initWidget() {
        cb = (CheckBox) findViewById(R.id.cb);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = cb.isChecked();
                if (checked) {
                    mAdapter.selectAll();
                } else {
                    mAdapter.clearSelected();
                }

                tv_text.setText(mAdapter.calcul());

            }
        });
        tv_text = (TextView) findViewById(R.id.tv_text);
        edit = (Button) findViewById(R.id.edit);
        listview = (RecyclerView) findViewById(R.id.listview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listview.setLayoutManager(linearLayoutManager);
        listview.setItemAnimator(new DefaultItemAnimator());
        listview.setHasFixedSize(true);
        mDatas = new ArrayList<>();
        mAdapter = new ShopCarAdapter(listview, mDatas);
        listview.setAdapter(mAdapter);
        mAdapter.setOptionListener(new ShopCarAdapter.OptionListener() {
            @Override
            public void onAllSeleted(boolean flag) {
                cb.setChecked(flag);
                tv_text.setText(mAdapter.calcul());
            }

            @Override
            public void onSelected(String content) {
                tv_text.setText(content);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEdit = !isEdit;
                if(isEdit){
                    edit.setText("删除并完成");
//                    Integer[] selectedIndices = mAdapter.getSelectedIndices();
//                    List<Integer> integers = Arrays.asList(selectedIndices);
//                    Set strSet = new HashSet(Arrays.asList(integers));
//                    delete(strSet);
                }else{
                    edit.setText("编辑");
                }
            }
        });
    }

    private void delete(Set<Integer> integers) {
        for(int i=0;i<source.size();i++){
            Level1 level1 = source.get(i);
            if(integers.contains(level1.id)){
                source.remove(i);
                continue;
            }
            for(int j=0;j<level1.level2List.size();j++){
                Level2 level2 = level1.level2List.get(j);
                if(integers.contains(level2.id)){
                    level1.level2List.remove(j);
                    continue;
                }
                for(int k=0;k<level2.level3List.size();k++){
                    Level3 level3 = level2.level3List.get(k);
                    if(integers.contains(level3.id)){
                        level2.level3List.remove(k);
                    }
                }
                if(level2.level3List.size()==0){
                    level1.level2List.remove(level2);
                }
            }
            if(level1.level2List.size()==0){
                source.remove(i);
            }
        }
        loadData();
    }

    private void initData() {
        source = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Level1 l1 = new Level1();
            l1.name = "我是第一级" + i;
            l1.level2List = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                Level2 l2 = new Level2();
                l2.name = "我是第二级" + j;
                l2.level3List = new ArrayList<>();
                for (int k = 0; k < 2; k++) {
                    Level3 l3 = new Level3();
                    l3.name = "我是第三级" + k;
                    l2.level3List.add(l3);
                }
                l1.level2List.add(l2);
            }
            source.add(l1);
        }
        loadData();
        /**
         * *********************完成假数据的模拟**************************
         */

    }

    private void loadData() {
        MyTreeItem item;
        int temp;
        for (int i = 0; i < source.size(); i++) {
            Level1 level1 = source.get(i);
            item = new MyTreeItem();
            item.data = level1;
            item.parent = -1;
            mDatas.add(item);
            level1.id = mDatas.size()-1;
            temp = mDatas.size() - 1;
            for (int j = 0; j < level1.level2List.size(); j++) {
                Level2 level2 = level1.level2List.get(j);
                item = new MyTreeItem();
                item.data = level2;
                item.parent = temp;
                mDatas.add(item);
                level2.id = mDatas.size()-1;
                for (int k = 0; k < level2.level3List.size(); k++) {
                    Level3 level3 = level2.level3List.get(k);
                    item = new MyTreeItem();
                    item.data = level3;
                    item.parent = mDatas.size() - k - 1;
                    mDatas.add(item);
                    level3.id = mDatas.size()-1;
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }


}
