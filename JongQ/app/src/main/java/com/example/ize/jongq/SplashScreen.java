package com.example.ize.jongq;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import com.microsoft.windowsazure.messaging.*;
import com.microsoft.windowsazure.notifications.NotificationsManager;

public class SplashScreen extends Activity{

    private Handler myHandler;

    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.splashscreen);



        Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String prefName = "UserData";
                SharedPreferences mPreferences = getSharedPreferences(prefName, Activity.MODE_PRIVATE);
                boolean check = mPreferences.getBoolean("IsLogIn",false);

                if(check) {
                    finish();
                    Intent goMain = new Intent(getApplicationContext(), NavigationDrawer.class);
                    startActivity(goMain);
                } else {
                    finish();
                    Intent goMain = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(goMain);
                }
            }
        },3000);
    }
}
