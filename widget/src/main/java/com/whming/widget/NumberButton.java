package com.whming.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
* author: whming
* github: https://github.com/whaoming
* date: 2017/12/4
* TODO: 
* remark: nothing
*/
public class NumberButton extends LinearLayout implements View.OnClickListener {

    private ImageView iv_add,iv_del;
    private TextView tv;
    private int count = 0;
    private onOptionClickListener listener;

    public NumberButton(Context context) {
       this(context,null);
    }

    public NumberButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.widget_number_button, this);
        iv_add  = (ImageView) findViewById(R.id.iv_add);
        iv_del = (ImageView) findViewById(R.id.iv_del);
        tv = (TextView) findViewById(R.id.tv);
        iv_add.setOnClickListener(this);
        iv_del.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.iv_add) {
            onAddClick();
        } else if (i == R.id.iv_del) {
            onDelClick();
        }
        if(listener!=null){
            listener.onOption(count);
        }
    }

    private void onDelClick(){
        count--;
        if(count == 0){
            iv_del.setAnimation(getHiddenAnimation());
            tv.setVisibility(View.INVISIBLE);
            iv_del.setVisibility(View.INVISIBLE);

        }
        tv.setText(String.valueOf(count));
    }

    private void onAddClick(){
        if(count==0){
            iv_del.setVisibility(View.VISIBLE);
            Animation showAnimation = getShowAnimation();
            showAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    tv.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            iv_del.setAnimation(showAnimation);
        }
        count++;
        tv.setText(String.valueOf(count));
    }

    //显示减号的动画
    private Animation getShowAnimation(){
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,2f
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0,1);
        set.addAnimation(alpha);
        set.setDuration(200);
        return set;
    }
    //隐藏减号的动画
    private Animation getHiddenAnimation(){
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,2f
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(1,0);
        set.addAnimation(alpha);
        set.setDuration(200);
        return set;
    }

    public interface onOptionClickListener{
        void onOption(int currentCount);
    }

    public void setOnOptionClickListener(onOptionClickListener listener){
     this.listener = listener;
    }

    public View getAddView(){
        return iv_add;
    }
}
