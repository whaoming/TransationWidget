package com.whming.transationwidget.print;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whming.print.BasePrintActivity;
import com.whming.print.util.BluetoothUtil;
import com.whming.transationwidget.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author: whming
 * github: https://github.com/whaoming
 * date: 2017/11/28
 * TODO: 配对列表
 * remark: nothing
 */
public class DeviceListActivity extends BasePrintActivity {
    private RecyclerView listview;
    private Button btn_more;
    private DeviceListAdapter mAdapter;
    private List<BluetoothDevice> mDatas;
    private BluetoothDevice currentDevices;

    public static void goDeviceListActivity(Activity context){
        Intent intent = new Intent(context,DeviceListActivity.class);
        context.startActivityForResult(intent,1);
    }

    @Override
    public void onConnected(BluetoothSocket socket, int taskType) {
        //取得连接
        Log.i("wang","连接成功");
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable("device",currentDevices);
        intent.putExtra("value",bundle);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        listview = (RecyclerView) findViewById(R.id.listview);
        btn_more = (Button) findViewById(R.id.btn_more);
        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //连接更多设备
                startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
            }
        });
        initView();
    }

    private void initView() {
        mDatas = new ArrayList<>();
        mAdapter = new DeviceListAdapter(mDatas);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setSmoothScrollbarEnabled(true);
        linearLayoutManager1.setAutoMeasureEnabled(true);
        listview.setLayoutManager(linearLayoutManager1);
        listview.setItemAnimator(new DefaultItemAnimator());
        listview.setHasFixedSize(true);
        listview.setNestedScrollingEnabled(false);
        listview.setAdapter(mAdapter);
        mAdapter.setOnClickListener(new AllListener() {
            @Override
            public void onClick(BluetoothDevice device) {
                Log.i("wang","连接");
                DeviceListActivity.this.currentDevices = device;
                DeviceListActivity.this.connectDevice(device, 1);
            }
        });
        addData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addData();
    }

    private void addData(){
        List<BluetoothDevice> printerDevices = BluetoothUtil.getPairedDevices();
        mDatas.clear();
        mDatas.addAll(printerDevices);
        mAdapter.notifyDataSetChanged();
    }

    //BluetoothDevice
    static class DeviceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<BluetoothDevice> mDatas;
        private AllListener listener;

        public void setOnClickListener(AllListener listener){
            this.listener = listener;
        }

        public DeviceListAdapter(List<BluetoothDevice> datas) {
            this.mDatas = datas;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bluetooth_device, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ((ViewHolder) holder).tv.setText(mDatas.get(position).getName());
            ((ViewHolder) holder).rl_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        listener.onClick(mDatas.get(position));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;
        public RelativeLayout rl_content;

        ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_device_name);
            rl_content = (RelativeLayout) itemView.findViewById(R.id.rl_content);
        }
    }

    public interface AllListener{
        void onClick(BluetoothDevice device);
    }
}
