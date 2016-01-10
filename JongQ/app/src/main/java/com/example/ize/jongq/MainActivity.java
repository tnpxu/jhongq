package com.example.ize.jongq;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ize.jongq.RequestRepository.LoginRepository;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.microsoft.windowsazure.messaging.*;
import com.microsoft.windowsazure.notifications.NotificationsManager;


public class MainActivity extends ActionBarActivity {
    private CallbackManager callbackManager;
    private TextView info;
    private LoginButton loginButton;
    private EditText usernameValue;
    private EditText passwordValue;
    public static LoginRepository dataStore;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                info.setText("Welcome:  " +
                        loginResult.getAccessToken().getUserId() + "\n" +
                        "Auth Token: " + loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                Log.d("IZE", "onCancel");
            }

            @Override
            public void onError(FacebookException e) {
                Log.d("IZE", "onError " + e);
            }
        });

        //intent RegisterActivity
        findViewById(R.id.regis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext()
                        , Register.class);
                startActivity(intent);
            }
        });

        //intent None LogIn
        findViewById(R.id.textView_non_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext()
                        , NavigationDrawer.class);
                startActivity(intent);
            }
        });


        //get value form edit text

        usernameValue = (EditText)findViewById(R.id.Login_email);
        passwordValue = (EditText)findViewById(R.id.Login_psw);

        //intent NaviPage
        findViewById(R.id.Login_bnt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /******** get login form value *******/
                String us = usernameValue.getText().toString();
                String ps = passwordValue.getText().toString();
                dataStore = new LoginRepository(us, ps);


                /***********************************/
                /******* SERVICE TASK AREA **********/
                /***********************************/

                AsyncTask<String, Void, String> asyncBackground = new AsyncTask<String, Void, String>() {
                    @Override
                    protected void onPreExecute() {
                        dialog = ProgressDialog.show(MainActivity.this,"LogIn","Please wait");
                    }

                    @Override
                    protected String doInBackground(String... params) {



                        String realUrl = "http://jongqservice.azurewebsites.net/api/userservice/login";
                        ServiceTask serviceObject = new ServiceTask();
                        return serviceObject.requestPostLoginContent(realUrl, dataStore);


                    }

                    @Override
                    protected void onPostExecute(String res) {

                        try {
                            // store to sharedpreference
                            JSONObject json = new JSONObject(res);
                            Boolean error = json.getBoolean("Error");

                            if(!error) {
                                // store to sharedpreference
                                String prefName = "UserData";
                                SharedPreferences mPreferences = getSharedPreferences(prefName, Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = mPreferences.edit();

                                int userid = json.getInt("UserId");
                                String username = json.getString("Username");
                                String nickname = json.getString("Nickname");
                                String token = json.getString("Token");


                                editor.putInt("UserId", userid);
                                editor.putString("Username", username);
                                editor.putString("Nickname",nickname);
                                editor.putString("Token",token);
                                editor.putBoolean("IsLogIn",true);

                                editor.commit();



                                dialog.dismiss();
                                Intent intent = new Intent(getApplicationContext()
                                        , NavigationDrawer.class);
                                startActivity(intent);
                                finish();


                            } else {
                                String errormsg = "";
                                JSONArray jArr = json.getJSONArray("ErrorMsg");
                                for (int i=0; i < jArr.length(); i++) {
                                    JSONObject obj = jArr.getJSONObject(i);
                                    String message = obj.getString("Message");
                                    errormsg = errormsg + " - " + message;
                                }
                                dialog.dismiss();
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                                builder1.setMessage(errormsg);
                                builder1.setCancelable(true);
                                builder1.setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                builder1.setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();

                            }



                        } catch (JSONException e) {
                            // manage exceptions
                        }

                    }

                };


                if (Build.VERSION.SDK_INT >= 11)
                    asyncBackground.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                else
                    asyncBackground.execute();



                /*************************************************************************/


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }


}
