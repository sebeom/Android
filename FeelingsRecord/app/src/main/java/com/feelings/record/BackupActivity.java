package com.feelings.record;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class BackupActivity extends AppCompatActivity {
    private static final String TAG = null;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backup);

        //로그인 버튼을 클릭하면 로그인 의도를 시작하십시오.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });
        Button googleBackupButton = findViewById(R.id.googleBackupButton);
        googleBackupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), MyPrefsBackupAgent.class);
                startActivity(intent);
            }
        });


        // 사용자 ID, 이메일 주소 및 기본을 요청하도록 로그인 구성
        // 프로필. ID 및 기본 프로필은 DEFAULT_SIGN_IN에 포함되어 있습니다.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // gso에서 지정한 옵션으로 GoogleSignInClient를 빌드합니다.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1004);

    }

    //로그인 할 Google 계정을 선택하라는 메시지가 표시됩니다. 프로필,
    //이메일 및 openid 이외의 범위를 요청한 경우 요청 된 리소스에 대한 액세스 권한을 부여하라는 메시지도 사용자에게 표시됩니다.
    //마지막으로 활동 결과를 처리하십시오.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // GoogleSignInClient.getSignInIntent (...)에서 인 텐트를 시작한 결과가 반환되었습니다.
        if (requestCode == 1004) {
            // 이 호출에서 반환 된 작업은 항상 완료되며 리스너를 연결할 필요가 없습니다.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // 성공적으로 로그인했으며 인증 된 UI를 표시합니다.
            updateUI(account);
        } catch (ApiException e) {
            // ApiException 상태 코드는 자세한 실패 이유를 나타냅니다.
            // 자세한 내용은 GoogleSignInStatusCodes 클래스 참조를 참조하십시오.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }





    private void updateUI(GoogleSignInAccount account) {

    }
}


