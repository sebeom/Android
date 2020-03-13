package com.feelings.record;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Feel_List feel_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feel_list = new Feel_List();

//처음화면에 리스트형식으로 일기창이 보이게하기
        getSupportFragmentManager().beginTransaction().replace(R.id.container, feel_list).commit();

//메뉴버튼클릭시
        Button Menubutton = findViewById(R.id.menu);
        Menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupmenu(v);//메뉴바를 누르면 숨겨진 옵션메뉴가 뜨게하기

            }//Menubutton의 onClick 메서드 끝
        });//Menubutton의 setOnClickListener끝


        //리스트버튼 클릭시
        Button listButton = findViewById(R.id.cardMode);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "리스트선택", Toast.LENGTH_LONG).show();
            }//listButton onClick 메서드 끝
        });//listButton setOnClickListener끝

    }//onCreate 끝

    //숨겨진옵션메뉴
    private void popupmenu(View v) {


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

    }//MainActivity 끝
