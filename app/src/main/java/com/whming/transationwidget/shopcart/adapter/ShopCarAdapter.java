package com.whming.transationwidget.shopcart.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.whming.transationwidget.R;
import com.whming.transationwidget.extra.MyTreeItem;
import com.whming.transationwidget.extra.bean.Dao;
import com.whming.transationwidget.extra.bean.Level1;
import com.whming.transationwidget.extra.bean.Level2;
import com.whming.transationwidget.extra.bean.Level3;
import com.whming.transationwidget.widget.recycle.MutiRecyclerViewAdapter;
import com.whming.widget.AmountButton;

import java.util.Arrays;
import java.util.List;

/**
* author: whming
* github: https://github.com/whaoming
* date: 2017/10/12
* TODO: 购物车列表对应的adapter
* remark: nothing
*/
public class ShopCarAdapter extends MutiRecyclerViewAdapter<MyTreeItem<Dao>, MutiRecyclerViewAdapter.RecyclerViewHolder> {

        static final int TYPE_LEVEL_1 = 1;
        static final int TYPE_LEVEL_2 = 2;
        static final int TYPE_LEVEL_3 = 3;

        private OptionListener listener;


        public ShopCarAdapter(RecyclerView recyclerView, List<MyTreeItem<Dao>> mData) {
            super(recyclerView, mData);

            initCustomType();
        }

        private void initCustomType() {
            multiItemType = new MultiItemType<MyTreeItem<Dao>>() {
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
                public int getItemViewType(int position, MyTreeItem<Dao> s) {
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
        public void bindDataToItemView(RecyclerViewHolder recyclerViewHolder, MyTreeItem<Dao> item, final int position) {
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
                    if(listener!=null) listener.onSelected(calcul());
                }
            });
        }

    public String calcul() {
        int l1=0,l2 =0,l3=0;
        String content = "";
        List<Integer> integers = Arrays.asList(getSelectedIndices());
        for(int i=0;i<mData.size();i++){
            if(integers.contains(i)){
                MyTreeItem<Dao> item = mData.get(i);
                if(item.data instanceof Level1){
                    l1++;
                }else if(item.data instanceof Level2){
                    l2 ++;
                }else if(item.data instanceof Level3){
                    l3++;
                }
            }

        }
        content = "一级："+l1+",二级："+l2+",三级:"+l3;
       return content;
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
                    listener.onAllSeleted(flag && state);
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
            void onSelected(String content);
        }
    }