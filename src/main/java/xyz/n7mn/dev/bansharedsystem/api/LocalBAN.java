package xyz.n7mn.dev.bansharedsystem.api;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import xyz.n7mn.dev.bansharedsystem.event.BanKickEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LocalBAN implements BANInterface {

    private AuthData authData = null;

    @Override
    @Deprecated
    public void init(AuthData data) {}

    @Override
    public boolean run(Player fromPlayer, Player targetPlayer, String reason) {

        Date parse = new Date();
        try {
            parse = new SimpleDateFormat("yyyy-MM-dd hh:ii:ss").parse("9999-12-31 23:59:59");
        } catch (ParseException e) {
            // e.printStackTrace();
        }

        return run(fromPlayer, targetPlayer, reason, parse, true);
    }

    public boolean run(Player fromPlayer, Player targetPlayer, String reason, Date expirationDate, boolean isBan){
        try {

            Date parse = new Date();
            try {
                parse = new SimpleDateFormat("yyyy-MM-dd hh:ii:ss").parse("9999-12-31 23:59:59");
            } catch (ParseException e) {
                // e.printStackTrace();
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
            return false;
        }
    }
}
