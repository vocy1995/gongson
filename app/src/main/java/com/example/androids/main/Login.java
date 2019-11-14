package com.example.androids.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androids.callback.OnEventListener;
import com.example.androids.databases.SendDataTest;
import com.example.androids.person.FindInfo;
import com.example.androids.person.Register;
import com.example.androids.person.Student;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;


public class Login extends Activity {
    String s;
    EditText loginEdtID, loginEdtPW;
    Button loginBtnLogin;
    public String jsonData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this.getIntent());
        s = intent.getStringExtra("text");
        JSONObject pw;
        if(s == null){
            startActivity(new Intent(this, FirstActivity.class));
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.activity_main);
        }

        findViewById(R.id.loginBtnSignUp).setOnClickListener(sClickListener);
        findViewById(R.id.loginBtnFindPW).setOnClickListener(fClickListener);

        String url = "http://10.0.2.2:3000/login";
        JSONObject json = new JSONObject();

        loginEdtPW = findViewById(R.id.loginEdtPW);
        loginEdtID = findViewById(R.id.loginEdtID);
        loginBtnLogin = findViewById(R.id.loginBtnLogin);

        loginBtnLogin.setOnClickListener(v -> {
            try{
                json.accumulate("id", loginEdtID.getText().toString());
                json.accumulate("pw", loginEdtPW.getText().toString());

                SendDataTest someTask = new SendDataTest(url ,json ,getApplicationContext(), new OnEventListener<String>()  {

                    @Override
                    public void onSuccess(String result) {
                        System.out.println("온 값" + result);
                        if(!(result.equals(null))){
                            Student studentData = new Student();
                            try{
                                JSONArray jsonArray = new JSONArray(result);
                                JSONObject obj = jsonArray.getJSONObject(0);
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("name",obj.getString("name"));
                                map.put("st_num",obj.getString("st_num"));
                                String name = map.get("name");
                                String studentNum = map.get("st_num");
                                System.out.println("이름 : "+ name);
                                System.out.println("이름 : "+ studentNum);
                                studentData.setName(name);
                                studentData.setStNum(studentNum);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            Toast.makeText(getApplicationContext(), "성공하였습니다", Toast.LENGTH_LONG).show();
                            Intent search = new Intent(getApplicationContext(),Main.class);
                            startActivity(search);
                        }
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
        });



    }
    Button.OnClickListener sClickListener = new View.OnClickListener(){
        public void  onClick(View v) {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        }
    };

    Button.OnClickListener fClickListener = new View.OnClickListener(){
        public void  onClick(View v) {
            Intent a = new Intent(Login.this, FindInfo.class);
            startActivity(a);
        }
    };

    public void operator1(){
        startActivity(new Intent(this, FirstActivity.class));
        setContentView(R.layout.activity_main);
    }
    public void operator2(){
        setContentView(R.layout.activity_main);
    }
}
