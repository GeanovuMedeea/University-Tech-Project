package com.example.labjspservlet.model;
import java.util.Date;

public class GameState {
    private int gsid;
    private int uid;
    private Date startTime;
    private int elapsedTime;

    public GameState() {
    }

    public GameState(int gsid, int uid, Date startTime, int elapsedTime) {
        this.gsid = gsid;
        this.uid = uid;
        this.startTime = startTime;
        this.elapsedTime = elapsedTime;
    }

    public int getGsid() {
        return gsid;
    }

    public void setGsid(int gsid) {
        this.gsid = gsid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}
