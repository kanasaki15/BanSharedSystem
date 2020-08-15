package xyz.n7mn.dev.bansharedsystem.api.ban;

import java.util.Date;
import java.util.UUID;

public class BanShare {
    private int ID;
    private UUID BanUserUUID;
    private String ServerName;
    private UUID BanExecuteUserUUID;
    private String Reason;
    private Date BanExpirationDate;
    private Date BanExecuteDate;
    private String Type;
    private boolean Active;

    public BanShare(int ID, UUID banUserUUID, String serverName, UUID banExecuteUserUUID, String reason, Date banExpirationDate, Date banExecuteDate, String type, boolean active) {
        this.ID = ID;
        this.BanUserUUID = banUserUUID;
        this.ServerName = serverName;
        this.BanExecuteUserUUID = banExecuteUserUUID;
        this.Reason = reason;
        this.BanExpirationDate = banExpirationDate;
        this.BanExecuteDate = banExecuteDate;
        this.Type = type;
        this.Active = active;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public UUID getBanUserUUID() {
        return BanUserUUID;
    }

    public void setBanUserUUID(UUID banUserUUID) {
        BanUserUUID = banUserUUID;
    }

    public String getServerName() {
        return ServerName;
    }

    public void setServerName(String serverName) {
        ServerName = serverName;
    }

    public UUID getBanExecuteUserUUID() {
        return BanExecuteUserUUID;
    }

    public void setBanExecuteUserUUID(UUID banExecuteUserUUID) {
        BanExecuteUserUUID = banExecuteUserUUID;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public Date getBanExpirationDate() {
        return BanExpirationDate;
    }

    public void setBanExpirationDate(Date banExpirationDate) {
        BanExpirationDate = banExpirationDate;
    }

    public Date getBanExecuteDate() {
        return BanExecuteDate;
    }

    public void setBanExecuteDate(Date banExecuteDate) {
        BanExecuteDate = banExecuteDate;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }
}
