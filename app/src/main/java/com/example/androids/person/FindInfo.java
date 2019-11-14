package com.example.androids.person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androids.callback.OnEventListener;
import com.example.androids.databases.SendDataTest;
import com.example.androids.main.Main;
import com.example.androids.main.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class FindInfo extends Activity {
    EditText findInfoShowPW, txName, txStNum, txQuestion, txAnswer;
    TextView showpw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findinfo);
        findInfoShowPW = (EditText) findViewById((R.id.findInfoEdtShowPW));
        showpw = (TextView) findViewById((R.id.findInfoTxtShowPW));
        findInfoShowPW.setVisibility(View.INVISIBLE);
        showpw.setVisibility(View.INVISIBLE);


        txName = findViewById(R.id.findInfoEdtFindName);
        txStNum = findViewById(R.id.findInfoEdtFindClassnumber);
        txQuestion = findViewById(R.id.findInfoEdtFindQuestion);
        txAnswer = findViewById(R.id.findInfoEdtFindAnswer);

        findViewById(R.id.findInfoEdtFindPW).setOnClickListener(sClickListener);
        Intent intent = new Intent(this.getIntent());
    }
    Button.OnClickListener sClickListener = new View.OnClickListener(){
        public void  onClick(View v) {
            String url = "http://10.0.2.2:3000/findpw";


            try{
                JSONObject json = new JSONObject();
                json.accumulate("name", txName.getText().toString());
                json.accumulate("stNum", txStNum.getText().toString());
                json.accumulate("question", txQuestion.getText().toString());
                json.accumulate("answer", txAnswer.getText().toString());

                SendDataTest someTask = new SendDataTest(url ,json ,getApplicationContext(), new OnEventListener<String>()  {

                    @Override
                    public void onSuccess(String result) {
                        showpw.setVisibility(View.VISIBLE);
                        findInfoShowPW.setVisibility(View.VISIBLE);

                        try{
                            JSONArray jsonArray = new JSONArray(result);
                            JSONObject obj = jsonArray.getJSONObject(0);
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("password",obj.getString("password"));

                            String findPw = map.get("password");
                            System.out.println("찾은 비밀번호 : " + findPw);
                            findInfoShowPW.setText(findPw);
                        }catch(JSONException e){
                            e.printStackTrace();
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
        }
    };
}
