package com.xm.requestService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by j on 2017/3/16.
 */

public class RegisterRequest {
    public static String registerRequest(String account,String password,String email,String problem,String answer){
        //拼装路径
        String path="/PATH";
        HttpURLConnection connection=null;
        try{
            URL url=new URL(path);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);

            String data = "account=" + account + "&password=" + password + "&email=" + email + "&problem=" + problem + "&answer=" + answer;

            connection.setDoOutput(true);
            OutputStream out=connection.getOutputStream();
            out.write(data.getBytes());

            int code=connection.getResponseCode();
            if(code==200){
                InputStream in=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                StringBuilder response=new StringBuilder();
                String line;
                while((line=reader.readLine())!=null){
                    response.append(line);
                }

                return response.toString();

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
