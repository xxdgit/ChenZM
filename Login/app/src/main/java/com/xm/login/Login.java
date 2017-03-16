package com.xm.login;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xm.requestService.LoginRequest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        if(isLoginInfoOk == true){
                            String response=LoginRequest.loginRequest(account,password);
                            if(response==""){      //与获取服务器返回的内容一样，执行
                                Intent intent=new Intent(Login.this,MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(Login.this,response,Toast.LENGTH_LONG);
                            }

                        }else{
                            Toast.makeText(Login.this,"账户或密码不能为空",Toast.LENGTH_LONG);
                        }
                    }
                }).start();

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
        String account=getAccount();
        String password=getPassword();
        if(account == null || password == null){
            return false;
        }else{
            return true;
        }

    }

    private String getAccount(){
        et_account=(EditText)findViewById(R.id.et_login_account);
        String account=et_account.getText().toString();
        return account;
    }

    private String getPassword(){
        et_password=(EditText)findViewById(R.id.et_login_password);
        String password=et_password.getText().toString();
        return  password;
    }

}
