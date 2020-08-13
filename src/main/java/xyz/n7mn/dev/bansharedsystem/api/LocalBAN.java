package xyz.n7mn.dev.bansharedsystem.api;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
        try {

            Date parse = new Date();
            try {
                parse = new SimpleDateFormat("yyyy-MM-dd hh:ii:ss").parse("9999-12-31 23:59:59");
            } catch (ParseException e) {
                // e.printStackTrace();
            }

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
        } catch (Exception e){
            return false;
        }
    }
}
