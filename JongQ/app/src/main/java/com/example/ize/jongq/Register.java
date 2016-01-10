package com.example.ize.jongq;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ize.jongq.RequestRepository.RegisterRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Register extends ActionBarActivity {

    private EditText nicknameValue;
    private EditText usernameValue;
    private EditText passwordValue;
    private EditText confirmpasswordValue;
    private String genderValue = "Male";
    private EditText telValue;
    public static RegisterRepository dataStore;
    ProgressDialog dialog;

    //Single choice Dialog AddPic ***16JULY 2015***
    final String[] commandArray = new String[]{"???????", "??????????????????"};
    private static final int PICK_IMAGE = 1;
    private ImageView AddImage;
    private static final int TAKE_PICTURE = 100;
    private int state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.button_reg_Mgen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderValue = "Male";
            }
        });

        findViewById(R.id.button_reg_Fgen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderValue = "Female";
            }
        });

        AddImage = (ImageView) findViewById(R.id.addpic);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        /*getMenuInflater().inflate(R.menu.menu_register, menu);*/
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

    //BntCancel
    public void Close(View view) {
        Register.this.finish();
    }

    //BntRegister

    public void RegisterBnt(View view) {
        nicknameValue = (EditText)findViewById(R.id.editText_reg_name);
        usernameValue = (EditText)findViewById(R.id.editText_reg_email);
        passwordValue = (EditText)findViewById(R.id.editText_reg_psw);
        confirmpasswordValue = (EditText)findViewById(R.id.editText_reg_conpsw);
        telValue = (EditText)findViewById(R.id.editText_reg_phone);

        String u = usernameValue.getText().toString();
        String p = passwordValue.getText().toString();
        String n = nicknameValue.getText().toString();
        String t = telValue.getText().toString();
        String g = genderValue;


        dataStore = new RegisterRepository(u,p,n,t,g);

        /***********************************/
        /******* SERVICE TASK AREA **********/
        /***********************************/

        AsyncTask<String, Void, String> asyncBackground = new AsyncTask<String, Void, String>() {
            @Override
            protected void onPreExecute() {
                dialog = ProgressDialog.show(Register.this, "Registering", "Please wait");
            }

            @Override
            protected String doInBackground(String... params) {



                String realUrl = "http://jongqservice.azurewebsites.net/api/userservice/register";
                ServiceTask serviceObject = new ServiceTask();
                return serviceObject.requestPostRegisterContent(realUrl, dataStore);


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
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Register.this);
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

    //BntAddPic show single choice dialog ***16JULY 2015***
    public void AddPic(View view) {

        new AlertDialog.Builder(Register.this)
                .setTitle("Add your picture")
                .setItems(commandArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                //Camera
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, TAKE_PICTURE);
                                state = 0;

                                break;
                            case 1:
                                //Select pic from gallery
                                Intent i = new Intent();
                                i.setAction(Intent.ACTION_GET_CONTENT);
                                i.setType("image/*");
                                startActivityForResult(Intent.createChooser(i, "Select app to pick image"), PICK_IMAGE);
                                state = 1;

                                break;

                        }
                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

    }

    //set pic in imageView ***16JULY 2015***
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent returnedIntent) {

        if (state == 1) { //Add Pic from camera

            super.onActivityResult(requestCode, resultCode, returnedIntent);

            switch (requestCode) {
                case PICK_IMAGE:
                    if (resultCode == RESULT_OK) {

                        //Get URI Image
                        Uri imageUri = returnedIntent.getData();
                        //Get Path Image
                        String imagePath = findPath(imageUri);

                        Bitmap imageData = BitmapFactory.decodeFile(imagePath);
                        AddImage.setImageBitmap(imageData);
                    }
            }
        } else { //Add Pic from gallery


            if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
                Bitmap capturedImage = (Bitmap) returnedIntent.getExtras().get("data");
                AddImage.setImageBitmap(capturedImage);
            }

        }
    }

    //find path pic in gallery ???????????????????????
    private String findPath(Uri uri) {
        String imagePath;

        String[] column = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, column, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            imagePath = cursor.getString(columnIndex);

        } else {
            imagePath = uri.getPath();
        }
        return imagePath;


    }



}
