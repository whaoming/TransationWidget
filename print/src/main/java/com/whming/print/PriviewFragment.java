package com.whming.print;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whming.print.bean.Column;

import java.io.Serializable;
import java.util.List;


public class PriviewFragment extends Fragment {

    RecyclerView recyclerView;
    private PriviewAdapter mAdapter;
    List<Column> columns;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBundle(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initWidget(view);
        return view;
    }


    public static PriviewFragment newInstance(List<Column> columns) {
        Bundle args = new Bundle();
        PriviewFragment fragment = new PriviewFragment();
        args.putSerializable("columns", (Serializable) columns);
        fragment.setArguments(args);
        return fragment;
    }

    protected void initBundle(Bundle bundle) {
        columns = (List<Column>) bundle.getSerializable("columns");
    }

    protected int getLayoutId() {
        return R.layout.fragment_priview;
    }

    protected void initWidget(View root) {
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        mAdapter = new PriviewAdapter(columns);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setSmoothScrollbarEnabled(true);
        linearLayoutManager1.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager1);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(mAdapter);
    }


}
