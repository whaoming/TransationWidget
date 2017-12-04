package com.whming.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

/**
 * Created by liuzhuang on 15/11/10.
 */
public class MovedView extends FrameLayout {
    private static final String TAG = "MovedView";
    private static int DURATION_TIME = 500;

    public MovedView(Context context) {
        super(context);
    }

    public MovedView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MovedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public void move(final TopView.AnimationInfo animationInfo) {
        BezierEvaluator evaluator = new BezierEvaluator(animationInfo.p0, animationInfo.p1, animationInfo.p2);
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, animationInfo.p0, animationInfo.p2).setDuration(DURATION_TIME);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                animationInfo.animationUpdate(pointF);
            }
        });

        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                Log.d(TAG, "onAnimationStart");
                animationInfo.animationStart();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d(TAG, "onAnimationRepeat");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "onAnimationEnd");
                animationInfo.animationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d(TAG, "onAnimationCancel");
                animationInfo.animationEnd();
            }
        });
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(DURATION_TIME);
        animator.start();
    }
}
