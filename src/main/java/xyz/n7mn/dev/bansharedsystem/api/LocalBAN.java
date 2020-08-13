package xyz.n7mn.dev.bansharedsystem.api;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.bansharedsystem.event.BanExecuteEvent;
import xyz.n7mn.dev.bansharedsystem.event.BanKickEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class LocalBAN implements BANInterface {

    private AuthData authData = null;

    @Override
    @Deprecated
    public void init(AuthData data) {}

    @Override
    public boolean run(Player fromPlayer, Player targetPlayer, String reason) {

        Date parse = new Date();
        try {
            parse = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("9999-12-31 23:59:59");
        } catch (ParseException e) {
            // e.printStackTrace();
        }

        return run(fromPlayer, targetPlayer, reason, parse, true);
    }

    public boolean run(Player fromPlayer, Player targetPlayer, String reason, Date expirationDate, boolean isBan){
        try {

            Date parse = new Date();
            try {
                parse = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("9999-12-31 23:59:59");
            } catch (ParseException e) {
                // e.printStackTrace();
                return false;
            }

            if (expirationDate == null){
                expirationDate = parse;
            }

            if (isBan){
                BanKickEvent event = new BanKickEvent(fromPlayer, targetPlayer, reason, parse);
                Bukkit.getPluginManager().callEvent(event);

                if (event.isCancelled()){
                    return true;
                }

                if (event.getFromPlayer() != null){
                    Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(event.getTargetPlayer().getName(), event.getReason(), event.getExpirationDate(), event.getFromPlayer().getName());
                } else {
                    Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(event.getTargetPlayer().getName(), event.getReason(), event.getExpirationDate(), "console");
                }

                targetPlayer.kickPlayer("You've been banned. Reason : "+event.getReason());
                return true;
            } else {
                Bukkit.getServer().getBanList(BanList.Type.NAME).pardon(fromPlayer.getName());
                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

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
                if (fromPlayer != null){
                    Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(new Function().UUID2UserName(targetPlayer), reason, expirationDate, new Function().UUID2UserName(fromPlayer));
                } else {
                    Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(new Function().UUID2UserName(targetPlayer), reason, expirationDate, "console");
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
}
