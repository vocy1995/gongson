package com.example.androids.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androids.board.BoardListView;
import com.example.androids.person.MyPage;
import com.example.androids.person.Student;

public class Main extends Activity {

    Button test, myPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        test = findViewById(R.id.test);
        myPage = findViewById(R.id.mypage);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent testt = new Intent(getApplicationContext(), BoardListView.class);
                startActivity(testt);
            }
        });

        myPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testt = new Intent(getApplicationContext(), MyPage.class);
                startActivity(testt);
            }
        });
    }
}
