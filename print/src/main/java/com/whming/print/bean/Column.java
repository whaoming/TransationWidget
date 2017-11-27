package com.whming.print.bean;



public class Column {


    /**
     * 基本类型：文字，图片，线/空行
     * 文字基本类型：1列  2列  3列
     * 文字基本属性：1列对齐方式，文字大小
     */

    //基本类型
    public static final int TYPE_TEXT_1 = 11;
    public static final int TYPE_TEXT_2 = 12;
    public static final int TYPE_TEXT_3 = 13;
    public static final int TYPE_LINE = 14;
    public static final int TYPE_IMAGE = 15;

    //对齐方式
    public static final int AGLIGNMENT_LEFT = 31;
    public static final int AGLIGNMENT_CENTRE = 32;
    public static final int AGLIGNMENT_RIGHT= 33;

    //线的类型
    public static final int LINE_EMPTY = 41;
    public static final int LINE_DASH = 42;

    public int type = -1;
    public int lineType = -1;

    public int alignment = AGLIGNMENT_LEFT; //对齐方式
    public int textSize = 14; //文字大小

    public int imgId; //图片地址
    public String text1;
    public String text2;
    public String text3;
    public int lineCount = 1;

    public static Column newEmptyLine(int count){
        Column column = new Column();
        column.type = TYPE_LINE;
        column.lineType = LINE_EMPTY;
        column.lineCount = count;
        return column;
    }

    public static Column newText(String text){
        Column column = new Column();
        column.type = TYPE_TEXT_1;
        column.text1 = text;
        return column;
    }

    public static Column newText(String text, int alignment){
        Column column = new Column();
        column.type = TYPE_TEXT_1;
        column.text1 = text;
        column.alignment = alignment;
        return column;
    }

    public static Column newText(String text, int alignment, int textSize){
        Column column = new Column();
        column.type = TYPE_TEXT_1;
        column.text1 = text;
        column.textSize = textSize;
        column.alignment = alignment;
        return column;
    }

    public static Column newLargeText(String text, int alignment){
        Column column = new Column();
        column.type = TYPE_TEXT_1;
        column.text1 = text;
        column.alignment = alignment;
        column.textSize = 22;
        return column;
    }

    public static Column newTwoText(String content1, String content2){
        Column column = new Column();
        column.type = TYPE_TEXT_2;
//        column.columnCount = COLUMN_COUNT_2;
        column.text1 = content1;
        column.text2 = content2;
        return column;
    }

    public static Column newThreeText(String content1, String content2, String content3){
        Column column = new Column();
        column.type = TYPE_TEXT_3;
        column.text1 = content1;
        column.text2 = content2;
        column.text3 = content3;
        return column;
    }

    public static Column newDashLine(){
        Column column = new Column();
        column.type = TYPE_LINE;
        column.lineType = LINE_DASH;
        column.text1 = "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ";
        return column;
    }

}
