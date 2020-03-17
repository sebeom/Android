package com.feelings.record;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
<<<<<<< HEAD
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
=======
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
>>>>>>> origin/db
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class FeelwriteActivity extends AppCompatActivity {
    public MutableLiveData<Boolean> flag;
    private DataRepository repository;
    private String saveFileUri;

    public static final int VERY_HAPPY=5;
    public static final int HAPPY=4;
    public static final int NORMAL=3;
    public static final int BAD=2;
    public static final int HORRIBLE=1;

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
<<<<<<< HEAD
    private TextView textView_Date;
    private TimePickerDialog.OnTimeSetListener callbackMethod;
    private EditText editText;

=======
    private RadioGroup radioGroup;
>>>>>>> origin/db

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feelwrite);
        initUI();
        repository = new DataRepository(getApplication());
        flag = new MutableLiveData<Boolean>();

        flag.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    finish();
                }
            }
        });


        //날짜선택
        EditText date = (EditText) findViewById(R.id.datePicker);

        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy년 MM월 dd일");
        Calendar Dtime = Calendar.getInstance();
        String format_time1 = format1.format(Dtime.getTime());
        date.setText(format_time1); //gettext 가져온다

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(FeelwriteActivity.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });






        //시간선택
        final EditText et_time = (EditText) findViewById(R.id.timePicker);

        SimpleDateFormat format2 = new SimpleDateFormat ( "HH시 mm분");
        Calendar Ttime = Calendar.getInstance();
        String format_time2 = format2.format(Ttime.getTime());
        et_time.setText(format_time2); //gettext

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

                mTimePicker = new TimePickerDialog(FeelwriteActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        if (selectedHour > 12) {
                            selectedHour -= 12;
                            state = "PM";
                        }
                        // EditText에 출력할 형식 지정
                        et_time.setText(/*state + " " + */selectedHour + "시 " + selectedMinute + "분");

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
            public void onClick(View v) {   //갤러리 사진 가져오기
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT); //action 세팅 (content)
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



                /*//리사이즈
                DisplayMetrics metrics = new DisplayMetrics();
                WindowManager windowManager = (WindowManager) getApplicationContext()
                        .getSystemService(Context.WINDOW_SERVICE);
                windowManager.getDefaultDisplay().getMetrics(metrics);


                ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) imageView.getLayoutParams();
                params.width = metrics.widthPixels;
                params.height = metrics.heightPixels;

                imageView.setLayoutParams(params);*/

            }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE)
        {
            if(resultCode == RESULT_OK) // 액션의 결과값
            {
                try{
                    DisplayMetrics met = new DisplayMetrics();
                    WindowManager manager = (WindowManager)getApplicationContext().getSystemService((Context.WINDOW_SERVICE));
                    manager.getDefaultDisplay().getMetrics(met);

                    imageView.setImageURI(data.getData());
                    ViewGroup.LayoutParams params = (ViewGroup.LayoutParams)imageView.getLayoutParams();
                    params.height = met.heightPixels/2;
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
        radioGroup = findViewById(R.id.radioGroup);
        imageView = findViewById(R.id.imageView);

        FloatingActionButton saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int typeId = radioGroup.getCheckedRadioButtonId();
                EditText inputcontent = findViewById(R.id.content);
                if(inputcontent.getText().toString().trim().length() == 0) return;
                if(imageView.toString().trim().length() == 0) return;
                if(typeId == -1) return;
                try{
                    String content = inputcontent.getText().toString().trim();

                    BitmapDrawable bitDraw = (BitmapDrawable)imageView.getDrawable();
                    Bitmap bitmap = bitDraw.getBitmap();

                    String fileName = UUID.randomUUID().toString();
                    File file = new File(getCacheDir(),fileName+".jpg");
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                    Data data = new Data();
                    data.setContent(content); //나머지 데이터들 넣기
                    data.setImageview(file.getPath());
                    data.setMood(getMoodType(typeId));

                    repository.insert(data, flag);
                }catch (Exception e){
                    e.printStackTrace();
                }

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
<<<<<<< HEAD


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
=======
    private int getMoodType(int id){
        int type=0;
        switch (id){
            case R.id.feelingBtn1:
                type = VERY_HAPPY;
                break;
            case R.id.feelingBtn2:
                type = HAPPY;
                break;
            case R.id.feelingBtn3:
                type = NORMAL;
                break;
            case R.id.feelingBtn4:
                type = BAD;
                break;
            case R.id.feelingBtn5:
                type = HORRIBLE;
                break;
        }
        return type;
>>>>>>> origin/db
    }
}
