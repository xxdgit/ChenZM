package com.xm.requestService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by j on 2017/3/16.
 */

public class LoginRequest {

    public static String loginRequest(String account,String password){
        //拼装路径
        String path = "http://10.0.85.233/index.php/Admin/Index/test_android_get?account=" + account + "&password=" + password;
        HttpURLConnection connection=null;
        try{
            URL url=new URL(path);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            int code=connection.getResponseCode();
            if(code==200){
                InputStream in=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                StringBuilder response=new StringBuilder();
                String line;
                while((line=reader.readLine())!=null){
                    response.append(line);
                }

                return response.toString().trim();

            }else{
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if(connection!=null)
                connection.disconnect();
        }

    }
}
