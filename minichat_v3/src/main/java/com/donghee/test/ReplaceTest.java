package com.donghee.test;

/**
 * Created by Administrator on 2017-02-26.
 */

public class ReplaceTest {
    public static void main(String[] args) {
        String str = "hello man";
        System.out.println(str);
        str = str.replaceAll(" ", "");
        System.out.println(str);
    }
}
