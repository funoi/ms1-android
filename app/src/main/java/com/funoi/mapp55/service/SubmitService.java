package com.funoi.mapp55.service;


import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class SubmitService {
    //Post方式的提交
    public String submitDataByPost(String path,String json) throws Exception{//注意参数的设定

        //网络的连接
        URL url=new URL(path);
        HttpURLConnection conn=(HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setReadTimeout(5*1000);

        //专门的为Post提交设置请求参数
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        // 设置发送的内容
        OutputStream os = conn.getOutputStream();
        os.write(json.getBytes(StandardCharsets.UTF_8));
        os.close();

        // 获取响应
        Reader reader = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
        Gson gson=new Gson();
        String result = gson.fromJson(reader, String.class);

        conn.disconnect();
        return result;
    }
}
