package com.whming.transationwidget.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whming.transationwidget.R;


/**
* author: whming
* github: https://github.com/whaoming
* date: 2017/8/17
* TODO: 
* remark: nothing
*/
public class AmountButton extends LinearLayout implements View.OnClickListener, TextWatcher {

    private int maxCount = Integer.MAX_VALUE;
    private int minCount = 1;
    private EditText et_ab;
    private OnWrongCountListener mOnWarnListener;

    public AmountButton(Context context) {
        this(context, null);
    }

    public AmountButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.widget_amount_button, this);
        findViewById(R.id.rl_less).setOnClickListener(this);
        findViewById(R.id.rl_plus).setOnClickListener(this);
        et_ab = findViewById(R.id.et_ab);
        et_ab.addTextChangedListener(this);
        et_ab.setOnClickListener(this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AmountButton);
        boolean editable = typedArray.getBoolean(R.styleable.AmountButton_editable, true);
        int textWidth = typedArray.getDimensionPixelSize(R.styleable.AmountButton_textWidth, -1);
        int textColor = typedArray.getColor(R.styleable.AmountButton_textColor, 0xff000000);
        String unit = typedArray.getString(R.styleable.AmountButton_unit);
        typedArray.recycle();
        setEditable(editable);
        et_ab.setTextColor(textColor);
        if (textWidth > 0) {
            LayoutParams textParams = new LayoutParams(textWidth, LayoutParams.MATCH_PARENT);
            findViewById(R.id.rl_textcontent).setLayoutParams(textParams);
        }
        et_ab.setSelection(et_ab.getText().toString().trim().length());
        TextView tv_unit = findViewById(R.id.tv_unit);
        if(TextUtils.isEmpty(unit)){
            tv_unit.setVisibility(View.GONE);
        }else{
            tv_unit.setVisibility(View.VISIBLE);
            tv_unit.setText(unit);
        }

    }

    public int getNumber() {
        try {
            return Integer.parseInt(et_ab.getText().toString());
        } catch (NumberFormatException e) {
        }
        et_ab.setText("1");
        return 1;
    }

    @Override
    public void onClick(View v) {
        et_ab.requestFocus();
        int count = getNumber();
        switch (v.getId()){
            case R.id.rl_less:
                if (count > minCount) {
                    //正常减
                    et_ab.setText("" + (count - 1));
                    et_ab.setSelection(et_ab.getText().toString().trim().length());
                }else{
                    underMinCount();
                }
                break;
            case R.id.rl_plus:
                if (count < maxCount) {
                    et_ab.setText("" + (count + 1));
                    et_ab.setSelection(et_ab.getText().toString().trim().length());
                }
                else {
                    exceedMaxCount();
                }
                break;
            case R.id.et_ab:
                et_ab.setSelection(et_ab.getText().toString().length());
                break;
        }
    }

    private void onNumberInput() {
        int count = getNumber();
        if(count==minCount){
            return;
        }
        Log.i("wang","count:"+count);
        if (count < minCount) {
            et_ab.setText(String.valueOf(minCount));
            underMinCount();
            return;
        }
        int limit = maxCount;
        if (count > limit) {
            et_ab.setText(limit + "");
            exceedMaxCount();
        }
        if (mOnWarnListener != null) mOnWarnListener.onNumberChange(getNumber());

    }


    private void underMinCount() {
        if (mOnWarnListener != null) mOnWarnListener.onUnderMinCount(minCount);
    }


    private void exceedMaxCount() {
        if (mOnWarnListener != null) mOnWarnListener.onExceedMaxCount(maxCount);
    }


    private void setEditable(boolean editable) {
        if (editable) {
            et_ab.setFocusable(true);
            et_ab.setKeyListener(new DigitsKeyListener());
        } else {
            et_ab.setFocusable(false);
            et_ab.setKeyListener(null);
        }
    }

    public AmountButton setCurrentNumber(int currentNumber) {
        if (currentNumber < 1) et_ab.setText("1");
        et_ab.setText(""+Math.min(maxCount, currentNumber));
        return this;
    }



    public int getMaxCount() {
        return maxCount;
    }

    public AmountButton setMaxCount(int maxCount) {
        this.maxCount = maxCount;
        return this;
    }

    public AmountButton setOnWarnListener(OnWrongCountListener onWarnListener) {
        mOnWarnListener = onWarnListener;
        return this;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        Log.i("wang","");
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        onNumberInput();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public interface OnWrongCountListener {
        void onUnderMinCount(int inventory);

        void onExceedMaxCount(int max);

        void onNumberChange(int number);
    }
}