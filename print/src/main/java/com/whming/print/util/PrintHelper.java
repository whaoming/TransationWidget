package com.whming.print.util;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.whming.print.bean.Column;

import java.util.ArrayList;
import java.util.List;

/**
* author: whming
* github: https://github.com/whaoming
* date: 2017/11/27
* TODO:
* remark: nothing
*/
public class PrintHelper {
    private List<Column> columns;
    private PrintUtil printUtil;
    private static PrintHelper INSTANCE;
    private Context mContext;
    private PrintHelper(){}
    public static PrintHelper getInstance(){
       if(INSTANCE==null){
           INSTANCE = new PrintHelper();
       }
       return INSTANCE;
    }

    public void init(BluetoothSocket bluetoothSocket,Context mContext)  {
        try {
            columns = new ArrayList<>();
            printUtil = new PrintUtil(bluetoothSocket.getOutputStream(), "GBK");
            this.mContext = mContext;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void print(){
        //记得做检查
        try {
            for (Column column : columns) {
                int type = column.type;
                switch (type) {
                    case Column.TYPE_TEXT_1:
                        switch (column.alignment){
                            case Column.AGLIGNMENT_LEFT:
                                printUtil.printAlignment(0);
                                break;
                            case Column.AGLIGNMENT_CENTRE:
                                printUtil.printAlignment(1);
                                break;
                            case Column.AGLIGNMENT_RIGHT:
                                printUtil.printAlignment(2);
                                break;
                        }
                        if(column.textSize != 14){
                            //打印大字
                            printUtil.printLargeText(column.text1);
                        }else {
                            printUtil.printText(column.text1);
                        }
                        printUtil.printAlignment(0);
                        break;
                    case Column.TYPE_TEXT_2:
                        printUtil.printTwoColumn(column.text1,column.text2);
                        break;
                    case Column.TYPE_TEXT_3:
                        printUtil.printThreeColumn(column.text1,column.text2,column.text3);
                        break;
                    case Column.TYPE_LINE:
                        if(column.lineType == Column.LINE_EMPTY){
                            printUtil.printLine(column.lineCount);
                        }else if(column.lineType == Column.LINE_DASH){
                            printUtil.printDashLine();
                        }
                        break;
                    case Column.TYPE_IMAGE:
                        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),column.imgId);
                        printUtil.printBitmap(bitmap);
                        break;
                }
            }
        }catch (Exception e){

        }
    }


    public PrintHelper addColumn(Column column){
        columns.add(column);
        return this;
    }

    public List<Column> getColumns(){
        return columns;
    }


}
