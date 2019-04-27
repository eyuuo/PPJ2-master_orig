package com.example.diaryoneline;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;

    //리사이클러 TOP
    private RecyclerView listview;
    private TOPAdapter adapter;
    //리사이클러 리스트
    private RecyclerView listview2;
    private ListAdapter2 adapter2;

    //시간 계산1
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM");
    String formatDate = sdfNow.format(date);
    TextView dateNow;
    //시간 계산 2

    SimpleDateFormat sdfNowYear = new SimpleDateFormat("yyyy");
    String nowYear = sdfNowYear.format(date);
    SimpleDateFormat sdfNowMonth = new SimpleDateFormat("MM");
    String nowMoth = sdfNowMonth.format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //현재 시간
        dateNow = (TextView) findViewById(R.id.dateNow);
        dateNow.setText(formatDate);

        //리사이클러뷰TOP
        init();
        //리사이클러리스트
        init2();

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);


        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

        // make new file


        fab1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Make_New_file.class);
                startActivity(intent);
            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //---------리사이클러TOP------------------

    private void init() {

        listview = findViewById(R.id.TOP_listview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listview.setLayoutManager(layoutManager);

        //날짜 계산
        int year = Integer.parseInt(nowYear);
        int month = Integer.parseInt(nowMoth);

        int [] Mdate = {31,28,31,30,31,30,31,31,30,31,30,31};
        //윤년 판단.
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            Mdate[1] = 29;
        }

        ArrayList<String> itemList = new ArrayList<>();
        String A;

        int M = Integer.parseInt(nowMoth);

        for(int i=1;i<=Mdate[M-1];i++)
        {
            A= String.valueOf(i);
            itemList.add(A);
        }


        adapter = new TOPAdapter(this, itemList, onClickItem);
        listview.setAdapter(adapter);

        MyTOPListDecoration decoration = new MyTOPListDecoration();
        listview.addItemDecoration(decoration);
    }


    private View.OnClickListener onClickItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = (String) v.getTag();
            Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
        }
    };

    //-----------리사이클러 리스트----------------

    private void init2() {

        listview2 = findViewById(R.id.main_listview);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listview2.setLayoutManager(layoutManager2);

        ArrayList<String> itemList2 = new ArrayList<>();
        String A;
        for(int i=0;i<10;i++)
        {
            //넣고 싶은 내용.
            A= String.valueOf(i);
            itemList2.add(A);
        }


        adapter2 = new ListAdapter2(this, itemList2, onClickItem2);
        listview2.setAdapter(adapter2);

        MyListDecoration decoration2 = new MyListDecoration();
        listview2.addItemDecoration(decoration2);
    }


    private View.OnClickListener onClickItem2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = (String) v.getTag();
            Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
        }
    };

    //---------------------------

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:
                anim();
                break;
            case R.id.fab1:
                // TODO: 위에꺼 여기다 정리하기
                anim();
                break;
            case R.id.fab2:
                anim();
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                // TODO: 버튼들을 인비저블하게(초기상태로 되돌리기) _ fab1,2 버튼 모두 적용
                break;
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_searching) {
            Toast.makeText(this ,"hi",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void anim() {

        if (isFabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }


    public void onCLickDate(View view) {
        Toast.makeText(this, "date", Toast.LENGTH_SHORT).show();
        //리사이클 뷰 보이기 안보이기
        //listview.setVisibility(View.VISIBLE);
        if(listview.getVisibility() == View.INVISIBLE)
            listview.setVisibility(View.VISIBLE);
        else listview.setVisibility(View.INVISIBLE);

    }
}
