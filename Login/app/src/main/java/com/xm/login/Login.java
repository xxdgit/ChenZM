package com.xm.login;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xm.requestService.LoginRequest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by j on 2017/3/15.
 */

public class Login extends Activity {

    private static String account;
    private static String password;

    private Button login;
    private Button register;
    private EditText et_account;
    private EditText et_password;

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.login);

        login=(Button)findViewById(R.id.bt_login);
        register=(Button)findViewById(R.id.bt_registerView);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean isLoginInfoOk=isLoginInfoOk();
                account=getAccount();
                password=getPassword();
                new Thread(){

                    @Override
                    public void run() {
                        if(isLoginInfoOk == true){
                            final String response=LoginRequest.loginRequest(account,password);
                            if(response == "success"){      //与获取服务器返回的内容一样，执行
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Login.this,response,Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(Login.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                            }
                            else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Login.this,response,Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }

                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Login.this,"账户或密码不能为空",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                }.start();

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });

    }

    private boolean isLoginInfoOk(){
        String check_account=getAccount();
        String check_password=getPassword();
        if(check_account == null || check_password == null){
            return false;
        }else{
            return true;
        }

    }

    private String getAccount(){
        et_account=(EditText)findViewById(R.id.et_login_account);
        String base64_account=et_account.getText().toString();
        base64_account=base64(base64_account);
        return base64_account;
    }

    private String getPassword(){
        et_password=(EditText)findViewById(R.id.et_login_password);
        String base64_password=et_password.getText().toString();
        base64_password=base64(base64_password);
        return  base64_password;
    }

    public String base64(String content){
        try{
            content=Base64.encodeToString(content.getBytes("utf-8"),Base64.DEFAULT);
            content= URLEncoder.encode(content);
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return content;
    }

}
