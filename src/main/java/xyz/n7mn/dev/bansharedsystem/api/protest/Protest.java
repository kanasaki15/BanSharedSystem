package xyz.n7mn.dev.bansharedsystem.api.protest;

import java.util.Date;

public class Protest {

    private int ID;
    private int BanListID;
    private String ProtestContents;
    private Date date;
    private boolean Active;

    public Protest(int ID, int banListID, String protestContents, Date date, boolean active) {
        this.ID = ID;
        BanListID = banListID;
        ProtestContents = protestContents;
        this.date = date;
        Active = active;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getBanListID() {
        return BanListID;
    }

    public void setBanListID(int banListID) {
        BanListID = banListID;
    }

    public String getProtestContents() {
        return ProtestContents;
    }

    public void setProtestContents(String protestContents) {
        ProtestContents = protestContents;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }
}
