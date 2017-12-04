package com.whming.transationwidget.shopcart;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whming.transationwidget.R;
import com.whming.widget.TopView;
import com.whming.widget.NumberButton;

import java.util.ArrayList;
import java.util.List;

/**
* author: whming
* github: https://github.com/whaoming
* date: 2017/12/4
* TODO: 添加到购物车动画
* remark: nothing
*/
public class AnimShopCartActivity extends AppCompatActivity {


//    private NumberButton nb;
    private RecyclerView recyclerView;
    private ItemAdapter mAdapter;
    private List<String> datas;
    private TextView tv_car;
    private TopView topView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_shop_cart);
//        nb = (NumberButton) findViewById(R.id.nb);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tv_car = (TextView) findViewById(R.id.tv_car);
        topView = TopView.attach2Window(this);
        initRecyclerView();
        initData();
    }

    private void initData() {
        for(int i=0;i<20;i++){
            datas.add("ssss"+i);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        datas = new ArrayList<>();
        mAdapter = new ItemAdapter(datas);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnOptionClickListener(new ItemAdapter.onOptionClickListener() {
            @Override
            public void onClick(int position, View view) {
                showAnimation(view);
            }
        });
    }

    /**
     * 执行添加到购物车动画
     */
    private void showAnimation(View view){
        int[] start = getViewCenter(view);
        int[] end = getViewCenter(tv_car);
        PointF p0 = new PointF(start[0], start[1]); // 起点
        PointF p1 = new PointF((start[0] - end[0]) / 2 + end[0], start[1] - 500); // 第二点
        PointF p2 = new PointF(end[0], end[1]); // 终点
        TopView.AnimationInfo animationInfo = new TopView.AnimationInfo.Builder().callback(
                new TopView.AnimationCallback() {
                    @Override
                    public void animationEnd() {
                        tada(tv_car,1f);
                    }
                })
                .resId(R.mipmap.icon_point_red)
                .p0(p0)
                .p1(p1)
                .p2(p2)
                .create();
        topView.add(animationInfo);
    }

    /**
     * 用于执行抖动的动画
     * @param view 需要抖动的目标view
     * @param shakeFactor 1f
     */
    public void tada(View view, float shakeFactor) {

        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofKeyframe(View.SCALE_X,
                Keyframe.ofFloat(0f, 1f),
                Keyframe.ofFloat(.1f, .9f),
                Keyframe.ofFloat(.2f, .9f),
                Keyframe.ofFloat(.3f, 1.1f),
                Keyframe.ofFloat(.4f, 1.1f),
                Keyframe.ofFloat(.5f, 1.1f),
                Keyframe.ofFloat(.6f, 1.1f),
                Keyframe.ofFloat(.7f, 1.1f),
                Keyframe.ofFloat(.8f, 1.1f),
                Keyframe.ofFloat(.9f, 1.1f),
                Keyframe.ofFloat(1f, 1f)
        );

        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofKeyframe(View.SCALE_Y,
                Keyframe.ofFloat(0f, 1f),
                Keyframe.ofFloat(.1f, .9f),
                Keyframe.ofFloat(.2f, .9f),
                Keyframe.ofFloat(.3f, 1.1f),
                Keyframe.ofFloat(.4f, 1.1f),
                Keyframe.ofFloat(.5f, 1.1f),
                Keyframe.ofFloat(.6f, 1.1f),
                Keyframe.ofFloat(.7f, 1.1f),
                Keyframe.ofFloat(.8f, 1.1f),
                Keyframe.ofFloat(.9f, 1.1f),
                Keyframe.ofFloat(1f, 1f)
        );

        PropertyValuesHolder pvhRotate = PropertyValuesHolder.ofKeyframe(View.ROTATION,
                Keyframe.ofFloat(0f, 0f),
                Keyframe.ofFloat(.1f, -3f * shakeFactor),
                Keyframe.ofFloat(.2f, -3f * shakeFactor),
                Keyframe.ofFloat(.3f, 3f * shakeFactor),
                Keyframe.ofFloat(.4f, -3f * shakeFactor),
                Keyframe.ofFloat(.5f, 3f * shakeFactor),
                Keyframe.ofFloat(.6f, -3f * shakeFactor),
                Keyframe.ofFloat(.7f, 3f * shakeFactor),
                Keyframe.ofFloat(.8f, -3f * shakeFactor),
                Keyframe.ofFloat(.9f, 3f * shakeFactor),
                Keyframe.ofFloat(1f, 0)
        );

        ObjectAnimator.ofPropertyValuesHolder(view, pvhScaleX, pvhScaleY,pvhRotate).
                start();
    }

    /**
     * 获取一个view的中心坐标
     * @param view 目标view
     * @return 横坐标和纵坐标
     */
    public int[] getViewCenter(View view) {
        int[] result = new int[]{0, 0};
        view.getLocationOnScreen(result);
        result[0] = result[0] ;
        result[1] = result[1] - getVirtualNavigationHeight(AnimShopCartActivity.this)/2;
        return result;
    }


    /**
     * 获取导航栏高度(如华为底部导航栏高度) 
     * @param context
     * @return
     */
    public static int getVirtualNavigationHeight(Context context) {
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android") ;
//        LogUtils.i("虚拟底布栏的高度", context.getResources().getDimensionPixelSize(context.getResources().getIdentifier("navigation_bar_height", "dimen", "android")));
        if (rid != 0) {
            return context.getResources().getDimensionPixelSize(context.getResources().getIdentifier("navigation_bar_height", "dimen", "android")) ;
        }
        return 0 ;
    }

    static class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

        private List<String> mDatas;
        private onOptionClickListener listener;

        public void setOnOptionClickListener(onOptionClickListener listener){
            this.listener = listener;
        }

        public ItemAdapter(List<String> datas){
            this.mDatas = datas;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_number_button,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.nb.setOnOptionClickListener(new NumberButton.onOptionClickListener() {
                @Override
                public void onOption(int currentCount) {
                    if(listener!=null){
                        listener.onClick(position,holder.nb.getAddView());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        public static  class ViewHolder extends RecyclerView.ViewHolder {
            public NumberButton nb;
            public ViewHolder(View itemView) {
                super(itemView);
                nb = (NumberButton) itemView.findViewById(R.id.nb);
            }
        }


        public interface onOptionClickListener{
            void onClick(int position,View view);
        }
    }

}
