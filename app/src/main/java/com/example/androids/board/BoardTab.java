package com.example.androids.board;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.androids.main.R;

public class BoardTab extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contest_tab);

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
}
