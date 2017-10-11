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
import com.whming.transationwidget.extra.bean.Level1;
import com.whming.transationwidget.extra.bean.Level2;
import com.whming.transationwidget.extra.bean.Level3;
import com.whming.transationwidget.widget.AmountButton;
import com.whming.transationwidget.widget.recycle.MutiRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShopCarActivity extends AppCompatActivity {

    private RecyclerView listview;
    private List<Level1> source;
    private List<MyTreeItem<Object>> mDatas;
    private ShopCarAdapter mAdapter;
    private CheckBox cb;
    private Button edit;
    private boolean isEdit = false;

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

            }
        });

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
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEdit = !isEdit;
                if(isEdit){
                    edit.setText("删除并完成");
                    delete();
                }else{
                    edit.setText("编辑");
                }
            }
        });
    }

    private void delete() {

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
        /**
         * *********************完成假数据的模拟**************************
         */
        MyTreeItem item;
        int temp;
        for (int i = 0; i < source.size(); i++) {
            Level1 level1 = source.get(i);
            item = new MyTreeItem();
            item.data = level1;
            item.parent = -1;
            mDatas.add(item);
            temp = mDatas.size() - 1;
            for (int j = 0; j < level1.level2List.size(); j++) {
                Level2 level2 = level1.level2List.get(j);
                item = new MyTreeItem();
                item.data = level2;
                item.parent = temp;
                mDatas.add(item);
                for (int k = 0; k < level2.level3List.size(); k++) {
                    Level3 level3 = level2.level3List.get(k);
                    item = new MyTreeItem();
                    item.data = level3;
                    item.parent = mDatas.size() - k - 1;
                    mDatas.add(item);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private static class ShopCarAdapter extends MutiRecyclerViewAdapter<MyTreeItem<Object>, MutiRecyclerViewAdapter.RecyclerViewHolder> {

        static final int TYPE_LEVEL_1 = 1;
        static final int TYPE_LEVEL_2 = 2;
        static final int TYPE_LEVEL_3 = 3;

        private OptionListener listener;


        ShopCarAdapter(RecyclerView recyclerView, List<MyTreeItem<Object>> mData) {
            super(recyclerView, mData);

            initCustomType();
        }

        private void initCustomType() {
            multiItemType = new MultiItemType<MyTreeItem<Object>>() {
                @Override
                public int getLayoutId(int itemType) {
                    if (itemType == TYPE_LEVEL_1) {
                        return R.layout.item_1;
                    } else if (itemType == TYPE_LEVEL_2) {
                        return R.layout.item_2;
                    } else {
                        return R.layout.item_3;
                    }
                }

                @Override
                public int getItemViewType(int position, MyTreeItem<Object> s) {
                    if (s.data instanceof Level1) {
                        return TYPE_LEVEL_1;
                    } else if (s.data instanceof Level2) {
                        return TYPE_LEVEL_2;
                    } else {
                        return TYPE_LEVEL_3;
                    }
                }
            };

        }


        @Override
        public void bindDataToItemView(RecyclerViewHolder recyclerViewHolder, MyTreeItem<Object> item, final int position) {
            int itemViewType = getItemViewType(position);
            TextView tv = recyclerViewHolder.getView(R.id.tv);
            CheckBox cb = recyclerViewHolder.getView(R.id.cb);
            cb.setChecked(isIndexSelected(position));

            switch (itemViewType) {
                case TYPE_LEVEL_1:
                    Level1 l1 = (Level1) item.data;
                    tv.setText(l1.name);
                    break;
                case TYPE_LEVEL_2:
                    Level2 l2 = (Level2) item.data;
                    tv.setText(l2.name);
                    break;
                case TYPE_LEVEL_3:
                    final Level3 l3 = (Level3) item.data;
                    tv.setText(l3.name);
                    AmountButton btnCount = recyclerViewHolder.getView(R.id.abb);
                    btnCount.setOnWarnListener(null);
                    btnCount.setCurrentNumber(l3.count);
                    btnCount.setOnWarnListener(new AmountButton.OnWrongCountListener() {
                        @Override
                        public void onUnderMinCount(int inventory) {

                        }

                        @Override
                        public void onExceedMaxCount(int max) {

                        }

                        @Override
                        public void onNumberChange(int number) {
                            l3.count = number;
                        }
                    });
                    break;
            }
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleSelected(position);
                    setChildState(position, isIndexSelected(position), true);
                }
            });
        }



        /**
         * 设置某个父亲节点下的所有子节点的状态
         * @param postion 父亲节点的位置
         * @param state   目标状态
         */
        private void setChildState(int postion, boolean state, boolean flag) {
            for (int i = 0; i < mData.size(); i++) {
                MyTreeItem item = mData.get(i);
                //检查postiion下有没有子节点
                if (item.parent == postion) {
                    //如果目标item的parent是这个，那么设置状态
                    setSelected(i, state);
                    //然后还要检查这个子节点下的更下一层的子节点
                    setChildState(i, state, false);
                }
            }
            if (flag) {
                //检查父亲节点下的所有子节点的状态是否一致
                checkParentState(postion, state);
            }
        }

        /**
         * 检查父亲节点下的所有节点,当发现所有子节点为同一状态时改变父亲节点的状态
         * @param position 当前点击的子节点的位置
         * @param state    当前状态
         */
        private void checkParentState(int position, boolean state) {
            int parentPosition = mData.get(position).parent;
            boolean flag = true;
            for (int i = 0; i < mData.size(); i++) {
                MyTreeItem item = mData.get(i);
                if (item.parent == parentPosition) {
                    boolean indexSelected = isIndexSelected(i);
                    if (indexSelected != state) {
                        flag = false;
                    }
                }
            }
            if (parentPosition == -1) {
                if(listener!=null){
                    listener.onAllSeleted(flag);
                }
                return;
            }
            if (state) {
                setSelected(parentPosition, flag);
            } else {
                setSelected(parentPosition, false);
            }
            //检查父亲节点下的所有子节点的状态是否一致
            checkParentState(parentPosition, state);
        }

        @Override
        public int getAdapterLayout() {
            return 0;
        }


        public void setOptionListener(OptionListener lis){
            this.listener = lis;

        }

        public  interface OptionListener{
            void onAllSeleted(boolean flag);
        }
    }
}
