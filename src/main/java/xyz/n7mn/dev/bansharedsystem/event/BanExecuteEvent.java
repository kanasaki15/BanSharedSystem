package xyz.n7mn.dev.bansharedsystem.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Date;
import java.util.UUID;

public class BanExecuteEvent extends Event implements Cancellable {
    private static HandlerList handlerList = new HandlerList();
    private boolean isCancel = false;

    private UUID FromUserUUID;
    private UUID TargetUserUUID;
    private String Reason;
    private Date ExpirationDate;
    private String BanType;
    private boolean isAdd;

    public BanExecuteEvent(UUID fromUserUUID, UUID targetUserUUID, String reason, Date expirationDate, String banType, boolean isAdd){
        this.FromUserUUID = fromUserUUID;
        this.TargetUserUUID = targetUserUUID;
        this.Reason = reason;
        this.ExpirationDate = expirationDate;
        this.BanType = banType;
        this.isAdd = isAdd;
    }

    public BanExecuteEvent(Player fromUser, Player targetUser, String reason, Date expirationDate, String banType, boolean isAdd){
        this.FromUserUUID = fromUser.getUniqueId();
        this.TargetUserUUID = targetUser.getUniqueId();
        this.Reason = reason;
        this.ExpirationDate = expirationDate;
        this.BanType = banType;
        this.isAdd = isAdd;
    }


    public UUID getFromUserUUID() {
        return FromUserUUID;
    }

    public UUID getTargetUserUUID() {
        return TargetUserUUID;
    }

    public String getReason() {
        return Reason;
    }

    public Date getExpirationDate() {
        return ExpirationDate;
    }

    public String getBanType() {
        return BanType;
    }

    public boolean isAdd() {
        return isAdd;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return isCancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancel = cancel;
    }
}
