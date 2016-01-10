package com.example.ize.jongq.RequestRepository;

/**
 * Created by tnpxu on 25/7/2558.
 */
public class SaveRegisNotiRepository {

//            "Token" : "asdsadsad",
//            "NotiKey" : "asdsad"
    private String token;
    private String notikey;

    public SaveRegisNotiRepository(String token,String notikey) {
        this.token = token;
        this.notikey = notikey;
    }
    public String getToken() {
        return this.token;
    }

    public String getNotikey() {
        return this.notikey;
    }
}
