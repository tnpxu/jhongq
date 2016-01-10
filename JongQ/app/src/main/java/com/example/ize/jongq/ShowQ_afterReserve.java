package com.example.ize.jongq;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ize.jongq.RequestRepository.CancelReserveRepository;
import com.example.ize.jongq.RequestRepository.ReserveUpdateRepository;
import com.example.ize.jongq.RequestRepository.SaveRegisNotiRepository;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import com.microsoft.windowsazure.messaging.*;
import com.microsoft.windowsazure.notifications.NotificationsManager;


public class ShowQ_afterReserve extends ActionBarActivity {


    //YourQ
    private int yourQ;
    public ReserveUpdateRepository dataStore;
    public String resName;
    public String resBranch;
    public String queueType;
    public String token;
    public int userid;
    public CancelReserveRepository dataStoreCancel;


    private GoogleCloudMessaging gcm;
    private Context context;
    private String registrationId;
    private String HUB_NAME = "pushnotijongq";
    private String SENDER_ID = "699773429021";
    private String CONNECTION_STRING = "Endpoint=sb://pushnotijongq.servicebus.windows.net/;SharedAccessKeyName=DefaultListenSharedAccessSignature;SharedAccessKey=Pls94XLZO0m18OU/iwhLXTsVawscgUAvGcWwN1s97GA=";
    public SaveRegisNotiRepository dataStoreRegisNoti;

    public Thread thread;

    public boolean flagCancel = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_q);

        //Set visible btn
        View btnReserve = findViewById(R.id.showQ_btn_reserve);
        View btnCancel = findViewById(R.id.showQ_btn_cancelQ);
        View txtYourQ = findViewById(R.id.showQ_txt_yourQ);
        View txtNoQ = findViewById(R.id.showQ_txt_noQ);
        btnReserve.setVisibility(View.GONE);
        btnCancel.setVisibility(View.VISIBLE);
        txtYourQ.setVisibility(View.VISIBLE);
        txtNoQ.setVisibility(View.VISIBLE);

