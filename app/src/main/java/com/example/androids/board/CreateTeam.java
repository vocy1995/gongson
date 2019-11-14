package com.example.androids.board;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androids.callback.OnEventListener;
import com.example.androids.databases.SendDataTest;
import com.example.androids.main.R;
import com.example.androids.person.Student;

import org.json.JSONObject;

public class CreateTeam extends Activity {

    EditText title, content, person_num;
    Button create;
    String con_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_team);

        title = findViewById(R.id.creTitle);
        content = findViewById(R.id.creContent);
        person_num = findViewById(R.id.creCount);
        create = findViewById(R.id.create);

        Intent getData = getIntent();
        con_no = getData.getStringExtra("con_no");


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject json = new JSONObject();
                String url = "http://10.0.2.2:3000/createTeam";
                try{
                    Student stuData = new Student();
                    System.out.println("con_no" + con_no);
                    json.accumulate("title", title.getText().toString());
                    json.accumulate("content", content.getText().toString());
                    json.accumulate("person_num", person_num.getText().toString());
                    json.accumulate("con_no", con_no);
                    json.accumulate("st_num", stuData.getStNum());

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
}
