package com.athome.demo;

import com.athome.commonutils.MD5;

public class TestMD5 {

    public static void main(String[] args) {
        String pwd = "123456";
        String encrypt = MD5.encrypt(pwd);
        System.out.println(encrypt);
    }
}
