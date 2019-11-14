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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androids.board.listviewadapter.ComputerListAdapter;
import com.example.androids.board.listviewadapter.EconomyListAdapter;
import com.example.androids.board.listviewadapter.HumanitiesListViewAdapter;
import com.example.androids.board.listviewadapter.RecruitmentListViewAdapter;
import com.example.androids.callback.OnEventListener;
import com.example.androids.databases.SendDataTest;
import com.example.androids.image.ImageLoader;
import com.example.androids.main.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SingleListView extends Activity {

    Context context;
    ImageView singleImage;
    TextView txTitle, txContent, txWriter, txLink;
    Button createTeam;
    ListView listviewRecruitment;
    RecruitmentListViewAdapter recruitmentListViewAdapter;
    HashMap<String, String> map; //이름 : 정보 로 값을 정하기 위해 <String , String> 사용
    JSONObject obj;
    String con_noData;
    public static String TITLE = "title";
    public static String RECR_NO = "recR_no";
    public static String CONTENT = "content";
    public static String CON_NO = "con_no";
    public static String PERSON_NUM = "person_num";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_board);
        ImageLoader imageLoader = new ImageLoader(context);

        Intent getData = getIntent();

        System.out.println(getData);
        con_noData = getData.getStringExtra("con_no");
        System.out.println("공모전 값 처음 : " + con_noData);
        String title = getData.getExtras().getString("title");
        String content = getData.getStringExtra("content");
        String writer = getData.getStringExtra("writer");
        String link = getData.getStringExtra("link");
        String image = getData.getStringExtra("image");

        singleImage = (ImageView) findViewById(R.id.singleBoardImage);
        txTitle = findViewById(R.id.singleTitle);
        txContent = findViewById(R.id.singleContent);
        createTeam = findViewById(R.id.createTeam);

        System.out.println("싱글" + image);
        txTitle.setText(title);
        txContent.setText(content);

        imageLoader.DisplayImage(image, singleImage); //imageLoader에 있는 displayimage를 사용하여 이미지 사용

        JSONObject json = new JSONObject();
        String url = "http://10.0.2.2:3000/contestRecu";

        SendDataTest someTask = new SendDataTest(url ,json ,getApplicationContext(), new OnEventListener<String>()  {

            @Override
            public void onSuccess(String result) {
                try{
                    System.out.println("싱글 결과 " + result);
                    recruitment(result);
                }
                catch(Exception e){
                }
            }
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getApplicationContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        someTask.execute();

        createTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),CreateTeam.class);
                next.putExtra("con_no", con_noData);
                System.out.println("공모전 값 : " + con_noData);
                startActivity(next);
            }
        });
    }


    private void recruitment(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        ArrayList<HashMap<String, String>> arrRec = new ArrayList<HashMap<String, String>>(); //이름 : 정보 로 구분짓기 편하게 위해 HashMap<String, String> 사용

        for (int i = 0; i < jsonArray.length(); i++) {
            map = new HashMap<String, String>(); //이름 : 정보 로 값을 정하기 위해 <String , String> 사용
            obj = jsonArray.getJSONObject(i);

            map.put("recr_no", obj.getString("recr_no"));
            map.put("title", obj.getString("title"));
            map.put("content", obj.getString("content"));
            map.put("person_num", obj.getString("person_num")); //서버에서 보내주는 값을 저장
            map.put("con_no",obj.getString("con_no"));

            arrRec.add(map);
        }
        Collections.reverse(arrRec); //최신순서대로 글을 띄우기 위해 reverse 사용
        listviewRecruitment = findViewById(R.id.listviewSmallProject);
        recruitmentListViewAdapter = new RecruitmentListViewAdapter(this, arrRec); //listview를 실행시킬곳에 필요한 arraylist 전송;

        listviewRecruitment.setAdapter(recruitmentListViewAdapter);

    }

}