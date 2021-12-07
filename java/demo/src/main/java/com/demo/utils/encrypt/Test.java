package com.demo.utils.encrypt;

import java.io.File;

/**
 * @author Alex
 * @date 2021/7/6 17:01
 */
public class Test {
    public static void main(String[] args) {
        String uri = "http://114.116.242.254:23203/00000304N7NL00242664/6742e663/app/2cac80487476a8f30174b4b5d8e217b9/G/downloads/mtms/app/NEWLAND/1E501F3EEC0DCD336A2C51A89BFC020051C2549C/com.fuioupay.deviceservice/5.apk";

        //获取文件路径 文件大小、名称
        File file;

        String specificPath = uri.split("downloads")[1];

        String fileRealPath;

        while (specificPath.startsWith("/")) {
            specificPath = specificPath.substring(1, specificPath.length());
        }

        String BASE_PATH = "/home/downloads";

        if (BASE_PATH.endsWith("/")) {
            fileRealPath = BASE_PATH + specificPath;
        } else {
            fileRealPath = BASE_PATH + "/" + specificPath;
        }

        System.out.println(fileRealPath);

    }
}
