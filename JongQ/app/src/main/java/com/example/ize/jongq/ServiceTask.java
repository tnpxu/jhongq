package com.example.ize.jongq;

import android.util.Log;

import com.example.ize.jongq.RequestRepository.CancelReserveRepository;
import com.example.ize.jongq.RequestRepository.CurrentQueueDetailRepository;
import com.example.ize.jongq.RequestRepository.LoginRepository;
import com.example.ize.jongq.RequestRepository.RegisterRepository;
import com.example.ize.jongq.RequestRepository.ResBranchPickingRepository;
import com.example.ize.jongq.RequestRepository.ReserveUpdateRepository;
import com.example.ize.jongq.RequestRepository.ReservingRepository;
import com.example.ize.jongq.RequestRepository.SaveRegisNotiRepository;
import com.example.ize.jongq.StoreServiceData.ResBranchPickingStorage;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by tnpxu on 30/6/2558.
 */
public class ServiceTask {

    public String requestPostLoginContent(String url , LoginRepository senddata) {


        HttpClient httpclient = new DefaultHttpClient();
        String result = null;

        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = null;
        InputStream instream = null;

        try {
            /*** json object **/

            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Username", senddata.getUsername());
            jsonObject.put("Password", senddata.getPassword());

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);
            // 6. set httpPost Entity
            httpPost.setEntity(se);
            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Content-type", "application/json");

            /*******************************/
            response = httpclient.execute(httpPost);

            Log.v("response code", response.getStatusLine()
                    .getStatusCode() + "");

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                instream = entity.getContent();
                result = convertStreamToString(instream);
            }

        } catch (Exception e) {
            // manage exceptions
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (Exception exc) {

                }
            }
        }

        return result;
    }


    public String requestPostRegisterContent(String url , RegisterRepository senddata) {


        HttpClient httpclient = new DefaultHttpClient();
        String result = null;

        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = null;
        InputStream instream = null;

        try {
            /*** json object **/

            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Username", senddata.getUsername());
            jsonObject.put("Password", senddata.getPassword());
            jsonObject.put("Nickname", senddata.getNickname());
            jsonObject.put("Tel", senddata.getTel());
            jsonObject.put("Gender", senddata.getGender());

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);
            // 6. set httpPost Entity
            httpPost.setEntity(se);
            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Content-type", "application/json");

            /*******************************/
            response = httpclient.execute(httpPost);

            Log.v("response code", response.getStatusLine()
                    .getStatusCode() + "");

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                instream = entity.getContent();
                result = convertStreamToString(instream);
            }

        } catch (Exception e) {
            // manage exceptions
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (Exception exc) {

                }
            }
        }

        return result;
    }

    public String requestPostResPickingContent(String url) {


        HttpClient httpclient = new DefaultHttpClient();
        String result = null;

        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = null;
        InputStream instream = null;

        try {
            /*** json object **/

            String json = "";
            JSONObject jsonObject = new JSONObject();

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);
            // 6. set httpPost Entity
            httpPost.setEntity(se);
            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Content-type", "application/json");

            /*******************************/
            response = httpclient.execute(httpPost);

            Log.v("response code", response.getStatusLine()
                    .getStatusCode() + "");

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                instream = entity.getContent();
                result = convertStreamToString(instream);
            }

        } catch (Exception e) {
            // manage exceptions
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (Exception exc) {

                }
            }
        }

        return result;
    }

    public String requestPostResBranchPickingContent(String url , ResBranchPickingRepository senddata) {


        HttpClient httpclient = new DefaultHttpClient();
        String result = null;

        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = null;
        InputStream instream = null;

        try {
            /*** json object **/

            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ResName", senddata.getResname());


            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);
            // 6. set httpPost Entity
            httpPost.setEntity(se);
            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Content-type", "application/json");

            /*******************************/
            response = httpclient.execute(httpPost);

            Log.v("response code", response.getStatusLine()
                    .getStatusCode() + "");

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                instream = entity.getContent();
                result = convertStreamToString(instream);
            }

        } catch (Exception e) {
            // manage exceptions
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (Exception exc) {

                }
            }
        }

        return result;
    }

    public String requestPostCurrentQueueDetailContent(String url , CurrentQueueDetailRepository senddata) {


        HttpClient httpclient = new DefaultHttpClient();
        String result = null;

        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = null;
        InputStream instream = null;

        try {
            /*** json object **/

            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ResName", senddata.getResname());
            jsonObject.put("ResBranch", senddata.getResbranch());
            jsonObject.put("QueueType", senddata.getQueuetype());

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);
            // 6. set httpPost Entity
            httpPost.setEntity(se);
            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Content-type", "application/json");

            /*******************************/
            response = httpclient.execute(httpPost);

            Log.v("response code", response.getStatusLine()
                    .getStatusCode() + "");

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                instream = entity.getContent();
                result = convertStreamToString(instream);
            }

        } catch (Exception e) {
            // manage exceptions
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (Exception exc) {

                }
            }
        }

        return result;
    }

    public String requestPostReServingContent(String url , ReservingRepository senddata) {


        HttpClient httpclient = new DefaultHttpClient();
        String result = null;

        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = null;
        InputStream instream = null;

        try {
            /*** json object **/

            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ResName", senddata.getResname());
            jsonObject.put("UserId", senddata.getUserid());
            jsonObject.put("Nickname", senddata.getNickname());
            jsonObject.put("QueueType", senddata.getQueuetype());
            jsonObject.put("ResBranch", senddata.getResbranch());
            jsonObject.put("Token", senddata.getToken());
            jsonObject.put("CurrentUserQueue", senddata.getCurrentuserqueue());

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);
            // 6. set httpPost Entity
            httpPost.setEntity(se);
            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Content-type", "application/json");

            /*******************************/
            response = httpclient.execute(httpPost);

            Log.v("response code", response.getStatusLine()
                    .getStatusCode() + "");

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                instream = entity.getContent();
                result = convertStreamToString(instream);
            }

        } catch (Exception e) {
            // manage exceptions
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (Exception exc) {

                }
            }
        }

        return result;
    }

    public String requestPostReServeUpdateContent(String url , ReserveUpdateRepository senddata) {


        HttpClient httpclient = new DefaultHttpClient();
        String result = null;

        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = null;
        InputStream instream = null;

        try {
            /*** json object **/

            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ResName", senddata.getResname());
            jsonObject.put("ResBranch", senddata.getResbranch());
            jsonObject.put("QueueType", senddata.getQueuetype());
            jsonObject.put("Token", senddata.getToken());

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);
            // 6. set httpPost Entity
            httpPost.setEntity(se);
            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Content-type", "application/json");

            /*******************************/
            response = httpclient.execute(httpPost);

            Log.v("response code", response.getStatusLine()
                    .getStatusCode() + "");

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                instream = entity.getContent();
                result = convertStreamToString(instream);
            }

        } catch (Exception e) {
            // manage exceptions
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (Exception exc) {

                }
            }
        }

        return result;
    }

    public String requestPostCancelReserveContent(String url , CancelReserveRepository senddata) {


        HttpClient httpclient = new DefaultHttpClient();
        String result = null;

        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = null;
        InputStream instream = null;

        try {
            /*** json object **/

            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserId", senddata.getUserid());
            jsonObject.put("ResName", senddata.getResname());
            jsonObject.put("ResBranch", senddata.getResbranch());
            jsonObject.put("QueueType", senddata.getQueuetype());
            jsonObject.put("Token", senddata.getToken());

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);
            // 6. set httpPost Entity
            httpPost.setEntity(se);
            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Content-type", "application/json");

            /*******************************/
            response = httpclient.execute(httpPost);

            Log.v("response code", response.getStatusLine()
                    .getStatusCode() + "");

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                instream = entity.getContent();
                result = convertStreamToString(instream);
            }

        } catch (Exception e) {
            // manage exceptions
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (Exception exc) {

                }
            }
        }

        return result;
    }

    public String requestPostSaveRegisNotiContent(String url , SaveRegisNotiRepository senddata) {


        HttpClient httpclient = new DefaultHttpClient();
        String result = null;

        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = null;
        InputStream instream = null;

        try {
            /*** json object **/

            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("NotiKey", senddata.getNotikey());
            jsonObject.put("Token", senddata.getToken());

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);
            // 6. set httpPost Entity
            httpPost.setEntity(se);
            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Content-type", "application/json");

            /*******************************/
            response = httpclient.execute(httpPost);

            Log.v("response code", response.getStatusLine()
                    .getStatusCode() + "");

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                instream = entity.getContent();
                result = convertStreamToString(instream);
            }

        } catch (Exception e) {
            // manage exceptions
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (Exception exc) {

                }
            }
        }

        return result;
    }



    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }

        return sb.toString();
    }

}
