package com.example.diaryoneline;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Make_New_file extends AppCompatActivity {

    int mYear, mMonth, mDay;
    TextView mTxtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make__new_file);

        // back to main menu
        Button OK = (Button) findViewById(R.id.OK_button);
        OK.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                //TODO : 메모리에 저장하는 기능

                // back to main
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        // set calendar
        mTxtDate = (TextView)findViewById(R.id.date);

        //현재 날짜 가져올 Calendar 선언
        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DATE);

        //화면에 텍스트뷰 업데이트하기
        UpdateNow();

    }

    public void date_clickOn(View v){
        switch(v.getId()){
            // 날짜 대화상자 버튼이 눌리면 대화상자 보여줌
            case R.id.set_date:
                new DatePickerDialog(Make_New_file.this, mDateSetListener, mYear, mMonth, mDay).show();

                break;

        }
    }

    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            mYear = year;
            mMonth = month;
            mDay = day;

            UpdateNow();
        }

    };

    void UpdateNow(){
        mTxtDate.setText(String.format("%d.%02d.%02d",mYear, mMonth+1, mDay));
    }
}


