package xyz.n7mn.dev.bansharedsystem.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BanCompleteEvent extends Event {

    private String Type;
    private Player FromPlayer;
    private Player TargetPlayer;
    private String Reason;
    private Date   ExecuteDate;
    private Date   ExpirationDate;

    private static HandlerList handlerList = new HandlerList();

    BanCompleteEvent(String type, Player fromPlayer, Player targetPlayer, String reason){
        this.Type = type;
        this.FromPlayer = fromPlayer;
        this.TargetPlayer = targetPlayer;
        this.Reason = reason;
        this.ExecuteDate = new Date();
        Date parse = new Date();
        try {
             parse = new SimpleDateFormat("yyyy-MM-dd hh:ii:ss").parse("9999-12-31 23:59:59");
        } catch (ParseException e) {
            // e.printStackTrace();
        }
        this.ExpirationDate = parse;
    }

    public String getType() {
        return Type;
    }

    public Player getFromPlayer() {
        return FromPlayer;
    }

    public Player getTargetPlayer() {
        return TargetPlayer;
    }

    public String getReason() {
        return Reason;
    }

    public Date getExecuteDate() {
        return ExecuteDate;
    }

    public Date getExpirationDate() {
        return ExpirationDate;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
