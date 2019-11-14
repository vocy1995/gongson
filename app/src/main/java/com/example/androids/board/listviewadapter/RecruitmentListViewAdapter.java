package com.example.androids.board.listviewadapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androids.board.BoardListView;
import com.example.androids.board.SingleListView;
import com.example.androids.callback.AsyncCallback;
import com.example.androids.callback.OnEventListener;
import com.example.androids.databases.SendDataTest;
import com.example.androids.image.ImageLoader;
import com.example.androids.image.TestImage;
import com.example.androids.main.R;
import com.example.androids.person.Student;

import org.json.JSONObject;

public class RecruitmentListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    TextView title, content, count;
    ImageView image;
    Button apply, decision;
    EditText applyContent;

    public RecruitmentListViewAdapter(Context context,
                                      ArrayList<HashMap<String, String>> arraylist) { //TimeLine에서 보내주는 값 저장
        this.context = context;
        data = arraylist;
    }

    @Override
    public int getCount() { //listview 출력되는 값을 위한 설정값
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables

        ArrayList<Integer> board_no = new ArrayList<>();

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE); //View를 직접사용하기위한 LayoutInflater 사용

        View itemViewOne = inflater.inflate(R.layout.single_recr, parent, false); //ListView 를위한 View 생성
        //View itemViewTwo = inflater.inflate(R.layout.board_item, parent, false); //ListView 를위한 View 생성

        HashMap<String, String> resultp = new HashMap<String, String>();

        resultp = data.get(position); //timeline에서 받은 값 저장
        String con_no = resultp.get(SingleListView.CON_NO);
        title = (TextView) itemViewOne.findViewById(R.id.recTitle);
        content = (TextView) itemViewOne.findViewById(R.id.recContent);
        count = (TextView) itemViewOne.findViewById(R.id.recCount);

        title.setText(resultp.get(SingleListView.TITLE));
        content.setText(resultp.get(SingleListView.CONTENT));
        count.setText(resultp.get(SingleListView.PERSON_NUM));

        applyContent = itemViewOne.findViewById(R.id.applyContent);
        apply = itemViewOne.findViewById(R.id.apply);
        decision = itemViewOne.findViewById(R.id.appDecision);

        apply.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {//댓글을 사용하기 위한 onclick 뷰 사용
                //applyContent.setVisibility(View.VISIBLE);
               // decision.setVisibility(View.VISIBLE);


            }
        });

        decision.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {//댓글을 사용하기 위한 onclick 뷰 사용
                JSONObject json = new JSONObject();
                String url = "http://10.0.2.2:3000/contestAttent";

                Student stuData = new Student();

                try{
                    System.out.println("con_no" + con_no);
                    System.out.println("name" + stuData.getName());
                    System.out.println("stNum" + stuData.getStNum());
                    System.out.println("content" + applyContent.getText().toString());
                    json.accumulate("name", stuData.getName());
                    json.accumulate("stNum", stuData.getStNum());
                    json.accumulate("conNo", con_no);
                    json.accumulate("content", applyContent.getText().toString());

                    SendDataTest someTask = new SendDataTest(url ,json ,context, new OnEventListener<String>()  {

                        @Override
                        public void onSuccess(String result) {
                            System.out.println("성공 메시지 : "  + result);
                            if(result.equals("success")){
                                System.out.println("성공하엿습니다.");
                                //Toast.makeText(context, "참가 신청이 완료 되었습니다.", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(context, "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                    someTask.execute();
                }catch(Exception e){
                }
            }
        });

        return itemViewOne;
    }

}