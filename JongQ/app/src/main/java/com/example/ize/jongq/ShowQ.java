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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ize.jongq.RequestRepository.CurrentQueueDetailRepository;
import com.example.ize.jongq.RequestRepository.ReservingRepository;
import com.example.ize.jongq.StoreServiceData.ResBranchPickingStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ShowQ extends ActionBarActivity {



    //YourQ
    private int yourQ;
    public String resName;
    public String resBranch;
    public String queueType;
    public int totalQueue;


    public CurrentQueueDetailRepository dataStore;
    public ReservingRepository dataStoreReserv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_q);


        Intent intent = this.getIntent();
        resName = intent.getStringExtra("ResName");
        resBranch = intent.getStringExtra("ResBranch");
        queueType = intent.getStringExtra("QueueType");



        dataStore = new CurrentQueueDetailRepository(resName,resBranch,queueType);

        TextView texViewRes =(TextView) findViewById(R.id.showQ_txt_RestName);
        texViewRes.setText(resName);

        TextView texViewBranch =(TextView) findViewById(R.id.showQ_txt_branch);
        texViewBranch.setText("สาขา " + resBranch);

        /***********************************/
        /******* SERVICE TASK AREA **********/
        /***********************************/

        AsyncTask<String, Void, String> asyncBackground = new AsyncTask<String, Void, String>() {
            @Override
            protected void onPreExecute() {

            }

            @Override
            protected String doInBackground(String... params) {



                String realUrl = "http://jongqservice.azurewebsites.net/api/queueservice/currentqueuedetail";
                ServiceTask serviceObject = new ServiceTask();
                return serviceObject.requestPostCurrentQueueDetailContent(realUrl, dataStore);


            }

            @Override
            protected void onPostExecute(String res) {

                try {

//                    //total queue store it and send back at reserving
//                    "TotalQueue": 0,
//                    //current queue
//                    "QueueNum": 0,
//                    "QueueType": "A"

                    JSONObject json = new JSONObject(res);
                    totalQueue = json.getInt("TotalQueue");
                    String queueNum = json.getString("QueueNum");
                    String queueType = json.getString("QueueType");


                    TextView texViewTotal =(TextView) findViewById(R.id.showQ_txt_CurQ);
                    texViewTotal.setText(queueNum + queueType);

                    TextView textViewWait =(TextView) findViewById(R.id.showQ_txt_sumQ);
                    textViewWait.setText(totalQueue + "");




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

        String txt = "คิวของคุณ:   ";
        TextView textViewYourQ =(TextView) findViewById(R.id.showQ_txt_yourQ);
        textViewYourQ.setText(txt);
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

    // how you detect this is application-specific

       /* int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    //BntBooking Q
    public void BookingQ(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ShowQ.this);

        builder.setTitle("ยืนยันการจองคิว");
        builder.setMessage("คุณต้องการทำการจองคิวใช่หรือไม่?");
        builder.setPositiveButton("ยืนยัน",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String prefName = "UserData";
                SharedPreferences mPreferences = getSharedPreferences(prefName, Activity.MODE_PRIVATE);
                int userid = mPreferences.getInt("UserId", 0);
                String username = mPreferences.getString("Username", "");
                String nickname = mPreferences.getString("Nickname","");
                String token = mPreferences.getString("Token","");

                dataStoreReserv = new ReservingRepository(resName,resBranch,userid,nickname,queueType,token,totalQueue);
                /***********************************/
                /******* SERVICE TASK AREA **********/
                /***********************************/

                AsyncTask<String, Void, String> asyncBackground = new AsyncTask<String, Void, String>() {
                    ProgressDialog dialog;
                    @Override
                    protected void onPreExecute() {
                        dialog = ProgressDialog.show(ShowQ.this,"LogIn","Please wait");
                    }

                    @Override
                    protected String doInBackground(String... params) {



                        String realUrl = "http://jongqservice.azurewebsites.net/api/queueservice/reserving";
                        ServiceTask serviceObject = new ServiceTask();
                        return serviceObject.requestPostReServingContent(realUrl, dataStoreReserv);


                    }

                    @Override
                    protected void onPostExecute(String res) {

                        try {


                            JSONObject json = new JSONObject(res);
                            Boolean error = json.getBoolean("Error");
                            String msg = json.getString("MsgThai");

                            if(!error) {
                                dialog.dismiss();
                                Intent intent = new Intent(getApplicationContext()
                                        , ShowQ_afterReserve.class);
                                intent.putExtra("ResName", resName);
                                intent.putExtra("ResBranch", resBranch);
                                intent.putExtra("QueueType", queueType);

                                //checkidreserved
                                String prefName = "IsReserved";
                                SharedPreferences mPreferences = getSharedPreferences(prefName, Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = mPreferences.edit();
                                editor.putBoolean("reserve", true);
                                editor.commit();

                                //checkidreserved
                                String prefNameQueue = "QueueRefresh";
                                SharedPreferences mPreferencesQueue = getSharedPreferences(prefNameQueue, Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editorQueue = mPreferencesQueue.edit();
                                editorQueue.putString("ResName", resName);
                                editorQueue.putString("ResBranch",resBranch);
                                editorQueue.putString("QueueType",queueType);
                                editorQueue.commit();



                                startActivity(intent);
                                finish();


                            } else {
                                dialog.dismiss();
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowQ.this);
                                builder1.setMessage(msg);
                                builder1.setCancelable(true);
                                builder1.setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                TextView texViewRes =(TextView) findViewById(R.id.showQ_txt_RestName);
                                                texViewRes.setText(resName);

                                                TextView texViewBranch =(TextView) findViewById(R.id.showQ_txt_branch);
                                                texViewBranch.setText("สาขา " + resBranch);

                                                /***********************************/
                                                /******* SERVICE TASK AREA **********/
                                                /***********************************/

                                                AsyncTask<String, Void, String> asyncBackground = new AsyncTask<String, Void, String>() {
                                                    @Override
                                                    protected void onPreExecute() {

                                                    }

                                                    @Override
                                                    protected String doInBackground(String... params) {



                                                        String realUrl = "http://jongqservice.azurewebsites.net/api/queueservice/currentqueuedetail";
                                                        ServiceTask serviceObject = new ServiceTask();
                                                        return serviceObject.requestPostCurrentQueueDetailContent(realUrl, dataStore);


                                                    }

                                                    @Override
                                                    protected void onPostExecute(String res) {

                                                        try {

//                    //total queue store it and send back at reserving
//                    "TotalQueue": 0,
//                    //current queue
//                    "QueueNum": 0,
//                    "QueueType": "A"

                                                            JSONObject json = new JSONObject(res);
                                                            totalQueue = json.getInt("TotalQueue");
                                                            String queueNum = json.getString("QueueNum");
                                                            String queueType = json.getString("QueueType");


                                                            TextView texViewTotal =(TextView) findViewById(R.id.showQ_txt_CurQ);
                                                            texViewTotal.setText(queueNum + queueType);

                                                            TextView textViewWait =(TextView) findViewById(R.id.showQ_txt_sumQ);
                                                            textViewWait.setText(totalQueue + "");




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

                                                String txt = "คิวของคุณ:   ";
                                                TextView textViewYourQ =(TextView) findViewById(R.id.showQ_txt_yourQ);
                                                textViewYourQ.setText(txt);
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

        builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();


    }

    //BntCancel
    public void btnCancelQ (View view) {

    }

    //BntFavo
    public void btnFavo (View view) {

    }
}
