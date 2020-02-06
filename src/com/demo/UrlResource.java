package com.demo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class UrlResource {
    private static Decoder decoder = Base64.getDecoder();
    private static Encoder encoder = Base64.getEncoder();

    public static void main(String[] args) {

        try {
//            System.out.println(UrlResource.getUrlDetail("http://",true));
//            saveUrlFile("http://.","F:\\1.png");

            System.out.println(Arrays.toString(encoder.encode(getUrlFileData("http://"))));

            File file = new File("D:\\1.png");
            // 简历输出字节流
            FileOutputStream fos = new FileOutputStream(file);

            fos.write(getUrlFileData("http://"));
            System.out.println("Success");
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void saveUrlFile(String fileUrl, String fileDes) throws Exception {
        File toFile = new File(fileDes);
        if (toFile.exists()) {
            throw new Exception("File exist");
        }
        boolean flag = toFile.createNewFile();
        if (!flag) {
            throw new Exception("create File failed");
        }
        FileOutputStream outStream = new FileOutputStream(toFile);
//        System.out.println(Arrays.toString(getUrlFileData(fileUrl)));
        outStream.write(getUrlFileData(fileUrl));
        outStream.close();
    }
    public static byte[] getBase64UrlFileData(String fileUrl) {
        //发票PDF文件二进制流base64，如果是get请求还需要进行UrlEncode，使用sdk除外
        try {
            return encoder.encode(getUrlFileData(new String(decoder.decode(fileUrl))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 根据 URL 获取byte数据
     * @param fileUrl
     * @return
     * @throws Exception
     */
    public static byte[] getUrlFileData(String fileUrl) throws Exception {
        URL url = new URL(fileUrl);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.connect();
        InputStream is = httpConn.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        is.close();
        byte[] fileData = outputStream.toByteArray();
        outputStream.close();
        return fileData;
    }
    /**
     * 获取 URL 的字符数据
     * @param urlStr
     * @param withSep
     *          是否需要换行
     * @return
     * @throws Exception
     */
    public static String getUrlDetail(String urlStr, boolean withSep) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.connect();
        InputStream is = httpConn.getInputStream();
        BufferedReader bReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer();
        String readLine = "";
        while ((readLine = bReader.readLine()) != null) {
            if (withSep) {
                sb.append(readLine).append(System.getProperty("line.separator"));
            } else {
                sb.append(readLine);
            }
        }
        return sb.toString();
    }
}
