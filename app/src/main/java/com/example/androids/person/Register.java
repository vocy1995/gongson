package com.example.androids.person;
import android.app.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androids.callback.AsyncCallback;
import com.example.androids.callback.OnEventListener;
import com.example.androids.databases.SendDataTest;
import com.example.androids.main.Login;
import com.example.androids.main.R;

import org.json.JSONObject;


public class Register extends Activity{

    EditText txStNum, txPw, txMajor, txEmail, txGrade, txQuestion, txAnswer, txPwCheck, txName;
    Button signUpCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Button.OnClickListener uClickListener = new View.OnClickListener(){
            public void  onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                intent.putExtra("text", String.valueOf("false"));
                startActivity(intent);
            }
        };

        txStNum = findViewById(R.id.registerEdtID);
        txPw = findViewById(R.id.registerEdtPW);
        txMajor = findViewById(R.id.registerEdtDepartment);
        //txEmail = findViewById(R.id.registerEdtName);
        txGrade = findViewById(R.id.registerEdtGrade);
        txQuestion = findViewById(R.id.registerEdtQuestion);
        txAnswer = findViewById(R.id.registerEdtAnswer);
        txPwCheck = findViewById(R.id.registerEdtPwCheck);
        txName = findViewById(R.id.registerEdtName);

        signUpCheck = findViewById(R.id.signUpCheck);
        System.out.println("패스워드 " + txPw.toString());
        if(txPw.getText().toString().equals(txPwCheck.getText().toString())){
            signUpCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject json = new JSONObject();
                    String url = "http://10.0.2.2:3000/sign";
                    try{
                        json.accumulate("stNum", txStNum.getText().toString());
                        json.accumulate("password", txPw.getText().toString());
                        json.accumulate("major", txMajor.getText().toString());
                        json.accumulate("grade", txGrade.getText().toString());
                        json.accumulate("question", txQuestion.getText().toString());
                        json.accumulate("answer", txAnswer.getText().toString());
                        json.accumulate("name", txName.getText().toString());

                        SendDataTest someTask = new SendDataTest(url ,json ,getApplicationContext(), new OnEventListener<String>()  {

                            @Override
                            public void onSuccess(String result) {
                                Toast.makeText(getApplicationContext(), "성공하였습니다", Toast.LENGTH_LONG).show();
                                onBackPressed();
                            }

                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(getApplicationContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                        someTask.execute();
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
        }



    }
}