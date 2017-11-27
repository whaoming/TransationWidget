package com.whming.print.util;


import com.whming.print.bean.Column;

/**
* author: whming
* github: https://github.com/whaoming
* date: 2017/11/27
* TODO:
* remark: nothing
*/
public class ConcreteBuilder implements Builder {
    @Override
    public Column newEmptyLine(int count) {
        return Column.newEmptyLine(count);
    }

    @Override
    public Column newText(String text) {
        return Column.newText(text);
    }

    @Override
    public Column newText(String text, int alignment) {
        return Column.newText(text, alignment);
    }

    @Override
    public Column newText(String text, int alignment, int textSize) {
        return Column.newText(text, alignment,textSize);
    }

    @Override
    public Column newLargeText(String text, int alignment) {
        return Column.newLargeText(text, alignment);
    }

    @Override
    public Column newTwoText(String content1, String content2) {
        return Column.newTwoText(content1, content2);
    }

    @Override
    public Column newThreeText(String content1, String content2, String content3) {
        return Column.newThreeText(content1, content2, content3);
    }

    @Override
    public Column newDashLine() {
        return Column.newDashLine();
    }

    @Override
    public Column newImage(int imgID) {
        Column column = new Column();
        column.type = Column.TYPE_IMAGE;
        column.imgId = imgID;
        return column;
    }
}
