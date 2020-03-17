package com.feelings.record;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FeelwriteActivity extends AppCompatActivity {
    //날짜 시간
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };


    private static final int REQUEST_CODE = 0;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feelwrite);
        initUI();


        //날짜선택
        EditText date = (EditText) findViewById(R.id.datePicker);

        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy년 MM월 dd일");
        Calendar Dtime = Calendar.getInstance();
        String format_time1 = format1.format(Dtime.getTime());
        date.setText(format_time1);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getApplicationContext(), myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //시간선택
        final EditText et_time = (EditText) findViewById(R.id.timePicker);

        SimpleDateFormat format2 = new SimpleDateFormat ( "HH시 mm분");
        Calendar Ttime = Calendar.getInstance();
        String format_time2 = format2.format(Ttime.getTime());
        et_time.setText(format_time2);

        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                SimpleDateFormat format1 = new SimpleDateFormat ( "HH시 mm분");
                Calendar time = Calendar.getInstance();
                String format_time1 = format1.format(time.getTime());
                et_time.setText(format_time1);

                mTimePicker = new TimePickerDialog(getApplicationContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        if (selectedHour > 12) {
                            selectedHour -= 12;
                            state = "PM";
                        }
                        // EditText에 출력할 형식 지정
                        et_time.setText(state + " " + selectedHour + "시 " + selectedMinute + "분");

                        // SimpleDateFormat format1 = new SimpleDateFormat ( state +"HH시 mm분");
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        //사진추가
        imageView = (ImageView)findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });



        //메뉴버튼클릭시
        Button Menubutton= findViewById(R.id.menu);
        Menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupmenu(v);//메뉴바를 누르면 숨겨진 옵션메뉴가 뜨게하기

            }//Menubutton의 onClick 메서드 끝
        });//Menubutton의 setOnClickListener끝


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                try{
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    imageView.setImageBitmap(img);
                }catch(Exception e)
                {

                }
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initUI() {
        FloatingActionButton saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText et_date = (EditText) findViewById(R.id.datePicker);
        et_date.setText(sdf.format(myCalendar.getTime()));

        String myFormat2 = "HH시 mm분";
    }


    //숨겨진옵션메뉴
    private void popupmenu(View v){


        PopupMenu p = new PopupMenu(
                getApplicationContext(), // 현재 화면의 제어권자
                v); // anchor : 팝업을 띄울 기준될 위젯
        getMenuInflater().inflate(R.menu.popupmenu, p.getMenu());
        // 이벤트 처리
        p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getApplicationContext(),
                        "팝업메뉴 이벤트 처리 - "
                                + item.getTitle(),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        p.show(); // 메뉴를 띄우기
    }

//숨겨진버튼
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.popupmenu, menu);
        return true;
    }
    public void dd(View v) {
        Toast.makeText(getApplicationContext(), "dd", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == 1) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
