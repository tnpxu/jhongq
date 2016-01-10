package com.example.ize.jongq.RequestRepository;

/**
 * Created by tnpxu on 24/7/2558.
 */
public class ReserveUpdateRepository {

//            "ResName" : "BonChon",
//            "ResBranch" : "a",
//            "QueueType" : "A",
//            "Token"     : "41rqz13Su9MGgyUBqpKzNE8tq"

    private String resname;
    private String resbranch;
    private String queuetype;
    private String token;

    public ReserveUpdateRepository (String resname, String resbranch, String queuetype, String token) {
        this.resname = resname;
        this.resbranch = resbranch;
        this.queuetype = queuetype;
        this.token = token;
    }

    public String getResname() {
        return this.resname;
    }

    public String getResbranch() {
        return this.resbranch;
    }

    public String getQueuetype() {
        return this.queuetype;
    }

    public String getToken() {
        return this.token;
    }

}
