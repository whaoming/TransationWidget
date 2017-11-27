package com.whming.print;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whming.print.bean.Column;

import java.util.List;


/**
 * author: whming
 * github: https://github.com/whaoming
 * date: 2017/11/24
 * TODO:
 * remark: nothing
 */
public class PriviewAdapter extends RecyclerView.Adapter<PriviewAdapter.ViewHolder> {


    private List<Column> columnList;

    public PriviewAdapter(List<Column> columnList) {
        this.columnList = columnList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Column.TYPE_TEXT_1:
            case Column.TYPE_LINE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_priview_text, parent, false);
                return new TextViewHolder(view);
            case Column.TYPE_TEXT_3:
            case Column.TYPE_IMAGE:
            case Column.TYPE_TEXT_2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_priview_two_text, parent, false);
                return new TwoTextViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Column column = columnList.get(position);
        if (holder instanceof TextViewHolder) {
            TextViewHolder h = (TextViewHolder) holder;
            if (column.type == Column.TYPE_LINE) {
                //是线
                h.tv1.setText(column.text1);
                h.tv1.setMaxLines(1);
            } else {
                //是文字
                h.tv1.setText(column.text1);
                h.tv1.setTextSize(column.textSize);
                if (column.alignment != -1) {
                    switch (column.alignment) {
                        case Column.AGLIGNMENT_LEFT:
                            h.tv1.setGravity(Gravity.LEFT);
                            break;
                        case Column.AGLIGNMENT_CENTRE:
                            h.tv1.setGravity(Gravity.CENTER);
                            break;
                        case Column.AGLIGNMENT_RIGHT:
                            h.tv1.setGravity(Gravity.RIGHT);
                            break;
                    }
                }
            }
        } else if (holder instanceof TwoTextViewHolder) {
            TwoTextViewHolder h = (TwoTextViewHolder) holder;
            h.tv1.setTextSize(column.textSize);
            h.tv1.setTextSize(column.textSize);
            h.tv1.setText(column.text1);
            h.tv2.setText(column.text2);
        }
    }

    @Override
    public int getItemCount() {
        return columnList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Column column = columnList.get(position);
        return column.type;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class TextViewHolder extends ViewHolder {
        TextView tv1;

        TextViewHolder(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.tv1);
        }
    }

    private static class TwoTextViewHolder extends ViewHolder {
        TextView tv1;
        TextView tv2;

        TwoTextViewHolder(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.tv1);
            tv2 = (TextView) itemView.findViewById(R.id.tv2);
        }
    }
}
