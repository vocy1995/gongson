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

public class ComputerListAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    TextView title;

    ImageView image;

    public ComputerListAdapter(Context context,
                                ArrayList<HashMap<String, String>> arraylist) { //TimeLine에서 보내주는 값 저장
        this.context = context;
        data = arraylist;
        imageLoader = new ImageLoader(context);
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

        View itemViewOne = inflater.inflate(R.layout.board_item, parent, false); //ListView 를위한 View 생성
        //View itemViewTwo = inflater.inflate(R.layout.board_item, parent, false); //ListView 를위한 View 생성

        HashMap<String, String> resultp = new HashMap<String, String>();

        resultp = data.get(position); //timeline에서 받은 값 저장

        title = (TextView) itemViewOne.findViewById(R.id.title);
        image = (ImageView) itemViewOne.findViewById(R.id.boardImage);

        String conNoTest = resultp.get(BoardListView.CON_NO);
        System.out.println("테스트 : " + conNoTest);
        title.setText(resultp.get(BoardListView.TITLE));

        imageLoader.DisplayImage(resultp.get(BoardListView.IMAGE), image); //imageLoader에 있는 displayimage를 사용하여 이미지 사용
        // Capture button clicks on ListView items

        image.setOnClickListener(new OnClickListener() {
            HashMap<String, String> single_resultp = new HashMap<String, String>();
            @Override
            public void onClick(View arg0) {//댓글을 사용하기 위한 onclick 뷰 사용
                single_resultp = data.get(position);

                Intent intent = new Intent(context, SingleListView.class);
                String  conNoTest = single_resultp.get(BoardListView.CON_NO);
                intent.putExtra("con_no",conNoTest);
                System.out.println("공모전 넘기기 전 값 : " + conNoTest);
                intent.putExtra("title",single_resultp.get(BoardListView.TITLE));
                intent.putExtra("content",single_resultp.get(BoardListView.CONTENT));
                intent.putExtra("image",single_resultp.get(BoardListView.IMAGE));
                intent.putExtra("link",single_resultp.get(BoardListView.LINK));
                intent.putExtra("writer",single_resultp.get(BoardListView.WRITER));
                context.startActivity(intent);

            }
        });
        return itemViewOne;
    }

}