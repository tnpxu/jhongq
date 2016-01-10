package com.example.ize.jongq;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ize.jongq.StoreServiceData.ResPickingStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawer extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    //Single choice Dialog
    private String[] commandArray = new String[]{"ภาคกลาง", "ภาคเหนือ", "ภาคใต้", "ภาคตะวันออกเฉียงเหนือ"};

    //Gridview
    private GridView gridView;
    private ArrayList<Drawable> images;
    private String SENDER_ID = "699773429021";


    // NavigationView Menu select
    NavigationView navigation;

    //service data
    public ArrayList<ResPickingStorage> list = new ArrayList<>();

    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        initInstances();
        //initInstancesTabBar();




        /***********************************/
        /******* SERVICE TASK AREA **********/
        /***********************************/


        AsyncTask<String, Void, String> asyncBackground = new AsyncTask<String, Void, String>() {
            @Override
            protected void onPreExecute() {

            }

            @Override
            protected String doInBackground(String... params) {



                String realUrl = "http://jongqservice.azurewebsites.net/api/queueservice/respicking";
                ServiceTask serviceObject = new ServiceTask();
                return serviceObject.requestPostResPickingContent(realUrl);


            }

            @Override
            protected void onPostExecute(String res) {
                try {
                    JSONArray json = new JSONArray(res);
                    for(int i=0;i<json.length();i++){

//                        {
//                            "ResName": "BonChon",
//                                "ResType": "Grill",
//                                "CountBranch": 8
//                        }

                        JSONObject obj = json.getJSONObject(i);
                        String resname = obj.getString("ResName");
                        int countbranch = obj.getInt("CountBranch");

//                        Toast.makeText(getApplicationContext(), resname + countbranch,
//                                Toast.LENGTH_LONG).show();

                        ResPickingStorage temp = new ResPickingStorage(resname,countbranch);
                        list.add(temp);


                    }

                    /******************** adapter **************************/

                    //grigview view Matching
                    gridView = (GridView) findViewById(R.id.gridView);
                    //Images
                    images = new ArrayList<Drawable>();

                    String[] resname = new String[list.size()];
                    String[] resnum = new String[list.size()];

                    for (int j = 0; j < list.size(); j++) {
                        ResPickingStorage anObject = list.get(j);
                        if(anObject.getResname().equals("BonChon")) {
                            images.add(getResources().getDrawable(R.drawable.logo_bonchon));
                            resname[j] = "" + anObject.getResname();
                            resnum[j] = "" + anObject.getCountbranch() + " สาขา";

                        }

                        if(anObject.getResname().equals("BarBQPlaza")) {
                            images.add(getResources().getDrawable(R.drawable.logo_bbq));
                            resname[j] = "" + anObject.getResname();
                            resnum[j] = "" + anObject.getCountbranch() + " สาขา";


                        }
                        if(anObject.getResname().equals("AfterYou")) {
                            images.add(getResources().getDrawable(R.drawable.logo_afteryou));
                            resname[j] = "" + anObject.getResname();
                            resnum[j] = "" + anObject.getCountbranch() + " สาขา";


                        }
                        if(anObject.getResname().equals("EatAmAre")) {
                            images.add(getResources().getDrawable(R.drawable.logo_eatamare));
                            resname[j] = "" + anObject.getResname();
                            resnum[j] = "" + anObject.getCountbranch() + " สาขา";


                        }
                    }

                    gridView.setAdapter(new GridViewAdapter(NavigationDrawer.this, images,resname,resnum));

                    //GridView ItemClick
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            TextView r = (TextView) view.findViewById(R.id.textView4);
                            String resname = r.getText().toString();

//                            Toast.makeText(getApplicationContext(), resname,
//                                Toast.LENGTH_LONG).show();
                            Intent i = new Intent(NavigationDrawer.this, BranchPage.class);
                            i.putExtra("ResName", resname);
                            startActivity(i);

                        }

                    });


                } catch (JSONException e) {
                    // manage exceptions
                }


            }

        };



        try {
            if (Build.VERSION.SDK_INT >= 11)
                asyncBackground.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
            else
                asyncBackground.execute().get();
        } catch (Exception e) {

        }
        /*************************************************************************/




        //intent EditProfile
        findViewById(R.id.nav_bnt_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext()
                        , EditProFile_Page.class);
                startActivity(intent);
            }
        });


    }