//        Intent intent = this.getIntent();
//        resName = intent.getStringExtra("ResName");
//        resBranch = intent.getStringExtra("ResBranch");
//        queueType = intent.getStringExtra("QueueType");

        String prefNameQueue = "QueueRefresh";
        SharedPreferences mPreferencesQueue = getSharedPreferences(prefNameQueue, Activity.MODE_PRIVATE);
        resName = mPreferencesQueue.getString("ResName", "");
        resBranch = mPreferencesQueue.getString("ResBranch", "");
        queueType = mPreferencesQueue.getString("QueueType", "");

        TextView textResName = (TextView) findViewById(R.id.showQ_txt_RestName);
        textResName.setText(resName);
        TextView textResBranch = (TextView) findViewById(R.id.showQ_txt_branch);
        textResBranch.setText("สาขา " + resBranch);

        String prefName = "UserData";
        SharedPreferences mPreferences = getSharedPreferences(prefName, Activity.MODE_PRIVATE);
        token = mPreferences.getString("Token", "");
        userid = mPreferences.getInt("UserId", 9999);

        /*********************** PUSH NOTI *********************************/

        context = getApplicationContext();
        gcm = GoogleCloudMessaging.getInstance(this);


        /*********************** PUSH NOTI END*********************************/

        /****************************** REFRESH ****************************/
        thread = new Thread(new Runnable() {
            public boolean stopThread = false;

            @Override
            public void run() {
                while (!stopThread) {
                    try {
                        Thread.sleep(3000);
//                        getActivity().runOnUiThread(new Runnable() // start actions in UI thread
//                        {
//
//                            @Override
//                            public void run() {
//                                Toast.makeText(getActivity(), Resname + Resbranch,
//                                        Toast.LENGTH_LONG).show();
//                            }
//                        });


                        dataStore = new ReserveUpdateRepository(resName, resBranch, queueType, token);
                        /***********************************/
                        /******* SERVICE TASK AREA QUEUE****/
                        /***********************************/

                        AsyncTask<String, Void, String> asyncBackground4 = new AsyncTask<String, Void, String>() {


                            @Override
                            protected void onPreExecute() {

                            }

                            @Override
                            protected String doInBackground(String... params) {


                                String realUrl = "http://jongqservice.azurewebsites.net/api/queueservice/reserveupdate";
                                ServiceTask serviceObject = new ServiceTask();
                                return serviceObject.requestPostReServeUpdateContent(realUrl, dataStore);


                            }

                            @Override
                            protected void onPostExecute(final String res) {

                                try {

//                                            "CurrentQueue": 1,
//                                            "YourQueue": 1,
//                                            "QueueType": "A",
//                                            "Error": false
                                    JSONObject json = new JSONObject(res);
                                    final int currentQueue = json.getInt("CurrentQueue");
                                    final int yourQueue = json.getInt("YourQueue");
                                    final String queueType = json.getString("QueueType");
                                    boolean error = json.getBoolean("Error");


                                    if (!error) {
                                        ShowQ_afterReserve.this.runOnUiThread(new Runnable() // start actions in UI thread
                                        {

                                            @Override
                                            public void run() {

                                                TextView textCurrentQ = (TextView) findViewById(R.id.showQ_txt_CurQ);
                                                textCurrentQ.setText(currentQueue + queueType + "");

                                                TextView textViewWait = (TextView) findViewById(R.id.showQ_txt_sumQ);
                                                textViewWait.setText((yourQueue - currentQueue) + "");

                                                String txt = "คิวของคุณ:";
                                                TextView textViewYourQMsg = (TextView) findViewById(R.id.showQ_txt_yourQ);
                                                textViewYourQMsg.setText(txt);


                                                TextView textViewYourQ = (TextView) findViewById(R.id.showQ_txt_noQ);
                                                textViewYourQ.setText(yourQueue + queueType);

                                            }
                                        });

                                    } else {

//                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowQ_afterReserve.this);
//                                        builder1.setMessage("คิวของคุณได้ถูกข้ามไปแล้ว");
//                                        builder1.setCancelable(true);
//                                        builder1.setPositiveButton("OK",
//                                                new DialogInterface.OnClickListener() {
//                                                    public void onClick(DialogInterface dialog, int id) {
//                                                        dialog.cancel();
//                                                        finish();
//                                                    }
//                                                });
//                                        AlertDialog alert11 = builder1.create();
//                                        alert11.show();
                                        if(flagCancel) {
                                            Toast.makeText(ShowQ_afterReserve.this, "คิวของคุณได้ถูกยืนยันเรียบร้อยแล้ว",
                                                    Toast.LENGTH_LONG).show();
                                        }

//                                        final Toast toast1 = Toast.makeText(getApplicationContext(), "คิวของคุณได้ถูกข้ามไปแล้ว", Toast.LENGTH_SHORT);
//                                        toast1.show();
//
//                                        Handler handler1 = new Handler();
//                                        handler1.postDelayed(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                toast1.cancel();
//                                            }
//                                        }, 1000);
                                        stopThread = true;
                                        String prefNameRe = "IsReserved";
                                        SharedPreferences mPreferencesRe = getSharedPreferences(prefNameRe, Activity.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = mPreferencesRe.edit();
                                        editor.clear();
                                        editor.commit();
                                        finish();
                                    }

                                } catch (JSONException e) {

                                }

                            }

                        };


                        try {
                            if (Build.VERSION.SDK_INT >= 11)
                                asyncBackground4.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            else
                                asyncBackground4.execute();
                        } catch (Exception e) {

                        }


                        /*************************************************************************/


                    } catch (Exception e) {

                    }

                }
            }
        });
        thread.start();


        /*********************************** REFRESH END********************************************/


        /*********************** PUSH NOTI *********************************/

        /***********************************/
        /******* SERVICE TASK AREA **********/
        /***********************************/

        AsyncTask<String, Void, String> asyncBackgroundTop = new AsyncTask<String, Void, String>() {
            @Override
            protected void onPreExecute() {

            }

            @Override
            protected String doInBackground(String... params) {


                String message = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    registrationId = gcm.register(SENDER_ID);
                    message = "success";

                } catch (IOException ex) {
                    message = "fail";
                }
                return message;

            }

            @Override
            protected void onPostExecute(String res) {

                try {
                    if (res.equals("success")) {


                        dataStoreRegisNoti = new SaveRegisNotiRepository(token, registrationId);
                        /***********************************/
                        /******* SERVICE TASK AREA **********/
                        /***********************************/

                        AsyncTask<String, Void, String> asyncBackground = new AsyncTask<String, Void, String>() {
                            @Override
                            protected void onPreExecute() {

                            }

                            @Override
                            protected String doInBackground(String... params) {


                                String realUrl = "http://jongqservice.azurewebsites.net/api/notification/saveregisnoti";
                                ServiceTask serviceObject = new ServiceTask();
                                return serviceObject.requestPostSaveRegisNotiContent(realUrl, dataStoreRegisNoti);

                            }

                            @Override
                            protected void onPostExecute(String res) {

                                try {
                                    JSONObject json = new JSONObject(res);
                                    boolean status = json.getBoolean("Status");

                                    if (status) {

//                                        Toast.makeText(ShowQ_afterReserve.this, "Regis Noti key to service success",
//                                                Toast.LENGTH_LONG).show();


                                    }


                                } catch (Exception e) {
                                    // manage exceptions
                                }

                            }

                        };


                        if (Build.VERSION.SDK_INT >= 11)
                            asyncBackground.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        else
                            asyncBackground.execute();


                        /*************************************************************************/


                    } else {
                        Toast.makeText(ShowQ_afterReserve.this, "Fail to regis GCM",
                                Toast.LENGTH_LONG).show();
                    }


                } catch (Exception e) {
                    // manage exceptions
                }

            }

        };


        if (Build.VERSION.SDK_INT >= 11)
            asyncBackgroundTop.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            asyncBackgroundTop.execute();


        /*************************************************************************/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_show_q, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }


       /* int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


    //BntCancel
    public void btnCancelQ(View view) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowQ_afterReserve.this);
        builder1.setMessage("คุณต้องการยกเลิกคิวของคุณหรือไม่?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        flagCancel = false;
                        dataStoreCancel = new CancelReserveRepository(userid, resName, resBranch, queueType, token);

                        /***********************************/
                        /******* SERVICE TASK AREA **********/
                        /***********************************/

                        AsyncTask<String, Void, String> asyncBackground = new AsyncTask<String, Void, String>() {
                            @Override
                            protected void onPreExecute() {

                            }

                            @Override
                            protected String doInBackground(String... params) {


                                String realUrl = "http://jongqservice.azurewebsites.net/api/queueservice/cancelreserve";
                                ServiceTask serviceObject = new ServiceTask();
                                return serviceObject.requestPostCancelReserveContent(realUrl, dataStoreCancel);


                            }

                            @Override
                            protected void onPostExecute(String res) {

                                try {

//                            "UserId" : "6",
//                            "ResName" : "BonChon",
//                            "ResBranch" : "a",
//                            "QueueType" : "A",
//                            "Token" : "41rqz13Su9MGgyUBqpKzNE8tq"

                                    JSONObject json = new JSONObject(res);
                                    String prefNameRe = "IsReserved";
                                    SharedPreferences mPreferencesRe = getSharedPreferences(prefNameRe, Activity.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = mPreferencesRe.edit();
                                    editor.clear();
                                    editor.commit();
                                    finish();


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

    //BntFavo
    public void btnFavo(View view) {

    }
}
