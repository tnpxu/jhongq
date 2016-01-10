package com.example.ize.jongq.StoreServiceData;

/**
 * Created by tnpxu on 15/7/2558.
 */
public class ResBranchPickingStorage {
    private String resbranch;
    private String region;
    private boolean queuestatus;

    public ResBranchPickingStorage() {

    }

    public ResBranchPickingStorage(String resbranch,String region,boolean queuestatus) {
        this.resbranch = resbranch;
        this.region = region;
        this.queuestatus = queuestatus;
    }

    public String getResbranch() {
        return this.resbranch;
    }

    public String getRegion() {
        return this.region;
    }

    public boolean getQueuestatus() {
        return this.queuestatus;
    }
}
