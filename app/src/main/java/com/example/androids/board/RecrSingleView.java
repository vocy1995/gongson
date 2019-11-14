package com.example.androids.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androids.board.listviewadapter.RecruitmentListViewAdapter;
import com.example.androids.image.ImageLoader;
import com.example.androids.main.R;
import org.json.JSONObject;

public class RecrSingleView extends Activity {

    Context context;
    TextView txTitle, txContent, txCount;
    Button apply, decision;
    EditText applyContent;
    ListView listviewRecruitment;
    RecruitmentListViewAdapter recruitmentListViewAdapter;
    HashMap<String, String> map; //이름 : 정보 로 값을 정하기 위해 <String , String> 사용
    JSONObject obj;

    public static String TITLE = "title";
    public static String RECR_NO = "recR_no";
    public static String CONTENT = "content";
    public static String CON_NO = "con_no";
    public static String PERSON_NUM = "person_num";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_recr);
        ImageLoader imageLoader = new ImageLoader(context);

        Intent getData = getIntent();
        String con_no = getData.getStringExtra("con_no");
        String title = getData.getStringExtra("title");
        String content = getData.getStringExtra("content");
        String recr_no = getData.getStringExtra("recr_no");
        String person_num = getData.getStringExtra("person_num");

        txTitle.setText(title);
        txContent.setText(content);
        txCount.setText(person_num);

    }


}
