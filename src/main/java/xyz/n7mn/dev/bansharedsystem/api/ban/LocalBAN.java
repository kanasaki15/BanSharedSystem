package xyz.n7mn.dev.bansharedsystem.api.ban;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import xyz.n7mn.dev.bansharedsystem.api.auth.AuthData;
import xyz.n7mn.dev.bansharedsystem.api.BANInterface;
import xyz.n7mn.dev.bansharedsystem.api.Function;
import xyz.n7mn.dev.bansharedsystem.event.BanExecuteEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class LocalBAN implements BANInterface {

    private AuthData authData = null;

    public LocalBAN(){

    }

    @Override
    public boolean run(UUID targetPlayer, UUID fromPlayer, String reason, Date expirationDate, boolean isBan){
        if (authData == null){
            return false;
        }

        Date maxDate = new Date();
        try {
            maxDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("9999-12-31 23:59:59");
        } catch (ParseException e) {
            // e.printStackTrace();
        }

        if (expirationDate.getTime() <= new Date().getTime()){
            return false;
        }

        if (expirationDate.getTime() >= maxDate.getTime()){
            expirationDate = null;
        }


        BanExecuteEvent event = new BanExecuteEvent(fromPlayer, targetPlayer, reason, expirationDate, "ShareBan", isBan);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()){
            return false;
        }

        try {

            if (isBan){

                if (Bukkit.getPlayer(event.getTargetUserUUID()) != null){
                    Bukkit.getPlayer(event.getTargetUserUUID()).kickPlayer("You've been banned. Reason : "+reason);
                }

                if (fromPlayer != null){
                    Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(new Function().UUID2UserName(event.getTargetUserUUID()), event.getReason(), event.getExpirationDate(), new Function().UUID2UserName(event.getFromUserUUID()));
                } else {
                    Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(new Function().UUID2UserName(event.getTargetUserUUID()), event.getReason(), event.getExpirationDate(), "console");
                }
                return true;
            } else {
                Bukkit.getServer().getBanList(BanList.Type.NAME).pardon(new Function().UUID2UserName(targetPlayer));
                return true;
            }

        } catch (Exception e) {
            // e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean ban(UUID fromPlayer, UUID targetPlayer, String reason, Date expirationDate) {
        return run(targetPlayer, fromPlayer, reason, expirationDate, true);
    }

    @Override
    public boolean unban(UUID fromPlayer, UUID targetPlayer, String reason, Date expirationDate) {
        return run(targetPlayer, fromPlayer, reason, expirationDate, false);
    }
}
