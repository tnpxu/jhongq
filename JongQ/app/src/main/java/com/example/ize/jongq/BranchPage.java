package com.example.ize.jongq;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ize.jongq.RequestRepository.ResBranchPickingRepository;
import com.example.ize.jongq.StoreServiceData.ResBranchPickingStorage;
import com.example.ize.jongq.StoreServiceData.ResPickingStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class BranchPage extends TabActivity {

    TabHost mTabHost;


    //Gridview
    private GridView gridView;
    private ArrayList<Drawable> images;
    private Context context;

    // listView
    private ListView listBranch;

    private ResBranchPickingRepository dataStore;
    public ArrayList<ResBranchPickingStorage> centralList = new ArrayList<>();
    public ArrayList<ResBranchPickingStorage> northeastList = new ArrayList<>();
    public ArrayList<ResBranchPickingStorage> westList = new ArrayList<>();
    public ArrayList<ResBranchPickingStorage> northList = new ArrayList<>();
    public ArrayList<ResBranchPickingStorage> eastList = new ArrayList<>();
    public ArrayList<ResBranchPickingStorage> southList = new ArrayList<>();

    public String resName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_page);

        //get resname from navigationdrawer
        Intent intent = this.getIntent();
        resName = intent.getStringExtra("ResName");

        dataStore = new ResBranchPickingRepository(resName);

        /***********************************/
        /******* SERVICE TASK AREA **********/
        /***********************************/

        AsyncTask<String, Void, String> asyncBackground = new AsyncTask<String, Void, String>() {
            @Override
            protected void onPreExecute() {
            }

            @Override
            protected String doInBackground(String... params) {



                String realUrl = "http://jongqservice.azurewebsites.net/api/queueservice/resbranchpicking";
                ServiceTask serviceObject = new ServiceTask();
                return serviceObject.requestPostResBranchPickingContent(realUrl, dataStore);


            }

            @Override
            protected void onPostExecute(String res) {

                try {
                    JSONArray json = new JSONArray(res);
                    for(int i=0;i<json.length();i++) {

//                        {
//                            "ResBranch": "a",
//                            "Region": "Central",
//                            "QueueStatus": true
//                        }

                        JSONObject obj = json.getJSONObject(i);
                        String resbranch = obj.getString("ResBranch");
                        String region = obj.getString("Region");
                        boolean queuestatus = obj.getBoolean("QueueStatus");

                        if(region.equals("Central")) {
                            ResBranchPickingStorage temp = new ResBranchPickingStorage(resbranch, region, queuestatus);
                            centralList.add(temp);
                        }

                        if(region.equals("NorthEast")) {
                            ResBranchPickingStorage temp = new ResBranchPickingStorage(resbranch, region, queuestatus);
                            northeastList.add(temp);
                        }

                        if(region.equals("West")) {
                            ResBranchPickingStorage temp = new ResBranchPickingStorage(resbranch, region, queuestatus);
                            westList.add(temp);
                        }

                        if(region.equals("North")) {
                            ResBranchPickingStorage temp = new ResBranchPickingStorage(resbranch, region, queuestatus);
                            northList.add(temp);
                        }

                        if(region.equals("East")) {
                            ResBranchPickingStorage temp = new ResBranchPickingStorage(resbranch, region, queuestatus);
                            eastList.add(temp);
                        }

                        if(region.equals("South")) {
                            ResBranchPickingStorage temp = new ResBranchPickingStorage(resbranch, region, queuestatus);
                            southList.add(temp);
                        }


                    }

                    if(centralList.size() > 0)
                        showBranchListViewRegion1(centralList);

                    if(northeastList.size() > 0)
                         showBranchListViewRegion2(northeastList);

                    if(westList.size() > 0)
                        showBranchListViewRegion3(westList);

                    if(northList.size() > 0)
                        showBranchListViewRegion4(northList);

                    if(eastList.size() > 0)
                        showBranchListViewRegion5(eastList);

                    if(southList.size() > 0)
                        showBranchListViewRegion6(southList);




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



        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost = getTabHost();

        mTabHost.addTab(mTabHost.newTabSpec("tab_test1").setIndicator("สาขา").setContent(R.id.branchTab));
        mTabHost.addTab(mTabHost.newTabSpec("tab_test2").setIndicator("โปรโมชั่น").setContent(R.id.promotionTab));


        mTabHost.setCurrentTab(0);

//Add item in listView Region
//        showBranchListViewRegion1();
//        showBranchListViewRegion2();
//        showBranchListViewRegion3();
//        showBranchListViewRegion4();
//        showBranchListViewRegion5();
//        showBranchListViewRegion6();


        //grigview view Matching Show on Promotion tab
        context = this;

        gridView = (GridView) findViewById(R.id.promotionList_gridView);
        //Images
        images = new ArrayList<Drawable>();
        images.add(getResources().getDrawable(R.drawable.pro_bbq_1));
        images.add(getResources().getDrawable(R.drawable.pro_bbq_2));
        images.add(getResources().getDrawable(R.drawable.pro_bbq_3));


        gridView.setAdapter(new GridViewAdapterPromotion(context, images));

        //GridView ItemClick
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String msg = "Select";
                msg += String.valueOf(position);
                switch (position) {

                    case 0:
                        IntentPromotionPage();

                        break;
                    case 1:
                        IntentPromotionPage();
                        break;

                    case 2:
                        IntentPromotionPage();

                        break;
                    case 3:
                        IntentPromotionPage();
                        break;
                }

            }

        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_branch_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void IntentPromotionPage() {

        Intent intent = new Intent(getApplicationContext()
                , Promotion_page.class);
        startActivity(intent);
    }

    // listViewBranch_region1
    private void showBranchListViewRegion1(ArrayList<ResBranchPickingStorage> data) {

        ListView list = (ListView) findViewById(R.id.List_Branch_region1);


////////Set Listview inside ScrollView: scrolling on Android//////////
        list.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
///////////////////////////////////////////////////////////////////////

        ArrayList<HashMap<String, String>> mylistData =

                new ArrayList<HashMap<String, String>>();

        String[] columnTags = new String[]{"col1", "col2"};


        int[] columnIds = new int[]{R.id.Column1, R.id.Column2};
        for (int i = 0; i < data.size(); i++)  //จำนวน Row
        {

            HashMap<String, String> map = new HashMap<String, String>();
            //initialize row data
            ResBranchPickingStorage anObject = data.get(i);
            map.put(columnTags[0], anObject.getResbranch());
            if(anObject.getQueuestatus()) {
                map.put(columnTags[1], "เปิด");
            }else {
                map.put(columnTags[1], "ปิด");
            }
            mylistData.add(map);
        }
        SimpleAdapter arrayAdapter =
                new SimpleAdapter(this, mylistData, R.layout.listview_row,
                        columnTags, columnIds);
        list.setAdapter(arrayAdapter);


        //onClick
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    //SingleChoice Dialog
                final String[] amount = new String[]{"1-3", "4-6", "8-10"};
                TextView r = (TextView) view.findViewById(R.id.Column1);
                final String resbranch = r.getText().toString();

                TextView d = (TextView) view.findViewById(R.id.Column2);
                String queuestatus = d.getText().toString();

                if(queuestatus.equals("เปิด")) {
                    android.app.AlertDialog.Builder builder = new AlertDialog.Builder(BranchPage.this);
                    builder.setTitle("เลือกจำนวนที่ต้องการจอง");
                    builder.setSingleChoiceItems(amount, 0, null);
                    builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {

                            String queuetype = "";
                            int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                            if (selectedPosition == 0) {
                                queuetype = "A";
                            }

                            if (selectedPosition == 1) {
                                queuetype = "B";
                            }

                            if (selectedPosition == 2) {
                                queuetype = "C";
                            }


                            Intent i = new Intent(BranchPage.this, ShowQ.class);
                            i.putExtra("ResName", resName);
                            i.putExtra("ResBranch", resbranch);
                            i.putExtra("QueueType", queuetype);
                            startActivity(i);

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
                }

                if(queuestatus.equals("ปิด")) {

                    android.app.AlertDialog.Builder builder = new AlertDialog.Builder(BranchPage.this);
                    builder.setTitle("ระบบจองร้านนี้ยังไม่เปิดให้บริการ");
                    builder.setNegativeButton("OK",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }

            }
        });
    }

    // listViewBranch_region2
    private void showBranchListViewRegion2(ArrayList<ResBranchPickingStorage> data) {

        ListView list = (ListView) findViewById(R.id.List_Branch_region2);

        ////////Set Listview inside ScrollView: scrolling on Android//////////
        list.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
///////////////////////////////////////////////////////////////////////

        ArrayList<HashMap<String, String>> mylistData =
                new ArrayList<HashMap<String, String>>();

        String[] columnTags = new String[]{"col1", "col2"};


        int[] columnIds = new int[]{R.id.Column1, R.id.Column2};
        for (int i = 0; i < data.size(); i++)  //จำนวน Row
        {
            HashMap<String, String> map = new HashMap<String, String>();
            //initialize row data
            ResBranchPickingStorage anObject = data.get(i);
            map.put(columnTags[0], anObject.getResbranch());
            if(anObject.getQueuestatus()) {
                map.put(columnTags[1], "เปิด");
            }else {
                map.put(columnTags[1], "ปิด");
            }

            mylistData.add(map);
        }
        SimpleAdapter arrayAdapter =
                new SimpleAdapter(this, mylistData, R.layout.listview_row,
                        columnTags, columnIds);
        list.setAdapter(arrayAdapter);


        //onClick
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//////////////

                Intent intent = new Intent(getApplicationContext()
                        , ShowQ.class);
                startActivity(intent);
            }
        });
    }

    // listViewBranch_region3
    private void showBranchListViewRegion3(ArrayList<ResBranchPickingStorage> data) {

        ListView list = (ListView) findViewById(R.id.List_Branch_region3);

        ////////Set Listview inside ScrollView: scrolling on Android//////////
        list.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
///////////////////////////////////////////////////////////////////////

        ArrayList<HashMap<String, String>> mylistData =
                new ArrayList<HashMap<String, String>>();

        String[] columnTags = new String[]{"col1", "col2"};


        int[] columnIds = new int[]{R.id.Column1, R.id.Column2};
        for (int i = 0; i < data.size(); i++)  //จำนวน Row
        {
            HashMap<String, String> map = new HashMap<String, String>();
            //initialize row data
            ResBranchPickingStorage anObject = data.get(i);
            map.put(columnTags[0], anObject.getResbranch());
            if(anObject.getQueuestatus()) {
                map.put(columnTags[1], "เปิด");
            }else {
                map.put(columnTags[1], "ปิด");
            }

            mylistData.add(map);
        }
        SimpleAdapter arrayAdapter =
                new SimpleAdapter(this, mylistData, R.layout.listview_row,
                        columnTags, columnIds);
        list.setAdapter(arrayAdapter);

        //onClick
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//////////////

                Intent intent = new Intent(getApplicationContext()
                        , ShowQ.class);
                startActivity(intent);
            }
        });
    }

    // listViewBranch_region4
    private void showBranchListViewRegion4(ArrayList<ResBranchPickingStorage> data) {

        ListView list = (ListView) findViewById(R.id.List_Branch_region4);

        ////////Set Listview inside ScrollView: scrolling on Android//////////
        list.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
///////////////////////////////////////////////////////////////////////

        ArrayList<HashMap<String, String>> mylistData =
                new ArrayList<HashMap<String, String>>();

        String[] columnTags = new String[]{"col1", "col2"};


        int[] columnIds = new int[]{R.id.Column1, R.id.Column2};
        for (int i = 0; i < data.size(); i++)  //จำนวน Row
        {
            HashMap<String, String> map = new HashMap<String, String>();
            //initialize row data
            ResBranchPickingStorage anObject = data.get(i);
            map.put(columnTags[0], anObject.getResbranch());
            if(anObject.getQueuestatus()) {
                map.put(columnTags[1], "เปิด");
            }else {
                map.put(columnTags[1], "ปิด");
            }

            mylistData.add(map);
        }
        SimpleAdapter arrayAdapter =
                new SimpleAdapter(this, mylistData, R.layout.listview_row,
                        columnTags, columnIds);
        list.setAdapter(arrayAdapter);

        //onClick
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//////////////

                Intent intent = new Intent(getApplicationContext()
                        , ShowQ.class);
                startActivity(intent);
            }
        });
    }

    // listViewBranch_region5
    private void showBranchListViewRegion5(ArrayList<ResBranchPickingStorage> data) {

        ListView list = (ListView) findViewById(R.id.List_Branch_region5);

        ////////Set Listview inside ScrollView: scrolling on Android//////////
        list.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
///////////////////////////////////////////////////////////////////////

        ArrayList<HashMap<String, String>> mylistData =
                new ArrayList<HashMap<String, String>>();

        String[] columnTags = new String[]{"col1", "col2"};


        int[] columnIds = new int[]{R.id.Column1, R.id.Column2};
        for (int i = 0; i < data.size(); i++)  //จำนวน Row
        {
            HashMap<String, String> map = new HashMap<String, String>();
            //initialize row data
            ResBranchPickingStorage anObject = data.get(i);
            map.put(columnTags[0], anObject.getResbranch());
            if(anObject.getQueuestatus()) {
                map.put(columnTags[1], "เปิด");
            }else {
                map.put(columnTags[1], "ปิด");
            }

            mylistData.add(map);
        }
        SimpleAdapter arrayAdapter =
                new SimpleAdapter(this, mylistData, R.layout.listview_row,
                        columnTags, columnIds);
        list.setAdapter(arrayAdapter);

        //onClick
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//////////////

                Intent intent = new Intent(getApplicationContext()
                        , ShowQ.class);
                startActivity(intent);
            }
        });
    }

    // listViewBranch_region6
    private void showBranchListViewRegion6(ArrayList<ResBranchPickingStorage> data) {

        ListView list = (ListView) findViewById(R.id.List_Branch_region6);

        ////////Set Listview inside ScrollView: scrolling on Android//////////
        list.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        ///////////////////////////////////////////////////////////////////////

        ArrayList<HashMap<String, String>> mylistData =
                new ArrayList<HashMap<String, String>>();

        String[] columnTags = new String[]{"col1", "col2"};


        int[] columnIds = new int[]{R.id.Column1, R.id.Column2};
        for (int i = 0; i < data.size(); i++)  //จำนวน Row
        {
            HashMap<String, String> map = new HashMap<String, String>();
            //initialize row data
            ResBranchPickingStorage anObject = data.get(i);
            map.put(columnTags[0], anObject.getResbranch());
            if(anObject.getQueuestatus()) {
                map.put(columnTags[1], "เปิด");
            }else {
                map.put(columnTags[1], "ปิด");
            }

            mylistData.add(map);
        }
        SimpleAdapter arrayAdapter =
                new SimpleAdapter(this, mylistData, R.layout.listview_row,
                        columnTags, columnIds);
        list.setAdapter(arrayAdapter);


        //onClick
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//////////////




                ///////////////////////////////////////////////////////
                Intent intent = new Intent(getApplicationContext()
                        , ShowQ.class);
                startActivity(intent);
            }
        });
    }
}
