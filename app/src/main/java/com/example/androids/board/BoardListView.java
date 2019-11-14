package com.example.androids.board;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.androids.board.listviewadapter.ComputerListAdapter;
import com.example.androids.board.listviewadapter.HumanitiesListViewAdapter;
import com.example.androids.board.listviewadapter.EconomyListAdapter;
import com.example.androids.callback.OnEventListener;
import com.example.androids.databases.SendDataTest;
import com.example.androids.main.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class BoardListView extends Activity{


    public static String TITLE = "title";
    public static String WRITER = "writer";
    public static String IMAGE = "image";
    public static String CONTENT = "content";
    public static String CON_NO = "con_no";
    public static String LINK = "link";

    final static String COMPUTER = "컴퓨터";
    final static String HUMANITIES = "인문";
    //final static String ECONOMY = "경제";

    ListView listviewCom, listviewHum, listviewEco;
    HumanitiesListViewAdapter adapterHum;
    ComputerListAdapter adapterCom;
    EconomyListAdapter adapterEco;
    HashMap<String, String> map; //이름 : 정보 로 값을 정하기 위해 <String , String> 사용
    JSONObject obj;

    public String jsonData;

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contest_tab);
        String url = "http://10.0.2.2:3000/board";
        JSONObject json = new JSONObject();

        SendDataTest someTask = new SendDataTest(url ,json ,getApplicationContext(), new OnEventListener<String>()  {

            @Override
            public void onSuccess(String result) {
                try{
                    jsonData = result;
                    boardData(jsonData);
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

        TabHost th = (TabHost)findViewById(R.id.th);
        th.setup();

        TabHost.TabSpec ts1 = th.newTabSpec("Tab1");
        ts1.setIndicator("언어");
        ts1.setContent(R.id.tab_view1);
        th.addTab(ts1);

        TabHost.TabSpec ts2 = th.newTabSpec("Tab2");
        ts2.setIndicator("기술");
        ts2.setContent(R.id.tab_view2);
        th.addTab(ts2);

        TabHost.TabSpec ts3 = th.newTabSpec("Tab3");
        ts3.setIndicator("예제");
        ts3.setContent(R.id.tab_view3);
        th.addTab(ts3);

        th.setCurrentTab(0);
    }

    private void boardData(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        ArrayList<HashMap<String, String>> arrComputer = new ArrayList<HashMap<String, String>>(); //이름 : 정보 로 구분짓기 편하게 위해 HashMap<String, String> 사용
        ArrayList<HashMap<String, String>> arrHumanities = new ArrayList<HashMap<String, String>>(); //이름 : 정보 로 구분짓기 편하게 위해 HashMap<String, String> 사용
        ArrayList<HashMap<String, String>> arrEconomy = new ArrayList<HashMap<String, String>>(); //이름 : 정보 로 구분짓기 편하게 위해 HashMap<String, String> 사용

        System.out.println("JsonArray: " + jsonArray);

        for (int i = 0; i < jsonArray.length(); i++) {
            map = new HashMap<String, String>(); //이름 : 정보 로 값을 정하기 위해 <String , String> 사용
            obj = jsonArray.getJSONObject(i);

            map.put("title", obj.getString("title"));
            map.put("image", obj.getString("image"));
            map.put("content", obj.getString("content"));
            map.put("con_no", obj.getString("con_no")); //서버에서 보내주는 값을 저장
            map.put("writer",obj.getString("writer"));
            map.put("link",obj.getString("link"));

            if(COMPUTER.equals(obj.get("type"))) {
                arrComputer.add(map); //arraylist 에 hashmap을 넣어줌으로써 arraylist에서 ㅂ구분짓게 함
                System.out.println("컴퓨터");
            }
            else if(HUMANITIES.equals(obj.get("type"))) {
                arrHumanities.add(map); //arraylist 에 hashmap을 넣어줌으로써 arraylist에서 ㅂ구분짓게 함
                System.out.println("인문");
            }
            else {
                arrEconomy.add(map);
                System.out.println("경제");
            }
        }
        Collections.reverse(arrComputer); //최신순서대로 글을 띄우기 위해 reverse 사용
        Collections.reverse(arrHumanities); //최신순서대로 글을 띄우기 위해 reverse 사용
        Collections.reverse(arrEconomy); //최신순서대로 글을 띄우기 위해 reverse 사용

        listviewHum = (ListView) findViewById(R.id.listviewHuman);
        listviewCom = (ListView) findViewById(R.id.listviewCom);
        listviewEco = (ListView) findViewById(R.id.listviewEco);

        adapterHum = new HumanitiesListViewAdapter(this, arrComputer); //listview를 실행시킬곳에 필요한 arraylist 전송;
        adapterCom = new ComputerListAdapter(this, arrHumanities); //listview를 실행시킬곳에 필요한 arraylist 전송;
        adapterEco = new EconomyListAdapter(this, arrEconomy); //listview를 실행시킬곳에 필요한 arraylist 전송;

        listviewHum.setAdapter(adapterHum);
        listviewCom.setAdapter(adapterCom);
        listviewEco.setAdapter(adapterEco);
    }

}
