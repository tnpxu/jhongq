package com.example.ize.jongq.RequestRepository;

/**
 * Created by tnpxu on 24/7/2558.
 */
public class ReservingRepository {
//            "ResName" : "AfterYou",
//            "UserId" : "6",
//            "Nickname" : "benz",
//            "QueueType" : "A",
//            "ResBranch" : "Siam Paragon",
//            "Token" : "xG33c2t7vmhhEI2zkRCnmhwox",
//            "CurrentUserQueue" : 0

    private String resname;
    private String resbranch;
    private int userid;
    private String nickname;
    private String queuetype;
    private String token;
    private int currentuserqueue;

    public ReservingRepository(String resname,String resbranch,int userid,String nickname,String queuetype, String token,int currentuserqueue) {
        this.resname = resname;
        this.resbranch = resbranch;
        this.userid = userid;
        this.nickname = nickname;
        this.queuetype = queuetype;
        this.token = token;
        this.currentuserqueue = currentuserqueue;
    }

    public String getResname() {
        return this.resname;
    }
    public String getResbranch() {
        return this.resbranch;
    }
    public int getUserid() {
        return this.userid;
    }
    public String getNickname() {
        return this.nickname;
    }
    public String getToken() {
        return this.token;
    }
    public int getCurrentuserqueue() {
        return this.currentuserqueue;
    }

    public String getQueuetype () {
        return this.queuetype;
    }
}
