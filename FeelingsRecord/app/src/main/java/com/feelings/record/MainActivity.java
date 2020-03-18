package com.feelings.record;


import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.feelings.record.calchart.CalendarChartActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final int REQUEST_CODE_SIGN_IN = 1;
    private static final int REQUEST_CODE_OPEN_DOCUMENT = 2;

    private DriveServiceHelper mDriveServiceHelper;
    private String mOpenFileId;

    private Button button;
    private Button button2;

    private DataRepository repository;
    private LiveData<List<Data>> allDatas;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        // feel_list = new Feel_List();
        // feel_write = new Feel_write();

        repository = new DataRepository(getApplication());
        allDatas = repository.getAllDatas();
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);


        allDatas.observe(this, new Observer<List<Data>>() { // 구독
            @Override
            public void onChanged(List<Data> data) {

                if (adapter == null) {
                    adapter = new RecyclerAdapter(getApplicationContext(), data, 2); // 객체만들기
                    recyclerView.setAdapter(adapter); //세팅을 해준다.
                } else {
                    adapter.dataArrayList = data;
                    adapter.notifyDataSetChanged();
                }
            }
        });

//메뉴버튼클릭시
        Button Menubutton = findViewById(R.id.menu);
        Menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupmenu(v);//메뉴바를 누르면 숨겨진 옵션메뉴가 뜨게하기

            }//Menubutton의 onClick 메서드 끝
        });//Menubutton의 setOnClickListener끝


        //리스트버튼 클릭시(앨범으로 가기)
        Button listButton = findViewById(R.id.cardMode);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
                startActivity(intent);

            }//listButton onClick 메서드 끝
        });//listButton setOnClickListener끝

        //글쓰기버튼 클릭시
        FloatingActionButton writeButton = findViewById(R.id.fab);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "글쓰기선택", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), FeelwriteActivity.class);
                startActivity(intent);
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, feel_write).commit();

            }
        });//글쓰기 끝

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
                    Intent intent = new Intent();
                    switch (item.getItemId()) {
                        case R.id.w :
                            Toast.makeText(getApplicationContext(),
                                    "팝업메뉴 이벤트 처리 - "
                                            + item.getTitle(),
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.d :
                            intent = new Intent(getApplicationContext(), CalendarChartActivity.class);
                            startActivity(intent);
                        case R.id.c :
                            intent = new Intent(getApplicationContext(), BackupActivity.class);
                            startActivity(intent);
                            break;
                    }
                    return true;
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


//           findViewById(R.id.button).setOnClickListener(view -> openFilePicker());
//           findViewById(R.id.button2).setOnClickListener(view -> createFile());
//
//            requestSignIn();
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
            GoogleSignIn.getSignedInAccountFromIntent(resultData).addOnSuccessListener(new OnSuccessListener<GoogleSignInAccount>() {
                @Override
                public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                    Log.i(TAG, "Signed in as " + googleSignInAccount.getEmail());

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Unable to sign in!", e);
                        }
                    });
            switch (requestCode) {
                case REQUEST_CODE_SIGN_IN:
                    if (resultCode == Activity.RESULT_OK && resultData != null) {
                        handleSignInResult(resultData);
                    }
                    break;

                case REQUEST_CODE_OPEN_DOCUMENT:
                    if (resultCode == Activity.RESULT_OK && resultData != null) {
                        Uri uri = resultData.getData();
                        if (uri != null) {
                            openFileFromFilePicker(uri);
                        }
                    }
                    break;
            }

            super.onActivityResult(requestCode, resultCode, resultData);
        }

        private void requestSignIn() {
            Log.d(TAG, "Requesting sign-in");
            GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestScopes(new Scope(DriveScopes.DRIVE_FILE)).build();
            GoogleSignInClient client = GoogleSignIn.getClient(this, signInOptions);

            startActivityForResult(client.getSignInIntent(), REQUEST_CODE_SIGN_IN);
        }

        private void handleSignInResult(Intent result) {
            GoogleSignIn.getSignedInAccountFromIntent(result)
                    .addOnSuccessListener(googleAccount -> {
                        Log.d(TAG, "Signed in as " + googleAccount.getEmail());

                        GoogleAccountCredential credential =
                                GoogleAccountCredential.usingOAuth2(
                                        this, Collections.singleton(DriveScopes.DRIVE_FILE));
                        credential.setSelectedAccount(googleAccount.getAccount());
                        Drive googleDriveService =
                                new Drive.Builder(
                                        AndroidHttp.newCompatibleTransport(),
                                        new GsonFactory(),
                                        credential)
                                        .setApplicationName("Drive API Migration")
                                        .build();

                        mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
                    })
                    .addOnFailureListener(exception -> Log.e(TAG, "Unable to sign in.", exception));
        }

        private void openFilePicker() {
            if (mDriveServiceHelper != null) {
                Log.d(TAG, "Opening file picker.");

                Intent pickerIntent = mDriveServiceHelper.createFilePickerIntent();

                startActivityForResult(pickerIntent, REQUEST_CODE_OPEN_DOCUMENT);
            }
        }

        private void openFileFromFilePicker(Uri uri) {
            if (mDriveServiceHelper != null) {
                Log.d(TAG, "Opening " + uri.getPath());

                mDriveServiceHelper.openFileUsingStorageAccessFramework(getContentResolver(), uri)
                        .addOnSuccessListener(nameAndContent -> {
                            String name = nameAndContent.first;
                            String content = nameAndContent.second;

                            setReadOnlyMode();
                        })
                        .addOnFailureListener(exception ->
                                Log.e(TAG, "Unable to open file from picker.", exception));
            }
        }

        private void createFile() {
            if (mDriveServiceHelper != null) {
                Log.d(TAG, "Creating a file.");

                mDriveServiceHelper.createFile().addOnSuccessListener(fileId -> readFile(fileId)).addOnFailureListener(exception -> Log.e(TAG, "Couldn't create file.", exception));
            }
        }

        private void readFile(String fileId) {
            if (mDriveServiceHelper != null) {
                Log.d(TAG, "Reading file " + fileId);

                mDriveServiceHelper.readFile(fileId).addOnSuccessListener(nameAndContent -> {
                    String name = nameAndContent.first;
                    String content = nameAndContent.second;

                    setReadWriteMode(fileId);
                }).addOnFailureListener(exception -> Log.e(TAG, "Couldn't read file.", exception));
            }
        }

        private void setReadOnlyMode() {
            mOpenFileId = null;
        }

        private void setReadWriteMode(String fileId) {
            mOpenFileId = fileId;

        }

        //앨범 보기위한 권한 추가
       /* private static String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        private static final int REQUEST_EXTERNAL_STORAGE = 1;

        public static void verifyStoragePermissions(Activity activity) {
            int permission = ActivityCompat.checkSelfPermission(
                    activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        activity,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE*/



    }