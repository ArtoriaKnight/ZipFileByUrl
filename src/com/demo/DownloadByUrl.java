package com.demo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author PC
 */
public class DownloadByUrl {

    private static String url1 = "http://106.75.64.233:6379/img/galaxy.jpg";
    private static String url2 = "http://106.75.64.233:6379/img/castle.jpg";
    private static String url3 = "http://106.75.64.233:6379/img/bay.jpg";

    public static void main(String[] args) {
        try {
            downloadFromUrl(url1, "xingxi.png", "M:\\Workspace\\others");
            downloadFromUrl(url2, "chengbao.png", "M:\\Workspace\\others");
            downloadFromUrl(url3, "haiwan.png", "M:\\Workspace\\others");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从网络Url中下载文件
     * @param fileUrl
     * @param fileName
     * @param savePath
     * @throws Exception
     */
    public static void downloadFromUrl(String fileUrl, String fileName, String savePath) throws Exception {
        URL url = new URL(fileUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置超时时间
        conn.setConnectTimeout(3000);
        // 防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        // 获取输入流
        InputStream is = conn.getInputStream();
        // 获取数组
        byte[] getData = readInputStream(is);

        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }

        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        fos.close();
        is.close();

        System.out.println("info: " + url + " download success");
    }

    public static byte[] readInputStream(InputStream inputStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

}