//    private void initInstancesTabBar() {
//        TabLayout tabLayout;
//        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
//        tabLayout.addTab(tabLayout.newTab().setText("แนะนำ"));
//        tabLayout.addTab(tabLayout.newTab().setText("ปิ้งย่าง"));
//        tabLayout.addTab(tabLayout.newTab().setText("ชาบู"));
//        tabLayout.addTab(tabLayout.newTab().setText("ของหวาน"));
//        tabLayout.addTab(tabLayout.newTab().setText("อื่นๆ"));
//    }

    //drawerLayout
    private void initInstances() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(NavigationDrawer.this, drawerLayout, R.string.hello_world, R.string.hello_world);
        drawerLayout.setDrawerListener(drawerToggle);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set user profile
        String prefName = "UserData";
        SharedPreferences mPreferences = getSharedPreferences(prefName, Activity.MODE_PRIVATE);
        String nickname = mPreferences.getString("Nickname","");
        TextView textUserName = (TextView) findViewById(R.id.textView_UserName);
        textUserName.setText(nickname);

        navigation = (NavigationView) findViewById(R.id.navigation);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navItem1:

                        String prefNameRe = "IsReserved";
                        SharedPreferences mPreferencesRe = getSharedPreferences(prefNameRe, Activity.MODE_PRIVATE);
                        boolean check = mPreferencesRe.getBoolean("reserve",false);
                        if(check){
                        Intent i = new Intent(NavigationDrawer.this, ShowQ_afterReserve.class);
                        startActivityForResult(i, 0);
                        return true;
                        }
                        else{

//                            Toast.makeText(getApplicationContext(), "คุณยังไม่ได้ทำการจองคิว",
//                                    Toast.LENGTH_LONG).show();

                            final Toast toast1 = Toast.makeText(getApplicationContext(), "คุณยังไม่ได้ทำการจองคิว", Toast.LENGTH_SHORT);
                            toast1.show();

                            Handler handler1 = new Handler();
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    toast1.cancel();
                                }
                            }, 1000);

                            break;
                        }
                    case R.id.navItem2:
//                        Intent i1 = new Intent(NavigationDrawer.this, Promotion_page.class);
//                        startActivityForResult(i1, 0);
//                        Toast.makeText(getApplicationContext(), "ระบบยังไม่เปิดให้ใช้",
//                                Toast.LENGTH_LONG).show();

                        final Toast toast2 = Toast.makeText(getApplicationContext(), "ระบบยังไม่เปิดให้ใช้", Toast.LENGTH_SHORT);
                        toast2.show();

                        Handler handler2 = new Handler();
                        handler2.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toast2.cancel();
                            }
                        }, 1000);
                        return true;
                    case R.id.navItem3:
//                        Intent i2 = new Intent(NavigationDrawer.this, nav_setting_page.class);
//                        startActivityForResult(i2, 0);
//                        Toast.makeText(getApplicationContext(), "ระบบยังไม่เปิดให้ใช้",
//                                Toast.LENGTH_LONG).show();
                        final Toast toast3 = Toast.makeText(getApplicationContext(), "ระบบยังไม่เปิดให้ใช้", Toast.LENGTH_SHORT);
                        toast3.show();

                        Handler handler3 = new Handler();
                        handler3.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toast3.cancel();
                            }
                        }, 1000);
                        break;
                    case R.id.navItem4:
                        String prefName = "UserData";
                        SharedPreferences mPreferences = getSharedPreferences(prefName, Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mPreferences.edit();
                        editor.clear();
                        editor.commit();

                        Intent i3 = new Intent(NavigationDrawer.this, MainActivity.class);
                        finish();
                        startActivityForResult(i3, 0);
                        break;
                }
                return false;
            }
        });
    }


    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        //Menu Item Click
        /*MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else {

        }


        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    private void IntentBranchPage () {

        Intent intent = new Intent(getApplicationContext()
                , BranchPage.class);
        startActivity(intent);
    }




    //Sing choice Dialog Method
   /* private void showDialog() {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("กรุณาเลือกภาค");
        builder.setItems(commandArray, new DialogInterface.OnClickListener() {

            //onClick Dialog ภาค Show branch list
            public void onClick(DialogInterface dialog, int which) {
                final String[] branchArray = new String[]{"สยามเซนเตอร์", "เทอมินอล21", "เซนทรันพระราม3", "เซนทรันลาดพร้าว"};

                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(NavigationDrawer.this);
                builder.setTitle("กรุณาเลือกสาขา");
                builder.setItems(branchArray, new DialogInterface.OnClickListener() {

                    //onClick Dialog branch Show PageJhongQ
                    public void onClick(DialogInterface dialog, int which) {
                        final String[] amount = new String[]{"1-3", "4-6", "8-10"};

                        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(NavigationDrawer.this);
                        builder.setTitle("เลือกจำนวนที่ต้องการจอง");
                        builder.setSingleChoiceItems(amount, 0, null);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                Intent i = new Intent(NavigationDrawer.this, ShowQ.class);
                                startActivity(i);

                                dialog.dismiss();
                            }
                        }).show();
                        builder.setNegativeButton("cancel",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();

                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                dialog.dismiss();
            }
        });

        //Bnt Cancel
        builder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }*/
}
