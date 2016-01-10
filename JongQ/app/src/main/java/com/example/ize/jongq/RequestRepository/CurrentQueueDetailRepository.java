package com.example.ize.jongq.RequestRepository;

/**
 * Created by tnpxu on 15/7/2558.
 */
public class CurrentQueueDetailRepository {

    private String resname;
    private String resbranch;
    private String queuetype;

    public CurrentQueueDetailRepository() {

    }

    public CurrentQueueDetailRepository(String resname, String resbranch , String queuetype) {
        this.resname = resname;
        this.resbranch = resbranch;
        this.queuetype = queuetype;
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
}
