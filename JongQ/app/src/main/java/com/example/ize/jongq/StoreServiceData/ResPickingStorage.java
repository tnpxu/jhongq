package com.example.ize.jongq.StoreServiceData;

/**
 * Created by tnpxu on 14/7/2558.
 */
public class ResPickingStorage {
    private String resname;
    private int countbranch;

    public ResPickingStorage() {

    }

    public ResPickingStorage(String resname,int countbranch) {
        this.resname = resname;
        this.countbranch = countbranch;
    }

    public String getResname() {
        return this.resname;
    }

    public int getCountbranch() {
        return this.countbranch;
    }
}
