package com.demo;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class DemoTest {

    public static void main(String[] args) {

        File file = new File("F:/");

    }

    /**
     * 根据 URL 下载文件到本地
     * @param url   图片URL
     * @param dir   存放目录
     * @return
     */
    public static String downloadFromUrl(String url, String dir) {
        try {
            URL httpurl = new URL(url);
            String fileName = getFileNameFromUrl(url);
            System.out.println(fileName);
            File file = new File(dir + fileName);
            FileUtils.copyURLToFile(httpurl, file);
        } catch (Exception e) {
            e.printStackTrace();
            return "Fault";
        }
        return "Success";
    }
    /**
     * 生成文件名, 根据系统时间long值命名
     * */
    public static String getFileNameFromUrl(String url) {
        String fileName = System.currentTimeMillis() + ".png";
        int index = url.lastIndexOf("/");
        if (index > 0) {
            fileName = url.substring(index+1);
        }
        return fileName;
    }
}
