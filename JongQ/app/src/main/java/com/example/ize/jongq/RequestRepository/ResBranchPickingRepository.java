package com.example.ize.jongq.RequestRepository;

/**
 * Created by tnpxu on 15/7/2558.
 */
public class ResBranchPickingRepository {

    private String resname;

    public ResBranchPickingRepository() {

    }

    public ResBranchPickingRepository(String resname) {
        this.resname = resname;
    }

    public String getResname() {
        return this.resname;
    }
}
