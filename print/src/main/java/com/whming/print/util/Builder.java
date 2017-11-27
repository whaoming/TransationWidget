package com.whming.print.util;


import com.whming.print.bean.Column;

/**
* author: whming
* github: https://github.com/whaoming
* date: 2017/11/27
* TODO:
* remark: nothing
*/
public interface Builder {
     Column newEmptyLine(int count);
    Column newText(String text);
    Column newText(String text, int alignment);
    Column newText(String text, int alignment, int textSize);
    Column newLargeText(String text, int alignment);
    Column newTwoText(String content1, String content2);
    Column newThreeText(String content1, String content2, String content3);
    Column newDashLine();
    Column newImage(int imgID);
}
