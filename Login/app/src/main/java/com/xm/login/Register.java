package com.xm.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xm.requestService.RegisterRequest;

/**
 * Created by j on 2017/3/15.
 */

public class Register extends Activity {

    private String account;
    private String password;
    private String email;
    private String problem;
    private String answer;

    private EditText et_account;
    private EditText et_password;
    private EditText et_ensure_password;
    private EditText et_email;
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
                email=getEmail();
                problem=getProblem();
                answer=getAnswer();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(isRegisterInfoOk == -1){
                            Toast.makeText(Register.this,"请将信息填写完整",Toast.LENGTH_LONG);
                        }else if(isRegisterInfoOk == 0){
                            Toast.makeText(Register.this,"确认密码错误",Toast.LENGTH_LONG);
                        }else{
                            String response= RegisterRequest.registerRequest(account,password,email,problem,answer);
                            if(response==""){      //与获取服务器返回的内容一样，执行
                                Toast.makeText(Register.this,"已注册，即将返回登录界面",Toast.LENGTH_LONG);
                                Intent intent=new Intent(Register.this,Login.class);
                                startActivity(intent);
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
            }
        });

    }

    private int isRegisterInfoOk(){
        String account=getAccount();
        String password=getPassword();
        String ensure_password=getEnsure_password();
        String email=getEmail();
        String problem=getProblem();
        String answer=getAnswer();
        if(account == null || password == null || ensure_password == null || email == null || problem == null || answer == null){
            return -1;
        }else if((password != null || ensure_password != null) && password != ensure_password){
            return 0;
        }else{
            return 1;
        }

    }

    public String getAccount(){
        et_account=(EditText)findViewById(R.id.et_register_account);
        String account=et_account.getText().toString();
        return  account;
    }

    public String getPassword(){
        et_password=(EditText)findViewById(R.id.et_register_account);
        String password=et_password.getText().toString();
        return  password;
    }

    public String getEnsure_password(){
        et_ensure_password=(EditText)findViewById(R.id.et_ensure_password);
        String ensure_password=et_ensure_password.getText().toString();
        return ensure_password;
    }

    public String getEmail(){
        et_email=(EditText)findViewById(R.id.et_email);
        String email=et_email.getText().toString();
        return email;
    }

    public String getProblem(){
        et_problem=(EditText)findViewById(R.id.et_problem);
        String problem=et_problem.getText().toString();
        return problem;
    }

    public String getAnswer(){
        et_answer=(EditText)findViewById(R.id.et_answer);
        String answer=et_answer.getText().toString();
        return answer;
    }
}
