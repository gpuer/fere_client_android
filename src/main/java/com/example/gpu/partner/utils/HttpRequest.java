package com.example.gpu.partner.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;


public class HttpRequest {
    private String Cookie="";
    /**
     * 返回String
     * **/
    public String doGet(String geturl,boolean utf8) {
        String realUrl=geturl;
        try {
            URL url=new URL(realUrl);
            URLConnection conn=url.openConnection();
            conn.setUseCaches(false);
            conn.setConnectTimeout(5000); //请求超时时间
            //协议头
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Cookie1",Cookie);
            conn.connect();
            Map<String, List<String>> headers=conn.getHeaderFields();
            if(headers.get("Cookie1")!=null){
                Cookie=headers.get("Cookie1").toString();
            }
            //4.2获取响应正文
            InputStream inStream = conn.getInputStream();
            BufferedReader in;
            if(utf8) {
            	in = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
            }else {
            	in = new BufferedReader(new InputStreamReader(inStream, "GBK"));
            }
            
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null){
                buffer.append(line);
            }
            String str = buffer.toString();
            return str;
        } catch (MalformedURLException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        finally {

        }
        return null;

    }
    /**
     * 返回String
     * **/
    public String doPost(String posturl,String params,boolean utf8,boolean isJson) {
        String retun="";
        try {
            URL url = new URL(posturl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);// 允许连接提交信息
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST");// 网页提交方式“GET”、“POST”
            //connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\\n");
            //connection.setRequestProperty("connection", "Keep-Alive");
            if(isJson) {
            	connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            }else {
            	connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            } 
            connection.setRequestProperty("Cookie1", Cookie);// 有网站需要将当前的session id一并上传
            OutputStream os = connection.getOutputStream();
            if(utf8) {
            	os.write(params.toString().getBytes("UTF-8"));
            }else {
            	os.write(params.toString().getBytes("GBK"));
            }
            os.close();
            InputStream inStream = connection.getInputStream();
            BufferedReader in;
            if(utf8) {
            	in = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
            }else {
            	in = new BufferedReader(new InputStreamReader(inStream, "GBK"));
            }
            Map<String, List<String>> headers=connection.getHeaderFields();
            if(headers.get("Cookie1")!=null)
                Cookie=headers.get("Cookie1").toString();
            Log.i("http",Cookie);
            String line = in.readLine();
            StringBuffer buffer = new StringBuffer();
            while (line != null) {
                buffer.append(line);
                line = in.readLine();
            }
            retun=buffer.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retun;
    }


    public String getCookie(){
        return Cookie;
    }
    public void setCookie(String c){
        Cookie=c;
    }
    public static byte[] streamToByte(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int c = 0;
        byte[] buffer = new byte[8 * 1024];
        try {
            while ((c = is.read(buffer)) != -1) {
                baos.write(buffer, 0, c);
                baos.flush();
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
