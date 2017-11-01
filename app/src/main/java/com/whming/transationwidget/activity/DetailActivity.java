package com.whming.transationwidget.activity;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whming.transationwidget.R;
import com.whming.transationwidget.widget.dot.TopView;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    TabLayout tab;
    private LinearLayout ll_title;
    private NestedScrollView scrollView;
    private TextView tv_recommend,tv_comment,tv_detail,tv_title,tv_add,tv_car;
    private int titleY,recommendY,commentY,detailY;
    private int currentState = 1;
    private View tab1,tab2,tab3,tab4;
    private TopView topView;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final int state = msg.what;
            switch (msg.what) {
                case 1:
                    setScrollState(state);
                    scrollView.scrollTo(0, 1);
                    scrollView.smoothScrollTo(0, 1);
                    break;
                case 2:
                    setScrollState(state);
                    scrollView.scrollTo(0, detailY + 1);
                    scrollView.smoothScrollTo(0, detailY + 1);
                    ll_title.setAlpha(1);
                    break;
                case 3:
                    //图片详情
                    ll_title.setAlpha(1);
                    setScrollState(state);
                    scrollView.scrollTo(0, commentY + 1);
                    scrollView.smoothScrollTo(0, commentY + 1);
                    break;
                case 4:
                    ll_title.setAlpha(1);
                    setScrollState(state);
                    scrollView.scrollTo(0, recommendY + 1);
                    scrollView.smoothScrollTo(0, recommendY + 1);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ll_title = (LinearLayout) findViewById(R.id.ll_title);
        tv_recommend = (TextView) findViewById(R.id.tv_recommend);
        tv_detail = (TextView) findViewById(R.id.tv_detail);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_comment = (TextView) findViewById(R.id.tv_comment);
        scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        tv_car = (TextView) findViewById(R.id.tv_car);
        tv_add = (TextView) findViewById(R.id.tv_add);

        topView = TopView.attach2Window(this);

        tab1 = findViewById(R.id.tab1);

        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        tab4 = findViewById(R.id.tab4);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tv_add.setOnClickListener(this);
        tab4.setOnClickListener(this);

        tab = (TabLayout) findViewById(R.id.tab);
        tab.addTab(tab.newTab().setText("产品"));
        tab.addTab(tab.newTab().setText("详情"));
        tab.addTab(tab.newTab().setText("评价"));
        tab.addTab(tab.newTab().setText("推荐"));
        tab.setTabMode(TabLayout.MODE_FIXED);
        tab.getTabAt(0).select();

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY < 20) {
                    ll_title.setVisibility(View.GONE);
                } else {
                    ll_title.setVisibility(View.VISIBLE);
                }
                if (scrollY < titleY) {
                    // (当前移动的矢量 - 开始移动的量)/完全显示的量 = 一个百分比
                    float changeAlpha = ((float) scrollY - 20) / titleY;
                    ll_title.setAlpha(changeAlpha);
                    setScrollState(1);
                } else if (scrollY >= titleY && scrollY < detailY) {
                    ll_title.setAlpha(1);
                    setScrollState(1);
                } else if (scrollY >= detailY && scrollY < commentY) {
                    setScrollState(2);
                } else if (scrollY >= commentY && scrollY < recommendY) {
                    setScrollState(3);
                } else if (scrollY >= recommendY) {
                    setScrollState(4);
                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    setScrollState(4);
                }
            }
        });

        initData();
    }

    /**
     * 设置tabview的位置
     * @param state 目标位置
     */
    public void setScrollState(int state) {
        if (currentState == state) {
            return;
        }
        tab.getTabAt(state - 1).select();
        currentState = state;
    }

    private void initData() {
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Point childOffset = new Point();

                //标题栏高度
                int tmp = Dip2Px(88);
                getDeepChildOffset(scrollView, tv_title.getParent(), tv_title, childOffset);
                titleY = childOffset.y - tmp;

                childOffset = new Point();
                getDeepChildOffset(scrollView, tv_recommend.getParent(), tv_recommend, childOffset);
                recommendY = childOffset.y - tmp;

                childOffset = new Point();
                getDeepChildOffset(scrollView, tv_comment.getParent(), tv_comment, childOffset);
                commentY = childOffset.y - tmp;

                childOffset = new Point();
                getDeepChildOffset(scrollView, tv_detail.getParent(), tv_detail, childOffset);
                detailY = childOffset.y - tmp;
            }
        },200);
    }

    /**
     * Used to get deep child offset.
     * <p/>
     * 1. We need to scroll to child in scrollview, but the child may not the direct child to scrollview.
     * 2. So to get correct child position to scroll, we need to iterate through all of its parent views till the main parent.
     *
     * @param mainParent        Main Top parent.
     * @param parent            Parent.
     * @param child             Child.
     * @param accumulatedOffset Accumalated Offset.
     */
    private void getDeepChildOffset(final ViewGroup mainParent, final ViewParent parent, final View child, final Point accumulatedOffset) {
        ViewGroup parentGroup = (ViewGroup) parent;
        accumulatedOffset.x += child.getLeft();
        accumulatedOffset.y += child.getTop();
        if (parentGroup.equals(mainParent)) {
            return;
        }
        getDeepChildOffset(mainParent, parentGroup.getParent(), parentGroup, accumulatedOffset);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int Dip2Px(float dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tab1:
                scrollToPosition(1);
                break;
            case R.id.tab2:
                scrollToPosition(2);
                break;
            case R.id.tab3:
                scrollToPosition(3);
                break;
            case R.id.tab4:
                scrollToPosition(4);
                break;
            case R.id.tv_add:
                showAnimation();
                break;
        }
    }

    /**
     * 将tabview滚动到目标位置
     * @param state 目标tab对应的位置
     */
    private void scrollToPosition(final int state) {
        switch (state) {
            case 1:
                mHandler.sendEmptyMessage(1);
                break;
            case 2:
                mHandler.sendEmptyMessage(2);
                break;
            case 3:
                //图片详情
                mHandler.sendEmptyMessage(3);
                break;
            case 4:
                mHandler.sendEmptyMessage(4);
                break;
            case 6:
                mHandler.sendEmptyMessage(6);
                break;
        }

    }

    /**
     * 获取一个view的中心坐标
     * @param view 目标view
     * @return 横坐标和纵坐标
     */
    public int[] getViewCenter(View view) {
        int[] result = new int[]{0, 0};
        view.getLocationOnScreen(result);
        result[0] = result[0] + view.getWidth() / 4;
        result[1] = result[1] - view.getHeight() / 2;
        return result;
    }


    /**
     * 执行添加到购物车动画
     */
    private void showAnimation(){
        int[] start = getViewCenter(tv_add);
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
}
