package com.feelings.record;

import android.app.backup.BackupAgentHelper;
import android.app.backup.FileBackupHelper;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.SharedPreferences;

public class MyPrefsBackupAgent extends BackupAgentHelper {

    static final String PREFS = "TEST";  // SharedPreferences 파일의 이름
    static final String PREFS_BACKUP_KEY = "AEdPqrEAAAAIZZvCDThZh8M_osvijf4MiG1qWoYV9vA1PIhQRA";
    // 백업 데이터 세트를 고유하게 식별하는 키

    // 도우미를 할당하고 백업 에이전트에 추가
    @Override
    public void onCreate() {
        SharedPreferencesBackupHelper helper =
                new SharedPreferencesBackupHelper(this, PREFS);
        addHelper(PREFS_BACKUP_KEY, helper);

    }
    /*android.content.SharedPreferences 백업
    SharedPreferencesBackupHelper를 인스턴스화할 때
    하나 이상의 SharedPreferences 파일 이름을 포함해야 합니다.
    예를 들어, user_preferences라는 이름의 SharedPreferences 파일을
    백업하기 위해 BackupAgentHelper를 사용하는 백업 에이전트의 완성된 모습이 위에것.
    *****참고: SharedPreferences의 메서드는 스레드로부터 안전하므로
    백업 에이전트와 다른 활동의 공유 환경설정 파일을 안전하게 읽고 쓸 수 있습니다.*/
}

    /*다른 파일 백업
        FileBackupHelper를 인스턴스화할 때 앱의 내부 저장소(getFilesDir()에 의해 지정된 위치이며
        openFileOutput()이 파일을 쓰는 위치와 동일함)에 저장될 하나 이상의 파일 이름을 포함해야 합니다.
        예를 들어, scores와 stats라는 이름의 두 개의 파일을 백업하기 위해
        BackupAgentHelper를 사용하는 백업 에이전트는 다음과 같습니다.
        * 중요 밑과 같은 방식으로 사용하면 스레드로부터 보호되지 않아 추가로 작업할 것이 많아짐. /

/*public class MyFileBackupAgent extends BackupAgentHelper {
    // The name of the file
    static final String TOP_SCORES = "scores";
    static final String PLAYER_STATS = "stats";

    // A key to uniquely identify the set of backup data
    static final String FILES_BACKUP_KEY = "myfiles";

    // Allocate a helper and add it to the backup agent
    @Override
    public void onCreate() {
        FileBackupHelper helper = new FileBackupHelper(this,
                TOP_SCORES, PLAYER_STATS);
        addHelper(FILES_BACKUP_KEY, helper);
    }
}*/
