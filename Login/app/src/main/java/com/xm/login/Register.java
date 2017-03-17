package com.xm.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xm.requestService.RegisterRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by j on 2017/3/15.
 */

public class Register extends Activity {

    private String account;
    private String password;
    private String problem;
    private String answer;

    private EditText et_account;
    private EditText et_password;
    private EditText et_ensure_password;
    private EditText et_problem;
    private EditText et_answer;
    private Button bt_register;
    private Button bt_return;

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.register);
        bt_register = (Button) findViewById(R.id.bt_register);
        bt_return = (Button) findViewById(R.id.bt_return_login);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int isRegisterInfoOk=isRegisterInfoOk();
                account=getAccount();
                password=getPassword();
                problem=getProblem();
                answer=getAnswer();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(isRegisterInfoOk == -1){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Register.this,"请将信息填写完整",Toast.LENGTH_LONG);
                                }
                            });

                        }else if(isRegisterInfoOk == 0){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Register.this,"两次密码不一致",Toast.LENGTH_LONG);
                                }
                            });

                        }else{
                            String response= RegisterRequest.registerRequest(account,password,problem,answer);
                            if(response == "success"){      //与获取服务器返回的内容一样，执行
                                Toast.makeText(Register.this,"已注册，即将返回登录界面",Toast.LENGTH_LONG);
                                Intent intent=new Intent(Register.this,Login.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(Register.this,response,Toast.LENGTH_LONG);
                            }
                        }
                    }
                }).start();
            }
        });

        bt_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register.this,Login.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private int isRegisterInfoOk(){
        String account=getAccount();
        String password=getPassword();
        String ensure_password=getEnsure_password();
        String problem=getProblem();
        String answer=getAnswer();
        if(account == null || password == null || ensure_password == null || problem == null || answer == null){
            return -1;
        }else if((password != null || ensure_password != null) && password != ensure_password){
            return 0;
        }else{
            return 1;
        }

    }

    public String getAccount(){
        et_account=(EditText)findViewById(R.id.et_register_account);
        String base64_account=et_account.getText().toString();
        base64_account=base64(base64_account);
        return  base64_account;
    }

    public String getPassword(){
        et_password=(EditText)findViewById(R.id.et_register_account);
        String base64_password=et_password.getText().toString();
        base64_password=base64(base64_password);
        return  base64_password;
    }

    public String getEnsure_password(){
        et_ensure_password=(EditText)findViewById(R.id.et_ensure_password);
        String base64_ensure_password=et_ensure_password.getText().toString();
        base64_ensure_password=base64(base64_ensure_password);
        return base64_ensure_password;
    }

    public String getProblem(){
        et_problem=(EditText)findViewById(R.id.et_problem);
        String base64_problem=et_problem.getText().toString();
        base64_problem=base64(base64_problem);
        return base64_problem;
    }

    public String getAnswer(){
        et_answer=(EditText)findViewById(R.id.et_answer);
        String base64_answer=et_answer.getText().toString();
        base64_answer=base64(base64_answer);
        return base64_answer;
    }

    public String base64(String content) {
        try {
            content = Base64.encodeToString(content.getBytes("utf-8"), Base64.DEFAULT);
            content = URLEncoder.encode(content);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
    }
}
