package com.whming.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;


/**
 * Created by liuzhuang on 15/11/9.
 * 铺在最外层，添加控件，修改控件的位置
 */
public class TopView extends FrameLayout {
    private static final String TAG = "TopView";
    private boolean isDetachedFromWindow;

    public TopView(Context context) {
        super(context);
        init();
    }

    public TopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setBackgroundColor(Color.TRANSPARENT);
    }

    public void add(AnimationInfo animationInfo) {
        if (isDetachedFromWindow) {
            return;
        }
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        MovedView movedView = (MovedView) layoutInflater.inflate(R.layout.dot, this, false);
        ImageView imageView = (ImageView) movedView.findViewById(R.id.imageView);
        imageView.setBackgroundResource(animationInfo.resId);
        animationInfo.setMovedView(movedView);
        TopView.this.addView(movedView);
        movedView.move(animationInfo);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isDetachedFromWindow = false;
    }

    @Override
    protected void onDetachedFromWindow() {
        isDetachedFromWindow = true;
        super.onDetachedFromWindow();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    /**
     * 添加到屏幕根目录中
     * https://github.com/tyrantgit/ExplosionField
     */
    public static TopView attach2Window(Activity activity) {
        ViewGroup rootView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        TopView topView = new TopView(activity);
        rootView.addView(topView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return topView;
    }

    // 动画回调
    public interface AnimationCallback {
        void animationEnd();
    }

    public static class AnimationInfo {
        AnimationCallback callback; // 动画结束回调
        MovedView movedView; // 要移动的 View
        int resId; // 图片资源id
        PointF p0; // 起点
        PointF p1; // 第二点
        PointF p2; // 终点

        public AnimationInfo(Builder builder) {
            this.callback = builder.callback;
            this.resId = builder.resId;
            this.p0 = builder.p0;
            this.p1 = builder.p1;
            this.p2 = builder.p2;
        }

        public void animationStart() {
            movedView.setVisibility(View.VISIBLE);
        }

        public void animationUpdate(PointF pointF) {
            movedView.setX(pointF.x);
            movedView.setY(pointF.y);
        }

        public void animationEnd() {
            movedView.setVisibility(View.GONE);
            callback.animationEnd();
            ViewGroup viewGroup = (ViewGroup) movedView.getParent();
            viewGroup.removeView(movedView);
        }

        public void setMovedView(MovedView movedView) {
            // 默认不展示
            movedView.setVisibility(View.GONE);
            this.movedView = movedView;
        }

        public static class Builder {
            AnimationCallback callback;
            int resId;
            PointF p0;
            PointF p1;
            PointF p2;

            public Builder callback(AnimationCallback callback) {
                this.callback = callback;
                return this;
            }

            public Builder resId(int resId) {
                this.resId = resId;
                return this;
            }

            public Builder p0(PointF p0) {
                this.p0 = p0;
                return this;
            }

            public Builder p1(PointF p1) {
                this.p1 = p1;
                return this;
            }

            public Builder p2(PointF p2) {
                this.p2 = p2;
                return this;
            }

            public AnimationInfo create() {
                return new AnimationInfo(this);
            }
        }
    }
}
