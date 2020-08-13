package xyz.n7mn.dev.bansharedsystem.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Date;

public class BanKickEvent extends Event implements Cancellable {
    private static HandlerList handlerList = new HandlerList();

    private boolean isCancel = false;

    private Player FromPlayer;
    private Player TargetPlayer;
    private String Reason;
    private Date ExpirationDate;


    public BanKickEvent(Player fromPlayer, Player targetPlayer, String reason, Date expirationDate){
        this.FromPlayer = fromPlayer;
        this.TargetPlayer = targetPlayer;
        this.Reason = reason;
        this.ExpirationDate = expirationDate;
    }


    public Player getFromPlayer() {
        return FromPlayer;
    }

    public void setFromPlayer(Player player){
        this.FromPlayer = player;
    }

    public Player getTargetPlayer() {
        return TargetPlayer;
    }

    public void setTargetPlayer(Player player){
        this.TargetPlayer = player;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public Date getExpirationDate() {
        return ExpirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        ExpirationDate = expirationDate;
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
