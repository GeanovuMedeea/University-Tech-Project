package com.example.labjspservlet.model;
import java.util.Date;

public class GameMove {
    private int gid;
    private int gsid;
    private String move;
    private Date moveTime;

    public GameMove() {
    }

    public GameMove(int gid, int gsid, String move, Date moveTime) {
        this.gid = gid;
        this.gsid = gsid;
        this.move = move;
        this.moveTime = moveTime;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getGsid() {
        return gsid;
    }

    public void setGsid(int gsid) {
        this.gsid = gsid;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public Date getMoveTime() {
        return moveTime;
    }

    public void setMoveTime(Date moveTime) {
        this.moveTime = moveTime;
    }
}
