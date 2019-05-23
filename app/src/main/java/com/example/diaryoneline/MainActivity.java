package com.example.diaryoneline;

import android.app.Activity;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Mainactivity 종료시키기 //13
    public static Activity mainActivity;


    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;

    //리사이클러 TOP
    private RecyclerView listview;//month list
    private RecyclerView listview3;//Year list
    private TOPAdapter adapter;
    private TOPAdapter3 adapter3;
    //리사이클러 리스트

    private RecyclerView listview2;
    private ListAdapter2 adapter2;

    //시간 계산1
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy.MM.dd");
    String formatDate = sdfNow.format(date);
    TextView monthNow;
    TextView yearNow;
    //시간 계산 2

    SimpleDateFormat sdfNowYear = new SimpleDateFormat("yyyy");
    String nowYear = sdfNowYear.format(date);
    SimpleDateFormat sdfNowMonth = new SimpleDateFormat("MM");
    String nowMoth = sdfNowMonth.format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Mainactivity 종료시키기 //13
        mainActivity = MainActivity.this;



        //현재 시간 설정
        yearNow = (TextView) findViewById(R.id.yearNow);
        yearNow.setText(nowYear);
        monthNow = (TextView) findViewById(R.id.monthNow);
        monthNow.setText(nowMoth);

        //리사이클러뷰TOP
        init();//Month
        init3();//Year

        //리사이클러리스트
        listview2 = findViewById(R.id.main_listview);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listview2.setLayoutManager(layoutManager2);

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
                //12주차 주만
                anim();

            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //버튼 누르면 Intent!! 각 지정된 페이지로 이동한다.
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:
                anim();
                break;
            case R.id.fab1:
                //Intent 는 위에서 실행되도록 함.
                anim();
                break;
            case R.id.fab2:
                anim();
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                // 12주차 수정
                isFabOpen = true;
                anim();
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

    /*이유경 파트 시작 : 사이클 뷰 3개 조작*/
    //---------리사이클러TOP------------------

    private void init() {

        listview = findViewById(R.id.TOP_listview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listview.setLayoutManager(layoutManager);


        //날짜 계산
        int year = Integer.parseInt(nowYear);

        //month
        ArrayList<String> itemList = new ArrayList<>();
        String A;
        int M = Integer.parseInt(nowMoth);
        //month
        for(int i=1;i<=12;i++)
        {
            A= String.valueOf(i);
            itemList.add(A);
        }

        adapter = new TOPAdapter(this, itemList, onClickItem);
        listview.setAdapter(adapter);
        MyTOPListDecoration decoration = new MyTOPListDecoration();

        listview.addItemDecoration(decoration);

    }

    private void init3(){

        listview3 = findViewById(R.id.TOP_listview3);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listview3.setLayoutManager(layoutManager3);
        //year
        ArrayList<String> itemList3 = new ArrayList<>();
        String A3;
        int year = Integer.parseInt(nowYear);
        //year
        for(int i=2015;i<=year;i++)
        {
            A3= String.valueOf(i);
            itemList3.add(A3);
        }

        //month
        adapter3 = new TOPAdapter3(this, itemList3, onClickItem3);
        listview3.setAdapter(adapter3);

        //month
        MyTOPListDecoration decoration3 = new MyTOPListDecoration();
        listview3.addItemDecoration(decoration3);

    }


    //-----------리사이클러 리스트----------------

    private void init2() {
        /*
        listview2 = findViewById(R.id.main_listview);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listview2.setLayoutManager(layoutManager2);    */

        ArrayList<String> itemList2 = new ArrayList<>();

        String yearS = (String) yearNow.getText();
        String monthS = (String) monthNow.getText();

        String str =  yearS+"."+monthS+".";
        String str2 ;
        //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        String A =" ";
        String B =" ";
        String C =" ";
        //목표 : 파일 제목을 불러와 A에 저장해서 넣기.

        //넣고 싶은 내용.
        for(int i=1;i<=31;i++) {
            str2 = str + String.valueOf(i);

            A = fileYN(str2);
            B = findeFileName(str2);
            C = A+"\n"+B;
            //Toast.makeText(this, B, Toast.LENGTH_SHORT).show();
            if (A == "-1" || A == "0") {
            } else {
                itemList2.add(C);

            }
        }

        adapter2 = new ListAdapter2(this, itemList2, onClickItem2);
        listview2.setAdapter(adapter2);

        MyListDecoration decoration2 = new MyListDecoration();
        listview2.addItemDecoration(decoration2);
    }

    //중간 리스트 누르면
    private View.OnClickListener onClickItem2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = (String) v.getTag();
            //Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();

            // 수정하기로 떠나는 것
            Intent intent = new Intent(MainActivity.this, modify.class);
            startActivity(intent);
            finish();
        }
    };


    //-----------위에 날짜 누르면 리스트 뷰 보이기.

    public void onCLickMonth(View view) {
        //Toast.makeText(this, "date", Toast.LENGTH_SHORT).show();

        //리사이클 뷰 보이기 안보이기
        //listview.setVisibility(View.VISIBLE);
        if(listview.getVisibility() == View.INVISIBLE){
            listview.setVisibility(View.VISIBLE);
        }
        else listview.setVisibility(View.INVISIBLE);
        if(listview3.getVisibility() == View.VISIBLE)
            listview3.setVisibility(View.INVISIBLE);

    }

    public void onCLickYear(View view) {
        //Toast.makeText(this, "year", Toast.LENGTH_SHORT).show();
        //리사이클 뷰 보이기 안보이기
        //listview3.setVisibility(View.VISIBLE);
        if(listview3.getVisibility() == View.INVISIBLE){
            listview3.setVisibility(View.VISIBLE);

        }
        else {
            listview3.setVisibility(View.INVISIBLE);
        }
        if(listview.getVisibility() == View.VISIBLE)
            listview.setVisibility(View.INVISIBLE);

    }

    //TOP리스트의 날짜 누르면 보이는 반응
    ///TOP 리스트를 누륻기!!!! MONTH
    private View.OnClickListener onClickItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = (String) v.getTag();
            if(str.length()==1)
                monthNow.setText("0"+str);
            else
                monthNow.setText(str);
            init2();
            // Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
        }
    };
    ///TOP 리스트를 누륻기!!!! YEAR
    private View.OnClickListener onClickItem3 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = (String) v.getTag();
            yearNow.setText(str);
            init2();
            // Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
        }
    };

    //------파일이 존재하면 파일 이름 리턴, 없으면 -1리턴
    public String fileYN(String str){

        //str = "2019.05.16";

        try {
            FileInputStream fis = openFileInput(str);
            Scanner scanner = new Scanner(fis);
            //scanner.useDelimiter("\\Z");
            // String content = scanner.next();
            scanner.close();
            return str;
            //Toast.makeText(MainActivity.this, content, Toast.LENGTH_SHORT).show();
        }catch(FileNotFoundException e){
            //Toast.makeText(this, "file not found", Toast.LENGTH_SHORT).show();
            return "-1";
        }

    }
    public String findeFileName(String str){

        String str2;
        // str = "2019.05.17";
        try {
            FileInputStream fis = openFileInput(str);
            Scanner scanner = new Scanner(fis);
            scanner.useDelimiter("\\Z");
            String content = scanner.next();
            scanner.close();
            str2 = content.split("\\n")[0];

            //Toast.makeText(this, str2, Toast.LENGTH_SHORT).show();
            return str2;

        }catch(FileNotFoundException e){
            return "-1";
        }

    }

    //파일 이름을 넣으면 파일 내용 String을 리턴하는 함수!!
    public String findeFileContent(String str){

        String str2;
        // str = "2019.05.17";
        try {
            FileInputStream fis = openFileInput(str);
            Scanner scanner = new Scanner(fis);
            scanner.useDelimiter("\\Z");
            String content = scanner.next();
            scanner.close();
            str2 = content.split("\\n")[1];

            //Toast.makeText(this, str2, Toast.LENGTH_SHORT).show();
            return str2;

        }catch(FileNotFoundException e){
            return "-1";
        }

    }


    //—————————————
    /*이유경 파트 끝*/



}

/*
   //main에서
  public static Activity mainActivity; //Main 전역에
   mainActivity = MainActivity.this; //Main onclick
   //이동한 곳에서.
   //Main activity 종료시키기 //13
   MainActivity MmainActivity = (MainActivity)MainActivity.mainActivity;
   MmainActivity.finish();

   //Make_New_file.java
   //Make_New_file 종료시키기 //13
  public static Activity make_new_file;
   //Make_New_file 종료시키기 //13
   make_new_file = Make_New_file.this;

   //이동한 곳에서.
   //Make_New_file 종료시키기 //13
   Make_New_file Mmake_new_file = (Make_New_file)Make_New_file.make_new_file;
   Mmake_new_file.finish();
*/