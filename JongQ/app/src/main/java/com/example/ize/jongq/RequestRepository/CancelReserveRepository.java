package com.example.ize.jongq.RequestRepository;

/**
 * Created by tnpxu on 25/7/2558.
 */
public class CancelReserveRepository {

//            "UserId" : "6",
//            "ResName" : "BonChon",
//            "ResBranch" : "a",
//            "QueueType" : "A",
//            "Token" : "41rqz13Su9MGgyUBqpKzNE8tq"

    private int userid;
    private String resname;
    private String resbranch;
    private String queuetype;
    private String token;

    public CancelReserveRepository(int userid,String resname,String resbranch,String queuetype,String token) {
        this.userid = userid;
        this.resname = resname;
        this.resbranch = resbranch;
        this.queuetype = queuetype;
        this.token = token;
    }

    public int getUserid () {
        return this.userid;
    }
    public String getResname () {
        return this.resname;
    }
    public String getResbranch () {
        return this.resbranch;
    }
    public String getQueuetype () {
        return this.queuetype;
    }
    public String getToken () {
        return this.token;
    }

}
